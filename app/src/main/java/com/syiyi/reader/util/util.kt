package com.syiyi.reader.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import okio.Buffer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class TypeGet<T> {
    val type: Type
        get() = run {
            val superclass = javaClass.genericSuperclass
            Util.canonicalize((superclass as ParameterizedType).actualTypeArguments[0])
        }
}

inline fun <reified T> T.toJson(): String {
    val moshi: Moshi = Moshi.Builder().build()

    val buffer = Buffer()

    val jsonWriter = JsonWriter.of(buffer).apply {
        indent = "   "
    }

    moshi.adapter<T>(object : TypeGet<T>() {}.type).toJson(jsonWriter, this)

    return buffer.readUtf8()
}


inline fun <reified T> String.toModel(): T? {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(object : TypeGet<T>() {}.type)
    return jsonAdapter.fromJson(this)
}
