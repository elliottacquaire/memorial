package com.exae.memorialapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.exae.memorialapp.BuildConfig
import com.exae.memorialapp.animation.ApiServiceAnno
import com.exae.memorialapp.animation.OkHttpAnnotation
import com.exae.memorialapp.animation.RetrofitAnno
import com.exae.memorialapp.animation.RetrofitAnnoOther
import com.exae.memorialapp.animation.TokenPreference
import com.exae.memorialapp.animation.UserPreference
import com.exae.memorialapp.api.NetwrokService
import com.exae.memorialapp.base.interceptor.ApiRequestInterceptor
import com.exae.memorialapp.common.Constants.URL_SERVICE
import com.exae.memorialapp.utils.SecurePreferences
import com.exae.memorialapp.utils.StringPreference
import com.exae.memorialapp.utils.StringPreferenceType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module  //模块用于向 Hlit 添加绑定，换句话说，是告诉 Hlit 如何提供不同类型的实例。
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideInt(): Int {
        return 1
    }

    @Provides
    @Singleton
    fun provideSecurePreferences(@ApplicationContext context: Context): SecurePreferences {
        return SecurePreferences(
            context,
            "1zUowAj8DjWokhPh4x7OkfHb6JbCDv2f",
            "porsche_prefs"
        )
    }

    @Provides
    @Singleton
    fun provideApiRequestInterceptor(
        @ApplicationContext context: Context,
        @TokenPreference stringPreference: StringPreferenceType,
    ): ApiRequestInterceptor {
        return ApiRequestInterceptor(
            context,
            stringPreference
        )
    }
    @Provides
    @TokenPreference
    fun provideTokenPreference(
        securePreferences: SecurePreferences
    ): StringPreferenceType {
        return StringPreference(securePreferences, "Secrets.TOKEN", "")
    }
    @Provides
    @UserPreference
    fun provideUserPreference(
        securePreferences: SecurePreferences
    ): StringPreferenceType {
        return StringPreference(securePreferences, "Secrets.USER", "")
    }
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    @OkHttpAnnotation
    fun provideOkHttpClient(@ApplicationContext context: Context,
                            httpLoggingInterceptor: HttpLoggingInterceptor,
                            apiRequestInterceptor: ApiRequestInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(apiRequestInterceptor)
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor(ChuckerInterceptor.Builder(context).build())
        return builder.build()
    }


    @Singleton
    @Provides
    @RetrofitAnno
    fun provideRetrofit(
        @OkHttpAnnotation httpClient: OkHttpClient
//        liveDataCallAdapterFactory: LiveDataCallAdapterFactory,
//        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(URL_SERVICE)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(liveDataCallAdapterFactory)
            .build()
    }

    @Singleton
    @Provides
    @RetrofitAnnoOther
    fun provideRetrofitService(
        @OkHttpAnnotation httpClient: OkHttpClient
//        liveDataCallAdapterFactory: LiveDataCallAdapterFactory,
//        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(URL_SERVICE)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(liveDataCallAdapterFactory)
            .build()
//            .create(NetwrokService::class)
    }

    @Singleton
    @Provides
    @ApiServiceAnno
    fun provideApiService(@RetrofitAnno retrofit : Retrofit) : NetwrokService {
        return retrofit.create(NetwrokService::class.java)
    }

}