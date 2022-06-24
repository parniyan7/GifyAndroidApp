package ir.ebcom.gifapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.databinding.ItemGifBinding
import javax.inject.Inject

class GifsAdapter @Inject constructor(
    private val glide: RequestManager,
): RecyclerView.Adapter<GifsAdapter.GifVh>() {

    private lateinit var onItemCallBack: (GifModel) -> Unit

    fun setCallBack(callBack: (GifModel) -> Unit) {
        this.onItemCallBack = callBack
    }


    class GifVh (var itemBinding: ItemGifBinding):RecyclerView.ViewHolder(itemBinding.root)

    private val diffCallback =object :DiffUtil.ItemCallback<GifModel>(){
        override fun areItemsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
            return oldItem == newItem
        }

    }
     private val differ = AsyncListDiffer(this,diffCallback)
    var searchResults: List<GifModel>
    get() =differ.currentList
    set(value) =differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifVh {
        return GifVh(ItemGifBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GifVh, position: Int) {
        glide.asGif()
            .load(searchResults[position].previewUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.itemBinding.gifItemIv)
        holder.itemView.setOnClickListener{
            if (this::onItemCallBack.isInitialized){
                onItemCallBack.invoke(searchResults[position])
            }
        }
    }

    override fun getItemCount(): Int = searchResults.size
}











