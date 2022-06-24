package ir.ebcom.gifapplication.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ebcom.gifapplication.BuildConfig
import ir.ebcom.gifapplication.data.repository.ApiRepository
import ir.ebcom.gifapplication.data.repository.ApiRepositoryImpl
import ir.ebcom.gifapplication.data.webservice.GiphyWebServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSession

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson{
        return GsonBuilder().serializeNulls().create()
    }



    @Singleton
    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor{
        val interceptor=HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }


    @Singleton
    @Provides
    fun getOkHttpClient(httpInterceptor: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .connectTimeout(30,TimeUnit.SECONDS)
            .followRedirects(false)
            .addInterceptor(httpInterceptor)
            .retryOnConnectionFailure(true)
            .hostnameVerifier{hostname:String,session: SSLSession -> true}
            .build()
    }



    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }



    @Singleton
    @Provides
    fun provideGiphyWebService(retrofit: Retrofit.Builder): GiphyWebServices{
        return retrofit.build().create(GiphyWebServices::class.java)
    }


    @Singleton
    @Provides
    fun provideApiRepository(giphyWebServices: GiphyWebServices): ApiRepository{
        return ApiRepositoryImpl(giphyWebServices)
    }
}
