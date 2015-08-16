package com.thingsle.sameer.thingsle.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.adapter.ListCountriesAdapter;
import com.thingsle.sameer.thingsle.data.CountrieData;
import com.thingsle.sameer.thingsle.data.CountryCRUD;

import java.util.List;

public class SearchActivity extends ActionBarActivity {

    SearchView search;
    private ListView mListView;
    private SearchView mSearchView;

    private ListView mListviewCountries;

    private ListCountriesAdapter mAdapter;
    private List<CountrieData> mListCountries;
    private CountryCRUD mCountryCrud;




    private final String[] mStrings = {"Thamel", "Basantapur", "Boudha", "Swoyambhu", "Lazimpat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_search);

        mCountryCrud = new CountryCRUD(this);
        mListCountries = mCountryCrud.getAllCountries();

        mAdapter = new ListCountriesAdapter(this, mListCountries);
        mListviewCountries.setAdapter(mAdapter);



        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListviewCountries = (ListView) findViewById(R.id.list_view);
       /* mListView.setAdapter(mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mStrings));*/
        mListviewCountries.setTextFilterEnabled(true);


        setupSearchView();



    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListviewCountries.clearTextFilter();
        } else {
            mListviewCountries.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}


