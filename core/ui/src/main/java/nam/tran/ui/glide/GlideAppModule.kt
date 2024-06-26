package nam.tran.ui.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.android.EntryPointAccessors
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject

@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class GlideAppModule : AppGlideModule() {

    @Inject
    @GlideOkHttpClient
    lateinit var okHttpClient: OkHttpClient

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val appContext = context.applicationContext

        // Lấy OkHttpClient từ Hilt
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            GlideAppModuleEntryPoint::class.java
        )

        val okHttpClient = hiltEntryPoint.okHttpClient()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
    }
}