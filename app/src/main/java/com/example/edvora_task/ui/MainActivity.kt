package com.example.edvora_task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.edvora_task.R
import com.example.edvora_task.repository.RidesRepository
import com.example.edvora_task.ui.fragments.NearestFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: RidesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository =RidesRepository()
        val viewModelProviderFactory=RidesViewModelProviderFactory(repository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(RidesViewModel::class.java)
//        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        supportFragmentManager.beginTransaction().replace(R.id.fvFragment,NearestFragment()).commit()
    }
}