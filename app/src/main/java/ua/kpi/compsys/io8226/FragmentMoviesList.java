package ua.kpi.compsys.io8226;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.kpi.compsys.io8226.model.Movie;
import ua.kpi.compsys.io8226.model.MoviesList;


public class FragmentMoviesList extends Fragment {

    MoviesList moviesList;
    ListView moviesListView;
    AdapterMoviesList adapterMoviesList;
    SearchView searchView;
    ArrayList<Movie> movies = new ArrayList<>();
    TextView noResults;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        noResults = view.findViewById(R.id.textView_noResults);
        searchView =  view.findViewById(R.id.search_view);
        moviesListView = (ListView) view.findViewById(R.id.moviesListView);
        adapterMoviesList = new AdapterMoviesList(this.getContext(), movies);
        moviesListView.setAdapter(adapterMoviesList);

        if (adapterMoviesList.getCount() == 0) {
            parseFromJson("movies_list");
            movies.addAll(moviesList.getMovies());
        }

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Movie item = moviesList.getMovieByPosition(position);
                Toast.makeText(view.getContext(), "You selected : " + item.getImdbID(),
                        Toast.LENGTH_LONG).show();
                parseFromTxt(item.getImdbID(), item);

                Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
                intent.putExtra("poster", item.getPoster());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("year", item.getYear());
                intent.putExtra("genre", item.getGenre());
                intent.putExtra("director", item.getDirector());
                intent.putExtra("writer", item.getWriter());
                intent.putExtra("actors", item.getActors());
                intent.putExtra("country", item.getCountry());
                intent.putExtra("language", item.getLanguage());
                intent.putExtra("production", item.getProduction());
                intent.putExtra("released", item.getReleased());
                intent.putExtra("runtime", item.getRuntime());
                intent.putExtra("awards", item.getAwards());
                intent.putExtra("rating", item.getImdbRating());
                intent.putExtra("votes", item.getImdbVotes());
                intent.putExtra("rated", item.getRated());
                intent.putExtra("plot", item.getPlot());

                startActivity(intent);
            }
        }
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Movie> results = new ArrayList<>();

                for (Movie movie: movies) {
                    if (movie.getTitle().toLowerCase().contains(s.toLowerCase()))
                        results.add(movie);
                }

                adapterMoviesList.update(results);

                if (results.size() == 0) {
                    moviesListView.setVisibility(View.GONE);
                    noResults.setVisibility(View.VISIBLE);
                } else {
                    noResults.setVisibility(View.GONE);
                    moviesListView.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });
    }


    //Parses JSON file with primary film characteristics.
    private void parseFromJson(String fileName) {
        Gson gson = new Gson();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(getFileLocation(fileName)));
            moviesList = gson.fromJson(br, MoviesList.class);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Parses TXT file with additional film characteristics.
    private void parseFromTxt(String fileName, Movie movie) {
        Gson gson = new Gson();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(getFileLocation(fileName)));
            Movie parsedMovie = gson.fromJson(br, Movie.class);

            movie.setTitle(parsedMovie.getTitle());
            movie.setYear(parsedMovie.getYear());
            movie.setGenre(parsedMovie.getGenre());
            movie.setDirector(parsedMovie.getDirector());
            movie.setWriter(parsedMovie.getWriter());
            movie.setActors(parsedMovie.getActors());
            movie.setCountry(parsedMovie.getCountry());
            movie.setLanguage(parsedMovie.getLanguage());
            movie.setProduction(parsedMovie.getProduction());
            movie.setReleased(parsedMovie.getReleased());
            movie.setRuntime(parsedMovie.getRuntime());
            movie.setAwards(parsedMovie.getAwards());
            movie.setImdbRating(parsedMovie.getImdbRating());
            movie.setImdbVotes(parsedMovie.getImdbVotes());
            movie.setRated(parsedMovie.getRated());
            movie.setPlot(parsedMovie.getPlot());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private InputStream getFileLocation(String fileName) {
        return getResources().openRawResource(getResources().getIdentifier(fileName,
                "raw", this.getContext().getPackageName()));
    }
}