package com.example.uzum.app.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}