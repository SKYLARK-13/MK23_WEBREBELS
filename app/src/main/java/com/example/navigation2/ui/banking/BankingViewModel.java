package com.example.navigation2.ui.banking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BankingViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public BankingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Banking fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
