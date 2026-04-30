package org.troikas.main.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.troikas.main.database.AppDatabase
import org.troikas.main.database.IngredientRepository
import org.troikas.main.network.FoodRepository
import org.troikas.main.utils.AnalyzeProductUseCase
import org.troikas.main.utils.StringParser
import org.troikas.main.ResultViewModel

val appModule = module {
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().ingredientDao() }
    
    single { IngredientRepository(get()) }
    single { FoodRepository() }
    single { StringParser() }
    
    single { AnalyzeProductUseCase(get(), get(), get()) }
    
    viewModel { ResultViewModel(get()) }
}