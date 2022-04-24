package com.example.beatoreat.uicomponents

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beatoreat.R
import com.example.beatoreat.databinding.ActivityMoviesBinding
import com.example.beatoreat.network.NetworkResult
import com.example.beatoreat.widget.MovieAdapter
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie

class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var viewModel: MoviesViewModel

    private var adapter : MovieAdapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Popular Movie"
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        initUI()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        observeViewModel()
    }

    protected fun initUI() {
        binding.listView.adapter = adapter
//        binding.listView.layoutManager = GridLayoutManager(this, 2)
        binding.listView.layoutManager = LinearLayoutManager(this)
    }

    protected fun observeViewModel() {
        viewModel.moviesResult.observe(this) {
            when (it) {
                is NetworkResult.ResponseResult<List<MutableMovie>> -> {
                    showResult("getting size ${it.value.size}")
                    val isFirstLoad = it.state == NetworkResult.ResutlState.Add
                    addMovies(it.value, isFirstLoad)
                }
                is NetworkResult.ErrorResult -> {
                    showError(it.message)
                }
                else -> {}
            }
        }
        viewModel.networkLoading.observe(this) {
            showError(if (it) "Loading ... " else "....")
        }
    }

    private fun showError(message: String) {
        binding.tvContent.text = message
    }

    private fun showResult(message: String) {
        if (viewModel.networkLoading.value == true) { return }
        binding.tvContent.text = "Result is $message"
    }

    private fun addMovies(movies: List<MutableMovie>, firstPage: Boolean) {
        adapter.addMovie(movies, firstPage)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.movie_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sorter) {
            viewModel.toggleSorter()
        } else if (item.itemId == R.id.menu_filter) {
            viewModel.toggleFavorite()
        }
        return super.onOptionsItemSelected(item)
    }
}