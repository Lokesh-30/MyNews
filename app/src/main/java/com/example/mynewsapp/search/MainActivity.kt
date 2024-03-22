package com.example.mynewsapp.search

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.paging.NewsPagingAdapter
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.utils.Constants
import com.example.mynewsapp.utils.checkForInternet
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsPagingAdapter
    private lateinit var mainBinding: ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        initViews()
        hitNewsApi()
    }

    /**
     * It handles the initializing and binding of the required views
     */
    @SuppressLint("CheckResult")
    private fun initViews() {
        adapter = NewsPagingAdapter(this)
        mainBinding.rvNewsList.layoutManager = LinearLayoutManager(this)
        mainBinding.rvNewsList.setHasFixedSize(true)
        mainBinding.rvNewsList.adapter = adapter

        /**
         * Handles the population of multiple inputs and returns only the final input
         */
        val disposable = mainBinding.etSearch.textChanges()
            .map(CharSequence::toString)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
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
                adapter.submitData(lifecycle, it)
            }
        else Toast.makeText(this, Constants.Error.NO_INTERNET, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}