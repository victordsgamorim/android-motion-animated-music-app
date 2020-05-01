package com.victor.musicapp.presenter.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.victor.musicapp.R
import com.victor.musicapp.data.api.SpotifyArtistTrackService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.util.LiveDataCallAdapterFactory
import com.victor.musicapp.data.util.SpotifyConstants.BASE_URL_ALBUM
import com.victor.musicapp.data.util.SpotifyConstants.BASE_URL_TOKEN
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
        client.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                return chain.proceed(chain.request())
            }

        })

        return client.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    @Named("retrofit_artist_album")
    fun provideRetrofitInstance(gson: Gson, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL_ALBUM)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    @JvmStatic
    @Singleton
    @Provides
    @Named("retrofit_generate_token")
    fun provideRetrofitGenerateToken(gson: Gson, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL_TOKEN)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideSportifyTokenService(
        @Named("retrofit_generate_token") retrofit: Retrofit
    ) =
        retrofit.create(SpotifyTokenService::class.java)

    @JvmStatic
    @Singleton
    @Provides
    fun provideSportifyservice(
        @Named("retrofit_artist_album") retrofit: Retrofit
    ) =
        retrofit.create(SpotifyArtistTrackService::class.java)

    @JvmStatic
    @Singleton
    @Provides
    fun provideRequestOption() = RequestOptions
        .placeholderOf(R.drawable.fresno_album_cover)
        .error(R.drawable.fresno_album_cover)


    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions) =
        Glide.with(application).setDefaultRequestOptions(requestOptions)

}