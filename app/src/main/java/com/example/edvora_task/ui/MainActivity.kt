package com.example.edvora_task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.edvora_task.R
import com.example.edvora_task.repository.RidesRepository
import com.example.edvora_task.ui.fragments.NearestFragment
import com.example.edvora_task.ui.fragments.PastFragment
import com.example.edvora_task.ui.fragments.UpcomingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_ride.view.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: RidesViewModel
    private val nearestFragment=NearestFragment()
    private val pastFragment=PastFragment()
    private val upcomingFragment=UpcomingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this).load("https://picsum.photos/200").into(civUser)

        val repository =RidesRepository()
        val viewModelProviderFactory=RidesViewModelProviderFactory(repository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(RidesViewModel::class.java)
        replaceFragment(nearestFragment)
        val navigationView = findViewById<BottomNavigationView>(R.id.navigationView)
        navigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nearestFragment ->replaceFragment(nearestFragment)
                R.id.pastFragment -> replaceFragment(pastFragment)
                R.id.upcomingFragment -> replaceFragment(upcomingFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        Log.e("In replace","$fragment")
        if(fragment!=null){
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fvFragment,fragment).commit()
        }
    }
}