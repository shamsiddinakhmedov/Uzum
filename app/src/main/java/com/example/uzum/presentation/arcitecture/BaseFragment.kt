package com.example.uzum.presentation.arcitecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.*

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun <S, T> StateFlow<S>.collect(collector: (T) -> Unit, mapper: (S) -> T) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            map { mapper(it) }.distinctUntilChanged().collect { collector(it) }
        }
    }

    fun <E> Flow<E>.collectEffect(collector: (E) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            collect {
                collector(it)
            }
        }
    }
}