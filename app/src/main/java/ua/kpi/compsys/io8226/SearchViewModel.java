package ua.kpi.compsys.io8226;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    public MutableLiveData<String> query = new MutableLiveData<String>();


    public LiveData<String> getQuery() {
        return query;
    }

    public void setQuery(String queryData) {
        query.setValue(queryData);
    }
}
