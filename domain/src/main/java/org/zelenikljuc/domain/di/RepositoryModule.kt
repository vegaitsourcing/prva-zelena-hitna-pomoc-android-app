package org.zelenikljuc.domain.di

import org.zelenikljuc.domain.contact.ContactRepository
import org.zelenikljuc.domain.contact.ContactRepositoryImpl
import org.zelenikljuc.domain.donate.DonateRepository
import org.zelenikljuc.domain.donate.DonateRepositoryImpl
import org.zelenikljuc.domain.home.categories.CategoryRepository
import org.zelenikljuc.domain.home.categories.CategoryRepositoryImpl
import org.zelenikljuc.domain.home.reportproblem.ProblemRepository
import org.zelenikljuc.domain.home.reportproblem.ProblemRepositoryImpl
import org.zelenikljuc.domain.home.wastedisposal.WasteDisposalRepository
import org.zelenikljuc.domain.home.wastedisposal.WasteDisposalRepositoryImpl
import org.zelenikljuc.domain.news.NewsRepository
import org.zelenikljuc.domain.news.NewsRepositoryImpl
import org.zelenikljuc.domain.partners.PartnersRepository
import org.zelenikljuc.domain.partners.PartnersRepositoryImpl
import org.zelenikljuc.firebase.FirebaseManager
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
    fun provideWasteDisposalRepository(
        firebaseManager: FirebaseManager
    ) : WasteDisposalRepository {
        return WasteDisposalRepositoryImpl(firebaseManager)
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