package ua.kpi.compsys.io8226;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> years = new ArrayList<>();
    ArrayList<String> imdbIDs = new ArrayList<>();
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> posters = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        moviesListView = (ListView) view.findViewById(R.id.moviesListView);
        AdapterMoviesList adapterMoviesList = new AdapterMoviesList(this.getContext(), titles,
                years, imdbIDs, types, posters);
        moviesListView.setAdapter(adapterMoviesList);

        if (adapterMoviesList.getCount() == 0) {
            parseFromFile("movies_list");
            assignFields(moviesList);
        }
    }


    //Opens file, reads its contents
    private void parseFromFile(String fileName) {
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

    private void assignFields(MoviesList moviesList) {
        for (Movie movie : moviesList.getMovies()) {
            try {
                if (!movie.getTitle().trim().equals(""))
                    titles.add(movie.getTitle());
                else
                    posters.add("");
                if (!movie.getYear().trim().equals(""))
                    years.add(movie.getYear());
                else
                    years.add("");
                if (!movie.getImdbID().trim().equals(""))
                    imdbIDs.add(movie.getImdbID());
                else
                    imdbIDs.add("");
                if (!movie.getType().trim().equals(""))
                    types.add(movie.getType());
                else
                    types.add("");
                if (!movie.getPoster().trim().equals(""))
                    posters.add(movie.getPoster());
                else
                    posters.add("");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private InputStream getFileLocation(String fileName) {
        return getResources().openRawResource(getResources().getIdentifier(fileName,
                "raw", this.getContext().getPackageName()));
    }

}