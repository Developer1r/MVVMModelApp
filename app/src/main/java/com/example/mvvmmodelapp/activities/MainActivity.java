package com.example.mvvmmodelapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmmodelapp.R;
import com.example.mvvmmodelapp.fragstates.MovieFragState;
import com.example.mvvmmodelapp.managers.ViewModelManager;
import com.example.mvvmmodelapp.models.MovieModel;
import com.example.mvvmmodelapp.others.Constants;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private MovieModel movieModel;

    private final Observer<MovieFragState> mObserver = movieFragState -> {
        if (movieFragState != null) {
            if (!movieFragState.isHasBeenViewed()) {
                if (movieFragState.isOk()) {
                    switch (movieFragState.getState()) {
                        case Constants.MOVIES:
                            movieModel = ViewModelManager.instance.getMovieViewModel().getMovieModels();

                            setData();
                            break;
                    }
                } else {
                    switch (movieFragState.getStateErrorCode()) {
                        case Constants.RESPONSE_ERROR:
                            Toast.makeText(this, "Response Error", Toast.LENGTH_LONG).show();
                            break;
                    }
                }

                movieFragState.setHasBeenViewed(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ViewModelManager.instance.getMovieViewModel().getHelper().getMovieFragStateMutableLiveData().removeObserver(mObserver);
    }

    private void initUI() {
        ViewModelManager.instance.init(this);

        ViewModelManager.instance.getMovieViewModel().getHelper().getMovieFragStateMutableLiveData().observe(this, mObserver);

        ViewModelManager.instance.getMovieViewModel().getHelper().getMovies();

        tvTitle = findViewById(R.id.tv_title);
    }

    private void setData() {
        if (movieModel != null) {
            tvTitle.setText(movieModel.getResults().get(0).getTitle());
        }
    }

}
