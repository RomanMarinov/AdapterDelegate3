package com.dev_marinov.adapterdelegate3.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev_marinov.adapterdelegate3.*
import com.dev_marinov.adapterdelegate3.data.ListItem
import com.dev_marinov.adapterdelegate3.databinding.FragmentMainBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter


class MainFragment : Fragment(R.layout.fragment_main) {

    // если я использую такую запись то методon CreateView не нужен
    // оставляем только onViewCreated
    private val binding by viewBinding { FragmentMainBinding.bind(it) }

    lateinit var viewModel: MainScreenViewModel


    // это вертикальый адаптер
    private val adapter = ListDelegationAdapter<List<ListItem>>(
        // gamesHorizontalDelegate отвечает за что чтобы отрисать вью гор. в верт.
        MainScreenDelegates.gamesHorizontalDelegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainScreenViewModel::class.java]

//        binding.shimmer.visibility = View.VISIBLE
  //      binding.shimmer.startShimmer()


        with(binding) {
            recyclerView.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner) {
                adapter.apply {
                    it?.let { listListItem ->
                    Log.d("333", " listListItem=" + listListItem)
//                        binding.shimmer.stopShimmer()
//                        binding.shimmer.visibility = View.GONE
                        items = listListItem
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}