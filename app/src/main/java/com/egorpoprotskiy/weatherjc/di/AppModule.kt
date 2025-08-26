package com.egorpoprotskiy.weatherjc.di

import com.egorpoprotskiy.weatherjc.api.RetrofitClient
import com.egorpoprotskiy.weatherjc.api.WeatherApi
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//7

@Module //этот класс является модулем и содержит логику для предоставления зависимостей.
@InstallIn(SingletonComponent::class) //все зависимости, предоставленные этим модулем, должны быть доступны на протяжении всего времени жизни приложения
object AppModule {
    /**
     * Предоставляет синглтон-экземпляр WeatherApi.
     * RetrofitClient.weatherApi уже является синглтоном, поэтому мы просто возвращаем его.
     */
    //Аннотация для функции, которая "предоставляет" экземпляр зависимости.
    @Provides
    //Гарантирует, что будет создан только один экземпляр этой зависимости.
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return RetrofitClient.weatherApi
    }

    /**
     * Предоставляет синглтон-экземпляр WeatherRepository.
     * Hilt автоматически внедрит WeatherApi, так как мы предоставили его выше.
     *
     * @param weatherApi Экземпляр WeatherApi, который Hilt предоставляет автоматически.
     * @return Возвращает реализацию WeatherRepository.
     */
    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }
}