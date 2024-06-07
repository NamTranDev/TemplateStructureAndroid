package nam.tran.data

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nam.tran.data.network.NetworkModule
import nam.tran.data.preference.StorePreferenceModule

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
}