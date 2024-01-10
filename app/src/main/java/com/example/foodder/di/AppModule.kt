package com.example.foodder.di

import com.example.foodder.common.Constants.BASE_URL
import com.example.foodder.data.remote.FoodApi
import com.example.foodder.data.repository.FoodRepositoryImpl
import com.example.foodder.domain.repository.FoodRepository
import com.example.foodder.domain.use_case.GetRandomFoodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
//all the dependencies in the module live as long as the application
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodApi(): FoodApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(api: FoodApi): FoodRepository{
        return FoodRepositoryImpl(api)
    }

    @Provides
    @Singleton
    //if more use cases just make data class with it and only provide this data class
    fun provideGetRandomUseCase(repository: FoodRepository): GetRandomFoodUseCase{
        return GetRandomFoodUseCase(repository)
    }
}