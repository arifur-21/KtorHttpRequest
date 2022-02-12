package com.example.ktorhttprequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktorhttprequest.adapter.PostAdapter
import com.example.ktorhttprequest.databinding.ActivityMainBinding
import com.example.ktorhttprequest.network.ApiService
import com.example.ktorhttprequest.repository.MainRepository
import com.example.ktorhttprequest.ui.MainViewModel
import com.example.ktorhttprequest.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
        initRecycleView()

        mainViewModel.getPost()
        lifecycleScope.launchWhenStarted {
            mainViewModel.apiStateFlow.collect {
               binding.apply {
                   when(it){
                       is ApiState.Success->{
                           recyclerview.isVisible = true
                           error.isVisible = false
                           progressBar.isVisible = false
                           postAdapter.submitList(it.data)
                       }
                       is ApiState.Failure->{
                           recyclerview.isVisible = false
                           error.isVisible = true
                           progressBar.isVisible = false
                           error.text = it.msg.message
                       }
                       is  ApiState.Failure->{
                           recyclerview.isVisible = false
                           error.isVisible = false
                           progressBar.isVisible = true
                       }
                       is ApiState.Loading->{
                           recyclerview.isVisible = false
                           error.isVisible = false
                           progressBar.isVisible = true

                       }
                       is  ApiState.Empty->{

                       }
                   }
               }

            }
        }
    }

    private fun initRecycleView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = postAdapter
            }
        }
    }
}