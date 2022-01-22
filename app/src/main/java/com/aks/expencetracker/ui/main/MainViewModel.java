package com.aks.expencetracker.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aks.expencetracker.data.database_models.ExpenseTable;
import com.aks.expencetracker.utils.databases.ExpenseTableDao;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@HiltViewModel
public class MainViewModel extends ViewModel {
    MutableLiveData<List<ExpenseTable>> listMutableLiveData = new MutableLiveData<>();
    @Inject
    ExpenseTableDao expenseTableDao;
    Disposable disposable;

    @Inject
    public MainViewModel() {
        this.listMutableLiveData = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<List<ExpenseTable>> getListMutableLiveData() {
        return listMutableLiveData;
    }

}
