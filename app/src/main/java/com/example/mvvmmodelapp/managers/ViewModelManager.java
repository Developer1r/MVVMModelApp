package com.example.mvvmmodelapp.managers;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmmodelapp.viewmodels.MovieViewModel;

public class ViewModelManager {

    public static final ViewModelManager instance = new ViewModelManager();

    private MovieViewModel movieViewModel;

    public void init(FragmentActivity activity) {
        movieViewModel = new ViewModelProvider(activity).get(MovieViewModel.class);
    }

    public MovieViewModel getMovieViewModel() {
        return movieViewModel;
    }

    public void setMovieViewModel(MovieViewModel movieViewModel) {
        this.movieViewModel = movieViewModel;
    }

}
