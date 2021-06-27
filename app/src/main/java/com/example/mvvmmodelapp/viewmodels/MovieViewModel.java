package com.example.mvvmmodelapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.mvvmmodelapp.helpers.MovieHelper;
import com.example.mvvmmodelapp.models.MovieModel;

public class MovieViewModel extends AndroidViewModel {

    private MovieHelper movieHelper;
    private MovieModel movieModels;

    public MovieViewModel(Application application) {
        super(application);
    }

    public MovieHelper getHelper() {
        if (movieHelper == null) {
            movieHelper = new MovieHelper();
        }
        return movieHelper;
    }

    public MovieModel getMovieModels() {
        return movieModels;
    }

    public void setMovieModels(MovieModel movieModels) {
        this.movieModels = movieModels;
    }

}
