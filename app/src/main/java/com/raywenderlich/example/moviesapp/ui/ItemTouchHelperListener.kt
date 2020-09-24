package com.raywenderlich.example.moviesapp.ui

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {

    fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int)
}