package com.example.daggercourse.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggercourse.di.AppViewModelFactory
import com.example.daggercourse.home.databinding.ScreenHomeBinding
import com.example.daggercourse.poweradapter.ItemRenderer
import com.example.daggercourse.poweradapter.PowerAdapter
import com.example.daggercourse.poweradapter.RecyclerItem
import javax.inject.Inject
import javax.inject.Provider

class HomeFragment : Fragment() {

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory

    @Inject
    lateinit var itemRenderer: Map<Class<out RecyclerItem>, @JvmSuppressWildcards Provider<ItemRenderer<out RecyclerItem>>>

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, appViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ScreenHomeBinding.inflate(inflater, container, false)
        binding.repoList.apply {
            adapter = PowerAdapter(itemRenderer)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        homeViewModel.viewStateUpdates.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is HomeViewStateLoading -> handleLoadingState(binding)
                is HomeViewStateLoaded -> handledLoadedState(state, binding)
                is HomeViewStateError -> handleErrorState(state, binding)
            }
        })
        return binding.root
    }

    private fun handleLoadingState(binding: ScreenHomeBinding) {
        binding.repoList.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun handledLoadedState(state: HomeViewStateLoaded, binding: ScreenHomeBinding) {
        binding.errorTextView.visibility = View.GONE
        binding.loadingIndicator.visibility = View.GONE

        binding.repoList.visibility = View.VISIBLE
        (binding.repoList.adapter as PowerAdapter).setData(state.repos)
    }

    private fun handleErrorState(state: HomeViewStateError, binding: ScreenHomeBinding) {
        binding.repoList.visibility = View.GONE
        binding.loadingIndicator.visibility = View.GONE

        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = state.message
    }
}