package com.example.newsfeedapp.fragment

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeedapp.R
import com.example.newsfeedapp.adapter.NewsAdapter
import com.example.newsfeedapp.model.Articles
import com.example.newsfeedapp.model.RecyclerOnClick
import com.example.newsfeedapp.presenter.NewsInteractor
import com.example.newsfeedapp.presenter.NewsPresenter
import com.example.newsfeedapp.retrofit.RetrofitAPIClient
import com.example.newsfeedapp.retrofit.RetrofitAPIInterface


class NewsFeedFragment:Fragment(),NewsInteractor.NewsView,RecyclerOnClick {

    lateinit var rvNewsFeedView:RecyclerView
    lateinit var apiInterface:RetrofitAPIInterface
    lateinit var newsPresenter:NewsPresenter
    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View = inflater.inflate(R.layout.fragment_news_feed, null)
        initialize(view)

        newsPresenter = NewsPresenter()
        newsPresenter.setView(this)
        newsPresenter.fetchLatestNewsInfo(apiInterface)

        return view
    }

    private fun initialize(view: View){
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage(requireActivity().resources.getString(R.string.loading))
        progressDialog.setCancelable(false)
        rvNewsFeedView = view.findViewById(R.id.rvNewsFeedView)
        rvNewsFeedView.layoutManager = LinearLayoutManager(requireActivity())
        apiInterface = RetrofitAPIClient.getClientInstance().create(RetrofitAPIInterface::class.java)
    }

    override fun updateView(articleList: ArrayList<Articles>) {
        hideDialog()
        var newsAdapter = NewsAdapter(requireActivity(), articleList, this)
        rvNewsFeedView.adapter = newsAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) =
                newsAdapter.removeAt(h.adapterPosition)
        }).attachToRecyclerView(rvNewsFeedView)
    }

    override fun showDialog() {
        progressDialog.show()
    }

    override fun hideDialog() {
        progressDialog.hide()
    }

    override fun onClick(article: Articles) {
        showPopUpDialog(article)
    }

    private fun showPopUpDialog(article: Articles){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_view)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER

        dialog.window?.attributes = lp

        dialog.setCancelable(true)

        val tvAuthorName = dialog.findViewById<TextView>(R.id.tvAuthorName)
        val tvLink = dialog.findViewById<TextView>(R.id.tvLink)
        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = dialog.findViewById<TextView>(R.id.tvDescription)
        val tvPublishedAt = dialog.findViewById<TextView>(R.id.tvPublishedDate)
        val tvSourceName = dialog.findViewById<TextView>(R.id.tvSourceName)
        val imageView = dialog.findViewById<ImageView>(R.id.ivImage)
        val tvContent = dialog.findViewById<TextView>(R.id.tvContent)
        val btnClose = dialog.findViewById<TextView>(R.id.btnClose)

        tvAuthorName.text = "Author Name : " + article.author
        tvLink.text = "Link : " + article.url
        tvTitle.text = "Title : " + article.title
        tvDescription.text = "Description : " + article.description
        tvPublishedAt.text = "Published At : " + article.publishedAt
        tvSourceName.text = "Source Name : " + article.source.name
        tvContent.text = "Content : " + article.content

        Glide.with(this).load(article.urlToImage)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .into(imageView)

        btnClose.setOnClickListener{
            dialog.cancel()
        }

        dialog.show()

    }


}