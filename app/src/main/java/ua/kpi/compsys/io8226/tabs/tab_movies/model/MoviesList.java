package ua.kpi.compsys.io8226.tabs.tab_movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MoviesList {
    @SerializedName("Search")
    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Movie getMovieByPosition(int position) {
        return movies.get(position);
    }
}
