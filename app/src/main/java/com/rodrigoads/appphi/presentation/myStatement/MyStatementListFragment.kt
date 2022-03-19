package com.rodrigoads.appphi.presentation.myStatement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.rodrigoads.appphi.databinding.FragmentMyStatementListBinding
import com.rodrigoads.appphi.presentation.myBalance.MyBalanceFragmentDirections
import com.rodrigoads.appphi.presentation.myBalance.MyBalanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyStatementListFragment : Fragment() {
    private lateinit var binding: FragmentMyStatementListBinding
    private val myStatementViewModel: MyStatementViewModel by activityViewModels()
    private val myBalanceViewModel: MyBalanceViewModel by activityViewModels()

    private lateinit var myStatementAdapter: MyStatementAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStatementListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatementAdapter()
        observerInitialLoadingState()

        myStatementViewModel.statementPagingData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                myStatementAdapter.submitData(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (myStatementViewModel.statementPagingData.value == null) {
            lifecycleScope.launch {
                myStatementViewModel.myStatement()
            }
        }
    }

    private fun initStatementAdapter() {
        myStatementAdapter = MyStatementAdapter(requireContext()) { onClickItem(it) }
        with(binding.recyclerViewMyStatement) {
            adapter = myStatementAdapter.withLoadStateFooter(MyStatementLoadMoreStateAdapter {
                myStatementAdapter.retry()
            })
        }
    }

    private fun observerInitialLoadingState() {
        lifecycleScope.launch {
            myStatementAdapter.loadStateFlow.collectLatest { loadState ->
                binding.flipperStatement.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }
                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_STATEMENT
                    }
                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        binding.includeViewMyStatementErrorState.buttonTryAgain.setOnClickListener {
                            myBalanceViewModel.myBalance()
                            myStatementAdapter.refresh()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewMyStatementLoadingState.shimmerStatement.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    private fun onClickItem(id: String) {
        val action =
            MyBalanceFragmentDirections.actionMyStatementFragmentToMyStatementItemDetailFragment(id)
        findNavController().navigate(action)
    }

    companion object {
        const val FLIPPER_CHILD_LOADING = 0
        const val FLIPPER_CHILD_STATEMENT = 1
        const val FLIPPER_CHILD_ERROR = 2
    }
}