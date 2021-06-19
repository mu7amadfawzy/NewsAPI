package com.tempo.news.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.tempo.news.R
import com.tempo.news.databinding.ActivityHomeBinding
import com.tempo.news.utils.Extensions.textChanges
import com.tempo.news.utils.ThemeDialogHandler
import com.tempo.news.utils.ViewModelFactory
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class HomeActivity : AppCompatActivity() {

    private lateinit var themeHandlerDialog: ThemeDialogHandler
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        configViewModel()
        initViews()
    }

    private fun initViews() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbar)
        configRecycler()
        configSearch()
        initThemeHandler()
    }


    private fun configViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())
            .get(HomeActivityViewModel::class.java)
        observeData()
    }

    private fun observeData() {
        viewModel.newsResult.observe(this) { result ->
            articlesAdapter.updateDataList(result)
            if (articlesAdapter.dataList.size == result.size)//if adapter was empty
                binding.recycler.scheduleLayoutAnimation()
        }
    }

    private fun configSearch() {
        binding.searchEt.textChanges().debounce(700)
            .onEach {
                articlesAdapter.clearData()
                viewModel.onQueryChanged(it.toString())
            }
            .launchIn(viewModel.viewModelScope)
    }

    private fun configRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
            articlesAdapter = ArticlesAdapter(this@HomeActivity, ArrayList(), viewModel)
            adapter = articlesAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme_changer -> {
                themeHandlerDialog.showThemesPickerDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initThemeHandler() {
        themeHandlerDialog =
            ThemeDialogHandler(this, callback = null, delegate)
    }


}