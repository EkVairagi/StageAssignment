package com.hello.stageassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hello.stageassignment.databinding.StoryItemLayoutBinding
import com.hello.stageassignment.data.model.StoryEntity


class StoryViewAdapter(
    private val listener: (Int) -> Unit
) : ListAdapter<StoryEntity, StoryViewAdapter.StoryViewHolder>(StoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val context = holder.itemView.context
        val story = getItem(position)

        holder.binding.username.text = story.username
        Glide.with(context).load(story.userProfile).into(holder.binding.profileImageStory)

        holder.binding.frameLayout.setOnClickListener {
            listener.invoke(position)
        }
    }

    class StoryViewHolder(val binding: StoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}

class StoryDiffCallback : DiffUtil.ItemCallback<StoryEntity>() {
    override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
        return oldItem == newItem
    }
}
