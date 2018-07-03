package com.skoumal.teanity.example.di

import com.skoumal.teanity.example.data.repository.PhotoRepository
import org.koin.dsl.module.applicationContext

val repositoryModule = applicationContext {
    bean { PhotoRepository(get()) }
}