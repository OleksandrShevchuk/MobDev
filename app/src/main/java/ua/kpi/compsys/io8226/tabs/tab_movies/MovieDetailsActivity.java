package ua.kpi.compsys.io8226.tabs.tab_movies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ua.kpi.compsys.io8226.R;
import ua.kpi.compsys.io8226.tabs.tab_movies.model.Movie;


public class MovieDetailsActivity extends AppCompatActivity {

    ScrollView movieDetailsLayout;
    ProgressBar detailedInfoProgressBar;
    LinearLayout progressBarLayout;
    ProgressBar detailedPosterProgressBar;
    ImageView poster;
    TextView title;
    TextView year;
    TextView genre;
    TextView director;
    TextView writer;
    TextView actors;
    TextView country;
    TextView language;
    TextView production;
    TextView released;
    TextView runtime;
    TextView awards;
    TextView rating;
    TextView votes;
    TextView rated;
    TextView plot;

    String key = "7e9fe69e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieDetailsLayout = (ScrollView) findViewById(R.id.movieDetailsLayout);
        detailedInfoProgressBar = (ProgressBar) findViewById(R.id.detailedInfoProgressBar);
        progressBarLayout = (LinearLayout) findViewById(R.id.progressBarLayout);
        detailedPosterProgressBar = (ProgressBar) findViewById(R.id.detailedPosterProgressBar);
        poster = (ImageView) findViewById(R.id.imageView_poster);
        title = (TextView) findViewById(R.id.textView_title);
        year = (TextView) findViewById(R.id.textView_year);
        genre = (TextView) findViewById(R.id.textView_genre);
        director = (TextView) findViewById(R.id.textView_director);
        writer = (TextView) findViewById(R.id.textView_writer);
        actors = (TextView) findViewById(R.id.textView_actors);
        country = (TextView) findViewById(R.id.textView_country);
        language = (TextView) findViewById(R.id.textView_language);
        production = (TextView) findViewById(R.id.textView_production);
        released = (TextView) findViewById(R.id.textView_released);
        runtime = (TextView) findViewById(R.id.textView_runtime);
        awards = (TextView) findViewById(R.id.textView_awards);
        rating = (TextView) findViewById(R.id.textView_rating);
        votes = (TextView) findViewById(R.id.textView_votes);
        rated = (TextView) findViewById(R.id.textView_rated);
        plot = (TextView) findViewById(R.id.textView_plot);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("poster").trim().equals("") ||
                    bundle.getString("poster").trim().equals("N/A")) {

                poster.setImageResource(android.R.drawable.ic_media_play);
            }

            Movie movie = new Movie();
            movie.setImdbID(bundle.getString("imdbId"));
            System.out.println(movie.getImdbID());

            AsyncLoadDetailedMovieInfo asyncLoadDetailedMovieInfo = new AsyncLoadDetailedMovieInfo();
            asyncLoadDetailedMovieInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, movie);
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncLoadDetailedMovieInfo extends AsyncTask<Movie, Void, Movie> {

        Movie movie;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            movieDetailsLayout.setVisibility(View.GONE);
            detailedInfoProgressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Movie doInBackground(Movie... movies) {
            movie = movies[0];
            return search();
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);

            detailedInfoProgressBar.setVisibility(View.GONE);
            movieDetailsLayout.setVisibility(View.VISIBLE);

            title.setText(movie.getTitle());
            year.setText(movie.getYear());
            genre.setText(movie.getGenre());
            director.setText(movie.getDirector());
            writer.setText(movie.getWriter());
            actors.setText(movie.getActors());
            country.setText(movie.getCountry());
            language.setText(movie.getLanguage());
            production.setText(movie.getProduction());
            released.setText(movie.getReleased());
            runtime.setText(movie.getRuntime());
            awards.setText(movie.getAwards());
            rating.setText(movie.getImdbRating());
            votes.setText(movie.getImdbVotes());
            rated.setText(movie.getRated());
            plot.setText(movie.getPlot());


            poster.setVisibility(View.GONE);
            detailedPosterProgressBar.setVisibility(View.VISIBLE);

            Picasso.get().load(movie.getPoster()).into(poster,
                    new com.squareup.picasso.Callback() {

                        @Override
                        public void onSuccess() {
                            detailedPosterProgressBar.setVisibility(View.GONE);
                            poster.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            detailedPosterProgressBar.setVisibility(View.GONE);
                            poster.setVisibility(View.VISIBLE);

                            poster.setImageResource(android.R.drawable.ic_media_play);
                        }
                    });

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private Movie search() {
            String url = String.format(
                    "http://www.omdbapi.com/?apikey=%s&i=%s",
                    key,
                    movie.getImdbID());

            try {
                return parseMovie(sendRequest(url));

            } catch (ParseException e) {
                System.err.println("Incorrect content of JSON file!");
                e.printStackTrace();
            }
            return null;
        }

        private Movie parseMovie(String jsonText)
                throws ParseException {

            Movie newMovie = new Movie();
            Gson gson = new Gson();
            newMovie = gson.fromJson(jsonText, Movie.class);

            return newMovie;
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