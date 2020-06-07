package com.starlab.dailycollection.ui.bottom_nav.create

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.starlab.dailycollection.R
import kotlinx.android.synthetic.main.fragment_create.*

class CreateFragment : Fragment() {

    companion object {
        fun newInstance() = CreateFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createBtn.setOnClickListener {
            val intent = Intent(context, CreateActivity::class.java)
            activity?.startActivity(intent)

        }
    }


}
