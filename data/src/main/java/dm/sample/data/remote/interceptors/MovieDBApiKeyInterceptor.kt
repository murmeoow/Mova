package dm.sample.data.remote.interceptors

import dm.sample.mova.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MovieDBApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val urlWithApi = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY_V3).build()

        val requestWithApi = originalRequest.newBuilder().url(urlWithApi).build()
        return chain.proceed(requestWithApi)
    }
}