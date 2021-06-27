package com.example.mvvmmodelapp.helpers;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmmodelapp.fragstates.MovieFragState;
import com.example.mvvmmodelapp.managers.ViewModelManager;
import com.example.mvvmmodelapp.models.MovieModel;
import com.example.mvvmmodelapp.others.Constants;
import com.example.mvvmmodelapp.retrofit.GetDataService;
import com.example.mvvmmodelapp.retrofit.RetrofitClientInstance;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieHelper {

    private final MutableLiveData<MovieFragState> movieFragStateMutableLiveData;

    public MovieHelper() {
        this.movieFragStateMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<MovieFragState> getMovieFragStateMutableLiveData() {
        return movieFragStateMutableLiveData;
    }

    public void getMovies() {
        GetDataService apiService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        apiService.getAllMovies("/3/search/movie?/&query=movie&api_key=" + Constants.API_KEY + "&language=en-US&").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MovieModel movieModel = new Gson().fromJson(response.body().string(), MovieModel.class);

                            ViewModelManager.instance.getMovieViewModel().setMovieModels(movieModel);

                            postPositiveResponse(Constants.MOVIES);
                        }
                    } else {
                        postNegativeResponse(Constants.RESPONSE_ERROR, Constants.API_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                postNegativeResponse(Constants.RESPONSE_ERROR, Constants.API_ERROR);
            }
        });
    }

    public void postPositiveResponse(String state) {
        MovieFragState fragState = new MovieFragState();
        fragState.setState(state);
        fragState.setOk(true);
        fragState.setShowWait(false);
        movieFragStateMutableLiveData.postValue(fragState);
    }

    public void postNegativeResponse(int errorCode, String errorMsg) {
        MovieFragState fragState = new MovieFragState();
        fragState.setState(Constants.API_ERROR);
        fragState.setOk(false);
        fragState.setStateErrorCode(errorCode);
        fragState.setErrorMsg(errorMsg);
        fragState.setShowWait(false);
        movieFragStateMutableLiveData.postValue(fragState);
    }

}
