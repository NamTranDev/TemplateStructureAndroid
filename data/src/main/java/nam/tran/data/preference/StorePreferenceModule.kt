package nam.tran.data.preference

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorePreferenceModule {

    @Binds
    abstract fun providePreference(preferenceProvider: StorePreferenceImpl): StorePreference
}
