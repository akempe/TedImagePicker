package gun0912.tedimagepicker.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import gun0912.tedimagepicker.R
import gun0912.tedimagepicker.adapter.AlbumAdapter
import gun0912.tedimagepicker.databinding.ItemAlbumExternBinding

internal abstract class BaseCustomButtonAdapter<D>(headerCount: Int = HEADER_COUNT, customBtnsCount: Int = 1) :
        BaseRecyclerViewAdapter<D, BaseViewHolder<ViewDataBinding, D>>(headerCount, customBtnsCount) {

    abstract fun getItemViewHolder(parent: ViewGroup): BaseViewHolder<ViewDataBinding, D>
    abstract fun getHeaderViewHolder(parent: ViewGroup): HeaderViewHolder<ViewDataBinding>
    abstract fun getCustomButtonViewHolder(parent: ViewGroup): CustomBtnViewHolder


    override fun getViewHolder(
            parent: ViewGroup,
            viewType: ViewType
    ): BaseViewHolder<*, D> {
        return when (viewType) {
            ViewType.HEADER -> getHeaderViewHolder(parent)
            ViewType.ITEM -> getItemViewHolder(parent)
            ViewType.CUSTOM_BTN -> getCustomButtonViewHolder(parent)
        }
    }

    open inner class HeaderViewHolder<out B : ViewDataBinding>(parent: ViewGroup, @LayoutRes layoutRes: Int) :
            BaseViewHolder<B, D>(parent, layoutRes) {

        override fun bind(data: D) {
            // no-op
        }
    }

    inner class CustomBtnViewHolder(parent: ViewGroup) :
            BaseViewHolder<ItemAlbumExternBinding, D>(parent, R.layout.item_album_extern) {

        override fun recycled() {

        }

        override fun bind(data: D) {
            TODO("Not yet implemented")
        }
    }


    companion object {
        private const val HEADER_COUNT = 0
    }


}