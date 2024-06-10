package nam.tran.ui.binding

import android.R
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import nam.tran.common.Logger
import java.io.File


object BindingCommon {

    @JvmStatic
    @BindingAdapter(
        value = ["app:link_image", "app:src_image_default"], requireAll = false
    )
    fun loadImageFromLink(view: ImageView?, linkImage: String?, srcImageDefault: Drawable? = null) {
        view ?: return
        linkImage?.run {
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 2f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val requestOptions = RequestOptions().placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).fitCenter().error(srcImageDefault)

            Glide.with(view).load(linkImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Logger.debug(e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                }).into(view)
        } ?: kotlin.run {
            srcImageDefault?.run {
                view.setImageDrawable(this)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:file", "app:default"], requireAll = false
    )
    fun loadImageFromFile(view: ImageView?, file: File?, default: Drawable? = null) {
        view ?: return
        file?.run {
            Glide.with(view).load(this).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(view)
        } ?: kotlin.run {
            default?.run {
                view.setImageDrawable(this)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:link_background", "app:src_background_default"], requireAll = false
    )
    fun loadImageBackgroundFromLink(
        view: View?, linkBackground: String?, srcBackgroundDefault: Drawable? = null
    ) {
        view ?: return
        linkBackground?.run {
            Glide.with(view).load(this).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable, transition: Transition<in Drawable?>?
                    ) {
                        view.background = resource
                    }
                })
        } ?: kotlin.run {
            srcBackgroundDefault?.run {
                view.background = this
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:selected", "app:src_selected", "app:src_unselect"], requireAll = false
    )
    fun loadDrawableBasedOnSelection(
        view: View, isSelected: Boolean?, selected: Drawable?, unselect: Drawable?
    ) {
        if (isSelected == null || selected == null || unselect == null) return
        when (view) {
            is ImageView -> {
                view.setImageDrawable(if (isSelected) selected else unselect)
            }

            else -> {
                view.setBackgroundDrawable(if (isSelected) selected else unselect)
            }
        }
    }
}