package com.dev_marinov.adapterdelegate3.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.adapterdelegate3.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private val _data = MutableLiveData<List<ListItem>>()
    var data: LiveData<List<ListItem>> = _data

    init {
        getProgress()
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            val listItem = listOf(
                GamesHorizontalItem(
                    title = "wide games",
                    games = listOf(
                        GameWideItem(0, "Game 1"),
                        GameWideItem(1, "Game 2"),
                        GameWideItem(2, "Game 3")
                    )
                ),
                GamesHorizontalItem(
                    title = "thin games",
                    games = listOf(
                        GameThinItem(0, "Game 1"),
                        GameThinItem(1, "Game 2"),
                        GameThinItem(2, "Game 3")
                    )
                ),
                GamesHorizontalItem(
                    title = "wide games",
                    games = listOf(
                        GameWideItem(0, "Game 1"),
                        GameWideItem(1, "Game 2"),
                        GameWideItem(2, "Game 3")
                    )
                )
            )
            _data.postValue(listItem)
        }
    }

    private fun getProgress() {
        val listItem = listOf(
            GamesHorizontalItem(
                title = "wide games",
                games = IntRange(1, 2).map { ProgressWideItem }
            ),
            GamesHorizontalItem(
                title = "thin games",
                games = IntRange(1, 3).map { ProgressThinItem }
            ),
            GamesHorizontalItem(
                title = "wide games",
                games = IntRange(1, 3).map { ProgressWideItem }
            )

        )
        _data.postValue(listItem)
    }
}