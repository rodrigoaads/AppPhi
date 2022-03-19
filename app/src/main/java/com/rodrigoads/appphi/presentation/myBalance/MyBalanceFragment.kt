package com.rodrigoads.appphi.presentation.myBalance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.rodrigoads.appphi.R
import com.rodrigoads.appphi.databinding.FragmentMyBalanceBinding
import com.rodrigoads.appphi.model.uiState.MyBalanceUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBalanceFragment : Fragment() {
    private lateinit var binding: FragmentMyBalanceBinding
    private val myBalanceViewModel: MyBalanceViewModel by activityViewModels()

    private var visible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBalanceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewClicks()

        myBalanceViewModel.getBalanceVisibility.observe(viewLifecycleOwner, Observer {
            getVisibility(it)
        })

        myBalanceViewModel.myBalance.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MyBalanceUiState.Success -> {
                    binding.textViewBalance.text = it.amount
                    binding.progressBarMyBalance.visibility = View.GONE
                }
                is MyBalanceUiState.Error -> {
                    binding.textViewBalance.text = getString(it.message)
                    binding.progressBarMyBalance.visibility = View.GONE
                }
                is MyBalanceUiState.Loading -> {
                    binding.progressBarMyBalance.visibility = View.VISIBLE
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        if (myBalanceViewModel.myBalance.value == null) {
            myBalanceViewModel.myBalance()
            myBalanceViewModel.getBalanceVisibility()
        }
    }

    private fun getViewClicks() {
        binding.imageViewVisibility.setOnClickListener {
            setVisibility()
        }

    }

    private fun setVisibility() {
        if (visible) {
            myBalanceViewModel.setBalanceVisibility(false)

        } else {
            myBalanceViewModel.setBalanceVisibility(true)
        }
    }

    private fun getVisibility(visible: Boolean) {
        if (visible) {
            binding.imageViewVisibility.setImageResource(R.drawable.ic_baseline_visibility_off)

            binding.viewVisibility.visibility = View.GONE
            binding.textViewBalance.visibility = View.VISIBLE
            this.visible = true
        } else {
            binding.imageViewVisibility.setImageResource(R.drawable.ic_baseline_visibility)

            binding.viewVisibility.visibility = View.VISIBLE
            binding.textViewBalance.visibility = View.GONE
            this.visible = false
        }
    }
}