package com.example.hw9_final.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Powered by TMDB\nDeveloped by Danny Diaz Ayon");
    }

    public LiveData<String> getText() {
        return mText;
    }
}