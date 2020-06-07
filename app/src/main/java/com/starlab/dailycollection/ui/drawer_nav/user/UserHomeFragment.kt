package com.starlab.dailycollection.ui.drawer_nav.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import com.starlab.dailycollection.logic.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.drawer_nav_header.*
import kotlinx.android.synthetic.main.fragment_user_home.*

class UserHomeFragment : Fragment() {


    private val userViewModel: UserViewModel by viewModels {
        CustomViewModelProvider.getUserViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

}