package com.victor.musicapp.presenter.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.victor.musicapp.R
import com.victor.musicapp.data.api.SpotifyArtistService
import com.victor.musicapp.data.api.SpotifyArtistTrackService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.database.AppDatabase
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.data.database.dao.TrackDao
import com.victor.musicapp.data.util.DATABASE_NAME
import com.victor.musicapp.data.util.LiveDataCallAdapterFactory
import com.victor.musicapp.data.util.SharedPreferencesConstants.SHARED_PREFERENCES_NAME
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


    /**retrofit*/
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
    fun provideSpotifyArtistService(
        @Named("retrofit_artist_album") retrofit: Retrofit
    ) =
        retrofit.create(SpotifyArtistService::class.java)


    /**glide*/
    @JvmStatic
    @Singleton
    @Provides
    fun provideRequestOption(): RequestOptions = RequestOptions
        .placeholderOf(R.drawable.fresno_album_cover)
        .error(R.drawable.fresno_album_cover)


    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager =
        Glide.with(application).setDefaultRequestOptions(requestOptions)


    /**shared preferences*/
    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application) =
        application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(pref: SharedPreferences) = pref.edit()


    /**database*/
    @JvmStatic
    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideSpotifyArtistTrackDao(database: AppDatabase): SpotifyArtistTrackDao =
        database.spotifyArtistTrackDao()

    @JvmStatic
    @Singleton
    @Provides
    fun providesTrackDao(database: AppDatabase): TrackDao =
        database.trackDao()

}