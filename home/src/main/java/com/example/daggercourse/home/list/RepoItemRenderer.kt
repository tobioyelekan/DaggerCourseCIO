package com.example.daggercourse.home.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daggercourse.home.HomeViewModel
import com.example.daggercourse.home.R
import com.example.daggercourse.home.databinding.RepoItemBinding
import com.example.daggercourse.poweradapter.ItemRenderer
import javax.inject.Inject

class RepoItemRenderer @Inject constructor(
    private val homeViewModel: HomeViewModel
) : ItemRenderer<RepoItem> {
    override fun layoutRes() = R.layout.repo_item

    override fun createView(parent: ViewGroup): View {
        val binding = RepoItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        val viewBinder = RepoItemViewBinder(binding, homeViewModel)
        binding.root.tag = viewBinder
        return binding.root
    }

    override fun render(itemView: View, item: RepoItem) {
        (itemView.tag as RepoItemViewBinder).bind(item)
    }
}

class RepoItemViewBinder(
    private val binding: RepoItemBinding,
    private val homeViewModel: HomeViewModel
) : RecyclerView.ViewHolder(binding.root) {

    private var repoItem: RepoItem? = null

    init {
        itemView.setOnClickListener {
            Log.d("DAGGER COURSE", "GOT HERE!! ${repoItem?.ownerName} ${repoItem?.name}")
            repoItem?.let { repo -> homeViewModel.onRepoSelected(repo.ownerName, repo.name) }
        }
    }

    fun bind(repoItem: RepoItem) {
        this.repoItem = repoItem
        binding.repoName.text = repoItem.name
        binding.repoDescription.text = repoItem.description
        binding.starsCount.text = "${repoItem.starsCount}"
        binding.forkCount.text = "${repoItem.forkCount}"
    }
}