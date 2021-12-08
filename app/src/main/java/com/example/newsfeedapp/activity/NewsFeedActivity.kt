package com.example.newsfeedapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.newsfeedapp.R
import com.example.newsfeedapp.adapter.PageSwipeAdapter
import com.example.newsfeedapp.fragment.NewsFeedFragment
import com.example.newsfeedapp.fragment.ProfileFragment
import com.example.newsfeedapp.presenter.NewsFeedPresenter
import com.example.newsfeedapp.sqlite.DBHandler
import com.google.android.material.tabs.TabLayout

class NewsFeedActivity : AppCompatActivity() {

    lateinit var tvLoginUserName:TextView
    lateinit var tabs:TabLayout
    lateinit var viewPager:ViewPager
    lateinit var toolBar:Toolbar
    lateinit var newsFeedPresenter: NewsFeedPresenter
    lateinit var db:DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        initialize()

    }

    private fun initialize(){
        db = DBHandler(this)
        tvLoginUserName = findViewById(R.id.tv_login_username)
        toolBar = findViewById(R.id.toolbar)
        tabs = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)
        setSupportActionBar(toolBar)
        newsFeedPresenter = NewsFeedPresenter()
        tvLoginUserName.text = "Welcome " + newsFeedPresenter.getUserName(db)

        setUpViewPager(viewPager)
    }
    private fun setUpViewPager(viewPager: ViewPager){
        var adapter = PageSwipeAdapter(supportFragmentManager)

        adapter.addFragment(NewsFeedFragment(), resources.getString(R.string.news_menu))
        adapter.addFragment(ProfileFragment(), resources.getString(R.string.profile_menu))

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}