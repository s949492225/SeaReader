package com.syiyi.reader.other

import com.syiyi.reader.engine.Property
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


object HttpClient {

    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()
    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = cookieStore[url.host]
            return cookies ?: emptyList()
        }
    }

    @Volatile
    private var client: OkHttpClient? = null

    operator fun invoke(): OkHttpClient {
        if (client == null) {
            synchronized(HttpClient.javaClass) {
                if (client == null) {
                    client = OkHttpClient
                        .Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .cookieJar(cookieJar)
                        .build()
                }
            }
        }

        return client!!
    }

    fun fetch(url: String, headers: Array<Property>?, formBody: Array<Property>?): String {
        val request: Request = Request.Builder()
            .url(url)
            .apply {
                headers?.forEach { header ->
                    header(header.name, header.value)
                }
                if (formBody != null) {
                    post(FormBody.Builder().apply {
                        formBody.forEach { param ->
                            add(param.name, param.value)
                        }
                    }.build())
                }
            }
            .build()

        val response: Response = HttpClient().newCall(request).execute()

        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return response.body!!.string()
    }

}