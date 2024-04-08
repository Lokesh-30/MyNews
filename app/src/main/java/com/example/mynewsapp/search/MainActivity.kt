package com.example.mynewsapp.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.paging.NewsPagingAdapter
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.utils.Constants
import com.example.mynewsapp.utils.checkForInternet
import com.example.mynewsapp.utils.hide
import com.example.mynewsapp.utils.observe
import com.example.mynewsapp.utils.show
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsPagingAdapter
    private lateinit var mainBinding: ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        observe(newsViewModel.errorHandler, ::errorHandler)
        initViews()
        hitNewsApi()
    }

    private fun errorHandler(error: String) {
        if (error == Constants.Error.OK) {
            mainBinding.apply {
                clError.hide()
                rvNewsList.show()
            }
            return
        }
        error.showError()
    }

    /**
     * It handles the initializing and binding of the required views
     */
    private fun initViews() {
        newsAdapter = NewsPagingAdapter(this)
        mainBinding.rvNewsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        /**
         * Handles the population of multiple inputs and returns only the final input
         */
        val disposable = mainBinding.etSearch.textChanges()
            .map(CharSequence::toString)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                newsAdapter.submitData(lifecycle, PagingData.empty())
                hitNewsApi(text)
            }

        compositeDisposable.add(disposable)
    }

    /**
     * Api to get the News data
     */
    private fun hitNewsApi(search: String = "") {
        if (checkForInternet(this))
            newsViewModel.getNews(search).observe(this) {
                newsAdapter.submitData(lifecycle, it)
            }
        else Constants.Error.NO_INTERNET.showError()
    }

    private fun String.showError() {
        mainBinding.apply {
            rvNewsList.hide()
            clError.show()
            tvError.text = this@showError
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}