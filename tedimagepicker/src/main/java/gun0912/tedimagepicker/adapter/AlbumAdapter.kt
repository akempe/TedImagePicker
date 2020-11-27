package gun0912.tedimagepicker.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import gun0912.tedimagepicker.R
import gun0912.tedimagepicker.base.BaseCustomButtonAdapter
import gun0912.tedimagepicker.base.BaseRecyclerViewAdapter
import gun0912.tedimagepicker.base.BaseViewHolder
import gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder
import gun0912.tedimagepicker.databinding.ItemAlbumBinding
import gun0912.tedimagepicker.databinding.ItemGalleryCameraBinding
import gun0912.tedimagepicker.model.Album
import gun0912.tedimagepicker.util.TextFormatUtil

internal class AlbumAdapter(private val builder: TedImagePickerBaseBuilder<*>) :
    BaseCustomButtonAdapter<Album>() {

    private var selectedPosition = 0

    override fun getItemViewHolder(parent: ViewGroup) = AlbumViewHolder(parent)

    override fun getHeaderViewHolder(parent: ViewGroup) = HeaderViewHolder<ItemGalleryCameraBinding>(parent, R.layout.item_gallery_camera)

    override fun getCustomButtonViewHolder(parent: ViewGroup) = CustomBtnViewHolder (parent)

    fun setSelectedAlbum(album: Album) {
        val index = items.indexOf(album)
        if (index >= 0 && selectedPosition != index) {
            val lastSelectedPosition = selectedPosition
            selectedPosition = index
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class AlbumViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemAlbumBinding, Album>(parent, R.layout.item_album) {
        override fun bind(data: Album) {
            binding.album = data
            binding.isSelected = adapterPosition == selectedPosition
            binding.mediaCountText =
                TextFormatUtil.getMediaCountText(builder.imageCountFormat, data.mediaCount)
        }

        override fun recycled() {
            Glide.with(itemView).clear(binding.ivImage)
        }
    }


}