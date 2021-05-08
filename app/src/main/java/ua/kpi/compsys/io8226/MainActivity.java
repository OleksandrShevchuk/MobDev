package ua.kpi.compsys.io8226;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import ua.kpi.compsys.io8226.ui.main.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity {

    SearchViewModel searchViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendMail();
            }
        });

//        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
//        SearchView searchView = (SearchView) men.findItem(R.id.search).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.i("onQueryTextSubmit", query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchViewModel.setQuery(newText);
//                Log.i("onQueryTextChange", newText);
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //getFilter(s);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMail() {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("legolasokay@gmail.com") +
                "?subject=" + Uri.encode("Відгук до MobDev додатку");
        Uri uri = Uri.parse(uriText);

        send.setData(uri);

        startActivity(Intent.createChooser(send, "Залиште відгук:"));
    }
}