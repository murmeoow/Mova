package dm.sample.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val urlWithApi = originalUrl.newBuilder()
            .addQueryParameter("language", dm.sample.mova.domain.Constants.DEFAULT_LANGUAGE).build()

        val requestWithApi = originalRequest.newBuilder().url(urlWithApi).build()
        return chain.proceed(requestWithApi)
    }
}