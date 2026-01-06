package net.paulbogdan.simplerecipe.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.paulbogdan.simplerecipe.BuildConfig
import net.paulbogdan.simplerecipe.SimpleRecipe
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.business.preferences.UserPrefsImpl
import net.paulbogdan.simplerecipe.network.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule{

    @Singleton
    @Binds
    abstract fun bindUserPrefs(userPrefs: UserPrefsImpl): UserPrefs

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesSimpleRecipeInstance(@ApplicationContext context: Context): SimpleRecipe {
        return context as SimpleRecipe
    }

    @Provides
    @Singleton
    fun provideUserPrefsImpl(@ApplicationContext context: Context) =
        UserPrefsImpl(context = context)

    @Provides
    @Singleton
    fun gsonFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
            .setLenient()
            .create()
    )

    @Provides
    @Singleton
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(gsonFactory())
        .build().create(ApiService::class.java)
}