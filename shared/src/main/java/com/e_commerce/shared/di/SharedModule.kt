package com.e_commerce.shared.di

import com.e_commerce.shared.data.repository.CustomerRepositoryImpl
import com.e_commerce.shared.data.resourceManager.ResourceManagerImpl
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    factoryOf(::CustomerRepositoryImpl) bind CustomerRepository::class
    factoryOf(::ResourceManagerImpl) bind ResourceManager::class
}