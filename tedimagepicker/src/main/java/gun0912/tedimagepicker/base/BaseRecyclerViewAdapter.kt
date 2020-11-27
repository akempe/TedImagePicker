package gun0912.tedimagepicker.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

internal abstract class BaseRecyclerViewAdapter<D, VH : BaseViewHolder<ViewDataBinding, D>>(protected var headerCount: Int = 0, protected var customBtnsCount: Int = 0) :
    RecyclerView.Adapter<VH>() {

    protected val items = mutableListOf<D>()

    var onItemClickListener: OnItemClickListener<D>? = null

    interface OnItemClickListener<D> {
        fun onItemClick(data: D, itemPosition: Int, layoutPosition: Int)

        fun onHeaderClick() {
            // no-op
        }

        fun onCustomButtonClick() {}
    }

    open fun replaceAll(items: List<D>, useDiffCallback: Boolean = false) {
        val diffCallback = BaseDiffUtilCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items.run {
            clear()
            addAll(items)
        }
        if (useDiffCallback) {
            diffResult.dispatchUpdatesTo(this)
        } else {
            notifyDataSetChanged()
        }

    }


    override fun getItemViewType(position: Int) = getViewType(position).ordinal


    protected open fun getViewType(position: Int): ViewType {
        return if (position < headerCount) {
            ViewType.HEADER
        } else if (position >= headerCount + items.size) {
            ViewType.CUSTOM_BTN
        } else {
            ViewType.ITEM
        }
    }


    abstract fun getViewHolder(parent: ViewGroup, viewType: ViewType): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return getViewHolder(parent, ViewType.getViewType(viewType)).apply {
            onItemClickListener?.let { listener ->
                itemView.setOnClickListener {
                    if (adapterPosition >= headerCount && adapterPosition < headerCount + items.size) {
                        listener.onItemClick(
                            getItem(adapterPosition),
                            getItemPosition(adapterPosition),
                            adapterPosition
                        )
                    } else if (adapterPosition < headerCount) {
                        listener.onHeaderClick()
                    } else if (adapterPosition >= items.size + headerCount) {
                        listener.onCustomButtonClick()
                    }
                }
            }
        }
    }

    private fun getItemPosition(adapterPosition: Int): Int {
        return adapterPosition - headerCount
    }


    override fun onBindViewHolder(holder: VH, position: Int) {

        when (getViewType(position)) {
            ViewType.HEADER -> {

            }
            ViewType.ITEM -> holder.bind(getItem(position))
        }
    }


    override fun onViewRecycled(holder: VH) {
        holder.recycled()
        super.onViewRecycled(holder)
    }

    fun getItemPosition(data: D) = items.indexOf(data).let { if (it < 0) it else it + headerCount }

    override fun getItemCount(): Int = items.size + headerCount + customBtnsCount

    open fun getItem(position: Int): D =
        items[getItemPosition(position)]


    enum class ViewType {
        HEADER, ITEM, CUSTOM_BTN;

        companion object {
            fun getViewType(value: Int) = values()[value]
        }
    }
}
