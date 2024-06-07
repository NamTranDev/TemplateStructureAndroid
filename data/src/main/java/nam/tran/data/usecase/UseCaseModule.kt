package nam.tran.data.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.home.HomeUseCase
import nam.tran.data.usecase.home.HomeUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UseCaseModule {

    @Binds
    @ActivityRetainedScoped
    @HomeUseCaseQualifier
    abstract fun bindHomeUseCase(
        homeUseCaseImpl: HomeUseCaseImpl
    ): HomeUseCase
}