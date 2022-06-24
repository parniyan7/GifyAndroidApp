package ir.ebcom.gifapplication.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ir.ebcom.gifapplication.data.consts.getRatingType
import ir.ebcom.gifapplication.R
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.databinding.GifDetailLayoutBinding

class GifDetailsView (context: Context, attrs: AttributeSet):
        LinearLayout(context,attrs){
            private val view= inflate(context, R.layout.gif_detail_layout,this)
    var gifDetailsBinding =GifDetailLayoutBinding.bind(view)
    private var glide : RequestManager = Glide.with(this)
    fun setGifDetails (gifModel: GifModel){
        gifDetailsBinding.loadingGifPb.visibility = View.VISIBLE
        glide.asGif()
            .load(gifModel.url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    gifDetailsBinding.loadingGifPb.visibility =View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    gifDetailsBinding.loadingGifPb.visibility =View.GONE
                    return false
                }

            })
            .into(gifDetailsBinding.gifIv)

        if (gifModel.title.isEmpty()){
            gifDetailsBinding.gifTitleTv.visibility =View.GONE
        }else{
            gifDetailsBinding.gifTitleTv.visibility= View.VISIBLE
            gifDetailsBinding.gifTitleTv.text =gifModel.title
        }
        gifDetailsBinding.gifShortLinkTv.text =gifModel.shortUrl
        gifDetailsBinding.rateTv.visibility=View.VISIBLE
        gifDetailsBinding.rateTv.text= getRatingType(gifModel.rating)
    }
        }