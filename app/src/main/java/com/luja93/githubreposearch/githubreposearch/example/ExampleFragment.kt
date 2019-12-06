package com.luja93.githubreposearch.githubreposearch.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.common.mvvm.ResourceStateObserver

class ExampleFragment : BaseFragment() {

    private val viewModel: ExampleViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
        viewModel.searchRepositories("Tetris")
    }

    private fun bindUI() {
        viewModel.repositories.observe(viewLifecycleOwner, ResourceStateObserver(this, {
            it?.let {
                // TODO
            }
        }))
    }
}
