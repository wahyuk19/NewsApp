package com.dev.newsapp.data.di

import android.content.Context
import androidx.room.Room
import com.dev.newsapp.BuildConfig
import com.dev.newsapp.data.api.NewsApi
import com.dev.newsapp.data.db.ArticleDao
import com.dev.newsapp.data.db.NewsRoomDatabase
import com.dev.newsapp.data.db.SourceDao
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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi{
        val logging = HttpLoggingInterceptor()
        logging.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient().newBuilder()

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50,TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient.build())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideArticleDao(newsRoomDatabase: NewsRoomDatabase): ArticleDao = newsRoomDatabase.articleDao()

    @Singleton
    @Provides
    fun provideSourceDao(newsRoomDatabase: NewsRoomDatabase): SourceDao = newsRoomDatabase.sourceDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NewsRoomDatabase = Room.databaseBuilder(
        context,NewsRoomDatabase::class.java,"news_db").fallbackToDestructiveMigration().build()
}