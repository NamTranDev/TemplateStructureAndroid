package nam.tran.data.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.pokemon_list.PokemonListUseCase
import nam.tran.data.usecase.pokemon_list.PokemonListUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UseCaseModule {

    @Binds
    @ActivityRetainedScoped
    @HomeUseCaseQualifier
    abstract fun bindUseCase(
        useCaseImpl: PokemonListUseCaseImpl
    ): PokemonListUseCase
}