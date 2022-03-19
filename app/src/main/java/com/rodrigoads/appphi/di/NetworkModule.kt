package com.rodrigoads.appphi.di

import com.rodrigoads.appphi.BuildConfig
import com.rodrigoads.appphi.network.ApiService
import com.rodrigoads.appphi.network.interceptor.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIME_SECONDS = 15L

    @Provides
    fun provideRetrofit(
        provideOkHttpClient: OkHttpClient,
        provideGsonConverterFactory: GsonConverterFactory
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideOkHttpClient)
            .addConverterFactory(provideGsonConverterFactory)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(
        provideHttpLoggingInterceptor: HttpLoggingInterceptor,
        provideAuthorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(provideHttpLoggingInterceptor)
            .addInterceptor(provideAuthorizationInterceptor)
            .readTimeout(TIME_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIME_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else HttpLoggingInterceptor.Level.NONE
            )
        }
    }

    @Provides
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor {
        return AuthorizationInterceptor(BuildConfig.TOKEN_API)
    }

}