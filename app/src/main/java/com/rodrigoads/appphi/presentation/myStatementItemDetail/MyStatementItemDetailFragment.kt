package com.rodrigoads.appphi.presentation.myStatementItemDetail

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.rodrigoads.appphi.R
import com.rodrigoads.appphi.databinding.FragmentMyStatementItemDetailBinding
import com.rodrigoads.appphi.model.MyStatementItemDetail
import com.rodrigoads.appphi.utils.ShareStatementItem
import com.rodrigoads.appphi.utils.currencyFormat
import com.rodrigoads.appphi.utils.dateAndTimeFormat

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStatementItemDetailFragment : Fragment() {
    private lateinit var binding: FragmentMyStatementItemDetailBinding
    private val myStatementItemDetailViewModel: MyStatementItemDetailViewModel by viewModels()

    private val args: MyStatementItemDetailFragmentArgs by navArgs()
    private lateinit var shareStatementItem: ShareStatementItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStatementItemDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getClicks()

        myStatementItemDetailViewModel.loadMyStatementItemDetailRequest.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    binding.flipperStatementItem.displayedChild = FLIPPER_CHILD_LOAD_STATE
                }
            })

        myStatementItemDetailViewModel.myStatementItemDetail.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setDetails(it)
                binding.flipperStatementItem.displayedChild = FLIPPER_CHILD_STATEMENT_ITEM
            }
        })

        myStatementItemDetailViewModel.myStatementItemDetailErrorRequest.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    binding.flipperStatementItem.displayedChild = FLIPPER_CHILD_ERROR_STATE
                }
            })
    }

    private fun getClicks() {
        binding.includeItemStatementDetail.buttonShare.setOnClickListener {
            shareStatementItem = ShareStatementItem()
            val file =
                shareStatementItem.getScreenshot(
                    binding.includeItemStatementDetail.statementDetailShare,
                    requireContext()
                )
            val uriToImage = FileProvider.getUriForFile(
                requireContext(),
                "com.rodrigoads.appphi.fileprovider",
                file
            )

            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uriToImage)
                type = "image/png"
            }
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.clipData = ClipData.newRawUri("", uriToImage)
            startActivity(Intent.createChooser(shareIntent, R.string.receipt.toString()))
        }

        binding.includeViewMyStatementErrorState.buttonTryAgain.setOnClickListener {
            myStatementItemDetailViewModel.myStatementDetail(args.id, true)
        }
    }

    override fun onStart() {
        super.onStart()
        myStatementItemDetailViewModel.myStatementDetail(args.id)
    }


    private fun setDetails(myStatementItemDetail: MyStatementItemDetail) {
        binding.includeItemStatementDetail.textViewTypeOfMovement.text =
            myStatementItemDetail.description

        binding.includeItemStatementDetail.textViewValue.text =
            myStatementItemDetail.amount.currencyFormat()

        binding.includeItemStatementDetail.textViewTo.text =
            myStatementItemDetail.to.let { if (it.isNullOrEmpty()) getString(R.string.your_account) else it }

        if (myStatementItemDetail.from.isNullOrEmpty()) {
            binding.includeItemStatementDetail.textViewFrom.isVisible = false
            binding.includeItemStatementDetail.textViewTitleFrom.isVisible = false
        } else {
            binding.includeItemStatementDetail.textViewFrom.text =
                myStatementItemDetail.from
        }

        binding.includeItemStatementDetail.textViewFrom.text

        binding.includeItemStatementDetail.textViewBankingInstitution.text =
            myStatementItemDetail.bankName.let { if (it.isNullOrEmpty()) getString(R.string.not_identified) else it }

        binding.includeItemStatementDetail.textViewDateAndTime.text =
            myStatementItemDetail.createdAt.dateAndTimeFormat()

        binding.includeItemStatementDetail.textViewAuthentication.text =
            myStatementItemDetail.authentication

    }

    companion object {
        const val FLIPPER_CHILD_LOAD_STATE = 0
        const val FLIPPER_CHILD_STATEMENT_ITEM = 1
        const val FLIPPER_CHILD_ERROR_STATE = 2
    }
}