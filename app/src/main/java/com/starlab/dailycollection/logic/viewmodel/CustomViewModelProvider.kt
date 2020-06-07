package com.starlab.dailycollection.logic.viewmodel

import android.content.Context
import com.starlab.dailycollection.logic.base.RepositoryProvider
import com.starlab.dailycollection.logic.viewmodel.factory.*

object CustomViewModelProvider {


    fun getHomeViewModel(context: Context) = HomeViewModelFactory(RepositoryProvider.getDCollectioonRepository(context))

    fun getSearchViewModel(context: Context) = SearchViewModelFactory(RepositoryProvider.getDCollectioonRepository(context))

    fun getCreateViewModel(context: Context) = CreateViewModelFactory(RepositoryProvider.getDCollectioonRepository(context))

    fun getDetailsViewModel(context: Context) = DetailsViewModelFactory(RepositoryProvider.getDCollectioonRepository(context))

    fun getUserViewModel(context: Context) = UserViewModelFactory(RepositoryProvider.getUserRepository(context))

}