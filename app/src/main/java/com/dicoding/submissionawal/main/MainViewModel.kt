package com.dicoding.submissionawal.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionawal.api.ApiConfig
import com.dicoding.submissionawal.response.GithubResponse
import com.dicoding.submissionawal.response.ItemsItem
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response


class MainViewModel : ViewModel() {
    private val _listReview = MutableLiveData<List<ItemsItem?>>()
    val listReview: LiveData<List<ItemsItem?>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    init{
        searchUser(USER_ID)
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.items as List<ItemsItem?>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER_ID = "rifai"
    }
}

