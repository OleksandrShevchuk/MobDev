package ua.kpi.compsys.io8226.tabs.tab_movies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import ua.kpi.compsys.io8226.R;
import ua.kpi.compsys.io8226.tabs.tab_movies.model.Movie;
import ua.kpi.compsys.io8226.tabs.tab_movies.model.MoviesList;


public class FragmentMoviesList extends Fragment {

    ListView moviesListView;
    AdapterMoviesList adapterMoviesList;
    SearchView searchView;
    ProgressBar progressBar;
    ArrayList<Movie> movies = new ArrayList<>();
    TextView noResults;
    ImageButton addMovieButton;

    String key = "7e9fe69e";


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        noResults = view.findViewById(R.id.textView_noResults);
        searchView = view.findViewById(R.id.search_view);
        moviesListView = (ListView) view.findViewById(R.id.moviesListView);
        progressBar = view.findViewById(R.id.progressBar);
        adapterMoviesList = new AdapterMoviesList(this.getContext(), movies);
        addMovieButton = (ImageButton) view.findViewById(R.id.button_addMovieButton);
        moviesListView.setAdapter(adapterMoviesList);





        //
        // searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);


        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                  Movie item = adapterMoviesList.movies.get(position);

                  Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
                  intent.putExtra("imdbId", item.getImdbID());
                  intent.putExtra("poster", item.getPoster());

                  startActivity(intent);
              }
          }
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                AsyncLoadMovies asyncLoadMovies = new AsyncLoadMovies();

                if (s.length() >= 3) {
                    //
                    // change key if invalid
                    //
                    asyncLoadMovies.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, s);

                    if (adapterMoviesList.getCount() == 0) {
                        moviesListView.setVisibility(View.GONE);
                        noResults.setVisibility(View.VISIBLE);
                    } else {
                        noResults.setVisibility(View.GONE);
                        moviesListView.setVisibility(View.VISIBLE);
                    }

                } else {
                    adapterMoviesList.clear();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                AsyncLoadMovies asyncLoadMovies = new AsyncLoadMovies();

                if (s.length() >= 3) {
                    //
                    // change key if invalid
                    //
                    asyncLoadMovies.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, s);

                    if (adapterMoviesList.getCount() == 0) {
                        moviesListView.setVisibility(View.GONE);
                        noResults.setVisibility(View.VISIBLE);
                    } else {
                        noResults.setVisibility(View.GONE);
                        moviesListView.setVisibility(View.VISIBLE);
                    }

                } else {
                    adapterMoviesList.clear();
                }

                return true;
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncLoadMovies extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            moviesListView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            return search(strings[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            adapterMoviesList.update(movies);

            progressBar.setVisibility(View.GONE);
            moviesListView.setVisibility(View.VISIBLE);
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        private ArrayList<Movie> search(String prompt) {
            String url = String.format(
                    "http://www.omdbapi.com/?apikey=%s&s=\"%s\"&page=1",
                    key,
                    prompt.trim().toLowerCase().replace("\\s+", "+"));

            try {
                return parseMovies(sendRequest(url));

            } catch (ParseException e) {
                System.err.println("Incorrect content of JSON file!");
                e.printStackTrace();
            }
            return null;
        }

        private ArrayList<Movie> parseMovies(String jsonText)
                throws ParseException {

            ArrayList<Movie> results = new ArrayList<>();
            Gson gson = new Gson();

            try {
                MoviesList moviesList = gson.fromJson(jsonText, MoviesList.class);
                results.addAll(moviesList.getMovies());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return results;
        }

        private String sendRequest(String url) {
            StringBuilder result = new StringBuilder();

            try {
                URL getReq = new URL(url);
                URLConnection movieConnection = getReq.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        movieConnection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();

            } catch (MalformedURLException e) {
                System.err.println(String.format("Incorrect URL <%s>!", url));
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }
    }
}
