package com.syiyi.reader.engine

import android.util.Log
import com.hippo.quickjs.android.*
import com.syiyi.reader.other.HttpClient
import com.syiyi.reader.util.toJson
import com.syiyi.reader.util.toModel


@Suppress("unused")
object JSEngine {

    @JvmStatic
    fun log(msg: String) {
        Log.i("JSRuntime", msg)
    }

    @JvmStatic
    fun fetch(
        url: String,
        headers: Array<Property>? = null,
        formBody: Array<Property>? = null
    ): String {
        return HttpClient.fetch(url, headers, formBody)
    }

    inline fun <reified T> execute(script: String, method: String, vararg parameters: Any): T? {
        val jsRuntime = QuickJs().createJSRuntime()
        val context = jsRuntime.createJSContext()
        try {
            buildLogJSMethod(context)

            buildFetchJSMethod(context)

            context.evaluate(script, "source.js")

            val jsMethod = buildSourceMethod(method, parameters)

            while (context.executePendingJob()) {
                println("执行promise中...")
            }

            val result =
                context.evaluate("source.$jsMethod", "exe-source.js", String::class.java)

            return result.toModel()

        } catch (e: Exception) {
            throw JSExecuteException(e.message, e.cause)
        } finally {
            context.close()
            jsRuntime.close()
        }
    }

    fun buildFetchJSMethod(context: JSContext) {
        val fetchMethod = Method.create(
            Void::class.java,
            JSEngine.javaClass.getMethod(
                "fetch",
                String::class.java,
                Array<Property>::class.java,
                Array<Property>::class.java
            )
        )

        context.globalObject.setProperty(
            "fetch",
            context.createJSFunctionS(JSEngine.javaClass, fetchMethod)
        )
    }

    fun buildLogJSMethod(context: JSContext) {
        val logMethod = Method.create(
            Void::class.java,
            JSEngine.javaClass.getMethod("log", String::class.java)
        )

        context.globalObject.setProperty(
            "log",
            context.createJSFunctionS(JSEngine.javaClass, logMethod)
        )
    }

    fun buildSourceMethod(
        method: String,
        parameters: Array<out Any>
    ): StringBuilder {
        val methodBuilder = StringBuilder("$method(")

        parameters.forEachIndexed { index, value ->
            if (index != 0) {
                methodBuilder.append(",")
            }
            when (value) {
                is String -> {
                    methodBuilder.append("\"$value\"")
                }
                is Int, Double, Long -> {
                    methodBuilder.append("$value")
                }
                is Boolean -> {
                    methodBuilder.append(if (value) "true" else "false")
                }
                else -> {
                    methodBuilder.append(value.toJson())
                }
            }
        }

        methodBuilder.append(")")
        return methodBuilder
    }
}