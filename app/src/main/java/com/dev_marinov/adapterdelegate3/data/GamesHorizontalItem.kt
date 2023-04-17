package com.dev_marinov.adapterdelegate3.data

import com.dev_marinov.adapterdelegate3.data.ListItem

data class GamesHorizontalItem(
    val title: String,
    val games: List<ListItem>
) : ListItem