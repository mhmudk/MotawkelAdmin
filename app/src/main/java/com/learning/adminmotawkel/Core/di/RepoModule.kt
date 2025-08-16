package com.learning.adminmotawkel.Core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.adminmotawkel.Data.RepoImp
import com.learning.adminmotawkel.Domain.FirebaseOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {






    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}