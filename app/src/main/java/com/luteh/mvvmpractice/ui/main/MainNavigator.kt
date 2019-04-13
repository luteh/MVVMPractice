package com.luteh.mvvmpractice.ui.main

import android.content.Intent

/**
 * Created by Luthfan Maftuh on 13/04/2019.
 * Email luthfanmaftuh@gmail.com
 */
interface MainNavigator {
    fun onInit()

    fun initRecyclerView()

    fun initViewModel()

    fun onClickComponent()

    fun onAddNoteResult(data: Intent)

    fun onEditNoteResult(data: Intent)
}