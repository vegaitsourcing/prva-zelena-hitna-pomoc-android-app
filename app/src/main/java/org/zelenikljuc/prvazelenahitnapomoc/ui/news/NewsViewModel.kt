package org.zelenikljuc.prvazelenahitnapomoc.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.zelenikljuc.common.models.news.News
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.common.utils.TAG
import org.zelenikljuc.domain.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<News>?>()
    val news: LiveData<List<News>?> get() = _news

    private fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews().collect{ news->
                when(news){
                    is  DataState.Loading -> {
                        Log.d(TAG, "getNews: Loading..")
                    }
                    is  DataState.Success -> {
                        Log.d(TAG, "getNews - Success: ${news.data}")
                        _news.postValue(news.data)
                    }
                    is  DataState.Error -> {
                        Log.d(TAG, "getNews - Error: ${news.exception}")
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        getNews()
    }
}