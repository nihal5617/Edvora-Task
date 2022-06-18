package com.example.edvora_task.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edvora_task.R
import com.example.edvora_task.adapter.RidesAdapter
import com.example.edvora_task.model.Rides
import com.example.edvora_task.model.RidesItem
import com.example.edvora_task.ui.MainActivity
import com.example.edvora_task.ui.RidesViewModel
import com.example.edvora_task.util.Resource
import kotlinx.android.synthetic.main.fragment_nearest.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

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
                        val ridesResponseNew=getRidesResponse(ridesResponse)
                        ridesAdapter.differ.submitList(ridesResponseNew)
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
    private fun getRidesResponse(rides: Rides):List<RidesItem>{
//        val current = LocalDate.now()
//        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
//        val formatted = current.format(formatter)
        val formatted = 20220619
        var myList:Rides = Rides()
        Log.e("Here", formatted.toString())
        for(i in rides.iterator()){
            var new = i.date!!.substring(0,10)
            var n = new.split('/')
            var str = n[2]+n[0]+n[1]
            var num = str.toInt()
            if(num < formatted){
                myList.add(i)
            }
        }
        val sortedList = myList.sortedBy {
            it.date
        }
        return sortedList
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