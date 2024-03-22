package com.example.mynewsapp.search

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.paging.NewsPagingAdapter
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.example.mynewsapp.databinding.ActivityMainBinding
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

    @SuppressLint("CheckResult")
    private fun initViews() {
        adapter = NewsPagingAdapter(this)
        mainBinding.rvNewsList.layoutManager = LinearLayoutManager(this)
        mainBinding.rvNewsList.setHasFixedSize(true)
        mainBinding.rvNewsList.adapter = adapter

        val disposable = mainBinding.etSearch.textChanges()
            .map(CharSequence::toString)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                hitNewsApi(text)
            }

        compositeDisposable.add(disposable)
    }

    private fun hitNewsApi(search: String = "") {
        newsViewModel.getNews(search).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}