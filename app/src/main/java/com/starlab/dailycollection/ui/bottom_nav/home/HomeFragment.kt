package com.starlab.dailycollection.ui.bottom_nav.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        CustomViewModelProvider.getHomeViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = GridLayoutManager(activity, 2)
        homeRecyclerView.layoutManager = layoutManager
        val adapter = HomeAdapter(this, homeViewModel.dCollectionList)
        homeRecyclerView.adapter = adapter
        homeViewModel.dCollectionLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.dCollectionList.clear()
                homeViewModel.dCollectionList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        swipeCollection.setColorSchemeResources(R.color.theme_color)
        swipeCollection.setProgressViewEndTarget(false, 300)
        swipeCollection.setOnRefreshListener {
            refreshCollection(adapter)
        }
    }

    private fun refreshCollection(adapter: HomeAdapter) {
        thread {
            Thread.sleep(500)
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
                swipeCollection?.isRefreshing = false
            }
        }
    }

}
