package com.example.domain.di

import com.example.domain.contact.ContactRepository
import com.example.domain.contact.ContactRepositoryImpl
import com.example.domain.donate.DonateRepository
import com.example.domain.donate.DonateRepositoryImpl
import com.example.domain.home.categories.CategoryRepository
import com.example.domain.home.categories.CategoryRepositoryImpl
import com.example.domain.home.reportproblem.ProblemRepository
import com.example.domain.home.reportproblem.ProblemRepositoryImpl
import com.example.domain.news.NewsRepository
import com.example.domain.news.NewsRepositoryImpl
import com.example.domain.partners.PartnersRepository
import com.example.domain.partners.PartnersRepositoryImpl
import com.example.firebase.FirebaseManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        firebaseManager: FirebaseManager
    ) : CategoryRepository {
        return CategoryRepositoryImpl(firebaseManager)
    }

    @Provides
    @Singleton
    fun provideProblemRepository(
        firebaseManager: FirebaseManager
    ) : ProblemRepository {
        return ProblemRepositoryImpl(firebaseManager)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        firebaseManager: FirebaseManager
    ) : NewsRepository {
        return NewsRepositoryImpl(firebaseManager)
    }

    @Provides
    @Singleton
    fun providePartnersRepository(
        firebaseManager: FirebaseManager
    ) : PartnersRepository {
        return PartnersRepositoryImpl(firebaseManager)
    }

    @Provides
    @Singleton
    fun provideDonateRepository(
        firebaseManager: FirebaseManager
    ) : DonateRepository {
        return DonateRepositoryImpl(firebaseManager)
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        firebaseManager: FirebaseManager
    ) : ContactRepository {
        return ContactRepositoryImpl(firebaseManager)
    }
}