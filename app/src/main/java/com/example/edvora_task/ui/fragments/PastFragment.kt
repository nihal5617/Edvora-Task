package com.example.edvora_task.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edvora_task.R
import com.example.edvora_task.adapter.RidesAdapter
import com.example.edvora_task.ui.MainActivity
import com.example.edvora_task.ui.RidesViewModel
import com.example.edvora_task.util.Resource
import kotlinx.android.synthetic.main.fragment_nearest.*

class PastFragment : Fragment(R.layout.fragment_past) {

    lateinit var viewModel: RidesViewModel
    lateinit var ridesAdapter: RidesAdapter
    val TAG = "PastFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setUpRecyclerView()

        viewModel.rides.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { ridesResponse ->
                        ridesAdapter.differ.submitList(ridesResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG,"An error occurred : $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }
    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }
    private fun setUpRecyclerView(){
        ridesAdapter = RidesAdapter()
        rvRides.apply{
            adapter=ridesAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}