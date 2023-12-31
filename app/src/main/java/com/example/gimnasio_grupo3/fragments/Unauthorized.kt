package com.example.gimnasio_grupo3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gimnasio_grupo3.R

class Unauthorized : Fragment() {
    lateinit var v : View
    companion object {
        fun newInstance() = Unauthorized()
    }

    private lateinit var viewModel: UnauthorizedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_unauthorized, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UnauthorizedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}