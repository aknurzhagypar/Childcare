package kz.aknur.newchildcare.content.home.articles.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel
import java.lang.Exception

class ArticleViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "ArticleViewModel"
    }

    private var repository: ArticleRepository =
        ArticleRepository(
            application
        )

    val article: MutableLiveData<ArticlesModel> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()

    fun getArticle(articleId: String){
        viewModelScope.launch {
            try {
                article.postValue(repository.getArticle(articleId))
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}