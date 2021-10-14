package com.syiyi.reader.engine

import com.hippo.quickjs.android.QuickJS

object QuickJs {

    @Volatile
    private var quickJS: QuickJS? = null

    operator fun invoke(): QuickJS {
        if (quickJS == null) {
            synchronized(QuickJs.javaClass) {
                if (quickJS == null) {
                    quickJS = QuickJS
                        .Builder()
                        .registerTypeAdapter(Property::class.java, PropertyTypeAdapter())
                        .build()
                }
            }
        }

        return quickJS!!
    }

}