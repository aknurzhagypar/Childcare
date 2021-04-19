package kz.aknur.newchildcare.content.favorite_articles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import java.lang.Exception

class FavouriteArticlesViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "FavouriteArticlesViewModel"
    }

    private var repository: FavoriteArticlesRepository =
        FavoriteArticlesRepository(
            application
        )

    val categories: MutableLiveData<List<SmallCategoriesModel>> = MutableLiveData()
    val articles: MutableLiveData<List<ArticlesModel>> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()


    fun getArticles(){
        viewModelScope.launch {
            try {
                articles.postValue(repository.getArticles())
            } catch (exception: Exception) {
                isError.postValue(exception.toString())
            }
        }
    }
}