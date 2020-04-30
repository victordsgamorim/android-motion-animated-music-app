package com.victor.musicapp.presenter.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.victor.musicapp.R
import com.victor.musicapp.data.api.SpotifyService
import com.victor.musicapp.data.util.LiveDataCallAdapterFactory
import com.victor.musicapp.data.util.SpotifyConstants.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()


    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(gson: Gson, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideSportifyservice(retrofit: Retrofit) = retrofit.create(SpotifyService::class.java)


    @Singleton
    @Provides
    fun provideRequestOption() = RequestOptions
        .placeholderOf(R.drawable.fresno_album_cover)
        .error(R.drawable.fresno_album_cover)


    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions) =
        Glide.with(application).setDefaultRequestOptions(requestOptions)

}