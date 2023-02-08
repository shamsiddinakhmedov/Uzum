package com.example.uzum.presentation.view.fragments.add_card

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.uzum.databinding.FragmentAddCardBinding
import com.example.uzum.presentation.arcitecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {

    private val viewModel:AddCardViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        viewModel.state.collect(this::isEnable) {
            it.isEnabled
        }
    }

    private fun initUi() = with(binding) {
        cardEt.doAfterTextChanged { text ->
            viewModel.processInput(input = AddCardViewModel.Input.SetCardNumber(cardEt.text.toString()))

            val formattedText = text.toString().replace(" ", "").chunked(4).joinToString(" ")

            if (formattedText != text.toString()) {
                cardEt.setText(formattedText)

                cardEt.setSelection(cardEt.length())
            }
        }

        dateEt.doAfterTextChanged { text ->
            viewModel.processInput(input = AddCardViewModel.Input.SetCardEDate(dateEt.text.toString()))

            val formattedText = text.toString().replace("/", "").chunked(2).joinToString("/")

            if (formattedText != text.toString()) {
                dateEt.setText(formattedText)

                dateEt.setSelection(dateEt.length())
            }
        }

        continueBtn.setOnClickListener {
            viewModel.processInput(AddCardViewModel.Input.AddCard)
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun isEnable(isEnable: Boolean) = with(binding) {
        continueBtn.isEnabled = isEnable
    }
}