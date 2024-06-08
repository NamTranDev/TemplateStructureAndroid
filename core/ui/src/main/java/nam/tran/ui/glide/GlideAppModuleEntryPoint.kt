package nam.tran.ui.glide

import okhttp3.OkHttpClient

@dagger.hilt.EntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
interface GlideAppModuleEntryPoint {
    fun okHttpClient(): OkHttpClient
}