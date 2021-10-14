package com.syiyi.reader.engine

import com.hippo.quickjs.android.JSObject
import com.hippo.quickjs.android.JSString
import com.hippo.quickjs.android.JSValue
import com.hippo.quickjs.android.TypeAdapter

data class Property(val name: String, val value: String)

class PropertyTypeAdapter : TypeAdapter<Property>() {

    override fun toJSValue(depot: Depot, context: Context, value: Property?): JSValue {
        return if (value == null) {
            context.createJSNull()
        } else {
            context.createJSObject(value).apply {
                setProperty("name", context.createJSString(value.name))
                setProperty("value", context.createJSString(value.value))
            }
        }
    }

    override fun fromJSValue(
        depot: Depot,
        context: Context,
        value: JSValue
    ): Property? {
        if (value is JSObject) {
            val jsObject = value.cast(JSObject::class.java)
            val name = jsObject.getProperty("name").cast(JSString::class.java)
            val value2 = jsObject.getProperty("value").cast(JSString::class.java)
            return Property(name.string, value2.string)
        }
        return null
    }
}