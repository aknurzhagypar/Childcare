package kz.aknur.newchildcare.content.home.articles.details

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_article_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick

class ArticleActivity : AppCompatActivity() {


    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        lets()
    }

    private fun lets() {
        prepare()
        setupListeners()
        observer()
    }

    private fun prepare() {
        viewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        if (intent.hasExtra("articleId")) {
            val articleId = intent.getStringExtra("articleId")
            getArticles(articleId)
        }
    }


    private fun getArticles(articleId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getArticle(articleId)
        }
    }

    private fun observer() {
        viewModel.isError.observe(this, Observer {
            errorDialog(it)
        })

        viewModel.article.observe(this, Observer {
            if (it != null) {
                initArticle(it)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })
    }

    private fun initArticle(it: ArticlesModel) {
        if (it.icon != null) {
            Glide.with(this)
                .load(it.icon)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(article_details_image)
        }
        article_details_title.text = it.topic
        article_details_description.text = Html.fromHtml(it.text)

    }

    private fun setupListeners() {
        article_details_favorite.onClick {

        }

    }

    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }.show()
    }


}