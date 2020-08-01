package com.example.navigation2.ui.pension;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PensionViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public PensionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Pension fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
