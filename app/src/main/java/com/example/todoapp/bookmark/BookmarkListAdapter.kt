package com.example.todoapp.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemBookmarkBinding
import com.example.todoapp.todo.home.TodoModel

class BookmarkListAdapter(
    val switchClickListener: (BookmarkModel) -> Unit
) :
    ListAdapter<BookmarkModel, BookmarkListAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<BookmarkModel>() {
        override fun areItemsTheSame(oldItem: BookmarkModel, newItem: BookmarkModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookmarkModel, newItem: BookmarkModel): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookmarkModel) = with(binding) {
            txtBookmarkTitle.text = item.title
            txtBookmarkContent.text = item.content
            switchBookmark.isChecked = item.isSwitch
            switchBookmark.setOnClickListener {
                switchClickListener(item)
            }
        }
    }
}