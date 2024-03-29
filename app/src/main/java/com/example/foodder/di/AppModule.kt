package com.example.foodder.di

import android.app.Application
import androidx.room.Room
import com.example.foodder.common.Constants.BASE_URL
import com.example.foodder.data.local_db.FoodDatabase
import com.example.foodder.data.remote.FoodApi
import com.example.foodder.data.repository.FoodDbRepositoryImpl
import com.example.foodder.data.repository.FoodApiRepositoryImpl
import com.example.foodder.domain.repository.FoodDbRepository
import com.example.foodder.domain.repository.FoodApiRepository
import com.example.foodder.domain.use_case.AddMealUseCase
import com.example.foodder.domain.use_case.DeleteMealUseCase
import com.example.foodder.domain.use_case.FavouriteFoodScreenUseCases
import com.example.foodder.domain.use_case.GetAllMealsUseCase
import com.example.foodder.domain.use_case.GetMealByIdUseCase
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
    fun provideFoodApiRepository(api: FoodApi): FoodApiRepository{
        return FoodApiRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideFoodDatabase(app: Application): FoodDatabase{
        return Room.databaseBuilder(
            app,
            FoodDatabase::class.java,
            "food_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideFoodDbRepository(db: FoodDatabase): FoodDbRepository {
        return FoodDbRepositoryImpl(db.mealDao)
    }
    @Provides
    @Singleton
    //if more use cases just make data class with it and only provide this data class
    fun provideGetRandomUseCase(repository: FoodApiRepository): GetRandomFoodUseCase{
        return GetRandomFoodUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideAddMealUseCase(repository: FoodDbRepository): AddMealUseCase{
        return AddMealUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMealByIdUseCase(repository: FoodDbRepository): GetMealByIdUseCase{
        return GetMealByIdUseCase(repository = repository)
    }
    @Provides
    @Singleton
    fun favouriteFoodScreenUseCases(repository: FoodDbRepository): FavouriteFoodScreenUseCases{
        return FavouriteFoodScreenUseCases(
            deleteMealUseCase = DeleteMealUseCase(repository),
            getAllMealsUseCase = GetAllMealsUseCase(repository)
        )
    }
}