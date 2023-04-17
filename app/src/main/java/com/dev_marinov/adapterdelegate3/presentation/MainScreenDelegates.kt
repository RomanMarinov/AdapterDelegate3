package com.dev_marinov.adapterdelegate3.presentation


import com.dev_marinov.adapterdelegate3.data.*
import com.dev_marinov.adapterdelegate3.databinding.ItemGameThinBinding
import com.dev_marinov.adapterdelegate3.databinding.ItemGameWideBinding
import com.dev_marinov.adapterdelegate3.databinding.ItemGamesHorizontalBinding
import com.dev_marinov.adapterdelegate3.databinding.ItemProgressThinBinding
import com.dev_marinov.adapterdelegate3.databinding.ItemProgressWideBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding


// https://github.com/sockeqwe/AdapterDelegates
// https://github.com/sockeqwe/AdapterDelegates/blob/master/kotlin-dsl-viewbinding/src/main/java/com/hannesdorfmann/adapterdelegates4/dsl/ViewBindingListAdapterDelegateDsl.kt

object MainScreenDelegates {

    val gamesHorizontalDelegate =
        adapterDelegateViewBinding<GamesHorizontalItem, ListItem, ItemGamesHorizontalBinding>({ inflater, container ->
            ItemGamesHorizontalBinding.inflate(inflater, container, false)
        }) {
//        binding.titleTextView.setOnClickListener {
//            itemClickedListener(item)
//        }
            // onCreateViewHolder регистарция делегатов
            binding.recyclerView.adapter = ListDelegationAdapter(
                wideGameDelegate,
                thinGameDelegate,
                progressWideGameDelegate,
                progressThinGameDelegate
            )
            // onBindViewHolder
            bind {
                binding.titleTextView.text = item.title
                (binding.recyclerView.adapter as? ListDelegationAdapter<List<ListItem>>)?.apply {
                    items = item.games
                    notifyDataSetChanged()
                }
            }

            // onViewRecycled для освобождения ресурсов, когда вью в layoutManager была освобождена
            // мы проскролили, она уже не видна, должна переиспользоваться, она освбождается и вызывается onViewRecycled
            // И если мы используем handlers, callbacks, glide, которые нужно отменять если view стала не видима
            // чтобы не допускать утечек или нагрузить код бесполезной фоновой работой, когда view стала не нужна
            onViewRecycled {
                // что-то для освобождения ресурсов
            }
        }

    private val wideGameDelegate =
        adapterDelegateViewBinding<GameWideItem, ListItem, ItemGameWideBinding>(
            { inflater, container -> ItemGameWideBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.imageView.setBackgroundColor(item.hashCode())
                binding.title = item.title
                binding.executePendingBindings()
            }
        }

    private val thinGameDelegate =
        adapterDelegateViewBinding<GameThinItem, ListItem, ItemGameThinBinding>(
            { inflater, container -> ItemGameThinBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.imageView.setBackgroundColor(item.hashCode())
                binding.title = item.title
                binding.executePendingBindings()
            }
        }

    private val progressWideGameDelegate =
        adapterDelegateViewBinding<ProgressWideItem, ListItem, ItemProgressWideBinding>(
            { inflater, container -> ItemProgressWideBinding.inflate(inflater, container, false) }
        ) {

        }

    private val progressThinGameDelegate =
        adapterDelegateViewBinding<ProgressThinItem, ListItem, ItemProgressThinBinding>(
            { inflater, container -> ItemProgressThinBinding.inflate(inflater, container, false) }
        ) {

        }

}