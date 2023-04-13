package dm.sample.data.di

import android.content.Context
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.base.ServerStatusCode
import dm.sample.data.remote.ServerApi
import dm.sample.data.remote.dtos.MovieAccountStatesDto
import dm.sample.data.remote.gson.AccountStateAdapter
import dm.sample.data.remote.gson.LocalDateAdapter
import dm.sample.data.remote.gson.ServerStatusCodeTypeAdapter
import dm.sample.data.remote.interceptors.LanguageInterceptor
import dm.sample.data.remote.interceptors.MovieDBApiKeyInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(ServerStatusCode::class.java, ServerStatusCodeTypeAdapter())
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(MovieAccountStatesDto::class.java, AccountStateAdapter())
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MovieDBApiKeyInterceptor())
            .addInterceptor(LanguageInterceptor())
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.MOVIE_DB_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideServerApi(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

}