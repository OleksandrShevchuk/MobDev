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
import java.net.UnknownHostException;

import ua.kpi.compsys.io8226.R;
import ua.kpi.compsys.io8226.repository.App;
import ua.kpi.compsys.io8226.repository.AppDatabase;
import ua.kpi.compsys.io8226.tabs.tab_movies.model.Movie;


public class MovieDetailsActivity extends AppCompatActivity {

    private static AppDatabase appDatabase;

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

    Movie movie;
    String imdbId;


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

        appDatabase = App.getInstance().getDatabase();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("poster") != null) {
                if (bundle.getString("poster").trim().equals("") ||
                        bundle.getString("poster").trim().equals("N/A")) {

                    poster.setImageResource(android.R.drawable.ic_media_play);
                }
            }

            movie = new Movie();
            movie.setImdbID(bundle.getString("imdbId"));
            imdbId = movie.getImdbID();
            System.out.println(movie.getImdbID());

            AsyncLoadDetailedMovieInfo asyncLoadDetailedMovieInfo = new AsyncLoadDetailedMovieInfo();
            asyncLoadDetailedMovieInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, movie);
        }
    }


    public static class AsyncLoadMovieDetailsToDB extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            appDatabase.movieDao().setDetailsByImdbId(movies[0].getImdbID(),
                    movies[0].getRated(),
                    movies[0].getReleased(),
                    movies[0].getRuntime(),
                    movies[0].getGenre(),
                    movies[0].getDirector(),
                    movies[0].getWriter(),
                    movies[0].getActors(),
                    movies[0].getPlot(),
                    movies[0].getLanguage(),
                    movies[0].getCountry(),
                    movies[0].getAwards(),
                    movies[0].getImdbRating(),
                    movies[0].getImdbVotes(),
                    movies[0].getProduction());

            return null;
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncLoadDetailedMovieInfo extends AsyncTask<Movie, Void, Movie> {

        Movie movie;
        boolean isLoaded;

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

            if (movie.getPosterBitmap() != null) {
                poster.setImageBitmap(movie.getPosterBitmap());
            } else {
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
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private Movie search() {

            String url = String.format(
                    "http://www.omdbapi.com/?apikey=%s&i=%s",
                    key,
                    movie.getImdbID());

            try {
                movie = parseMovie(sendRequest(url));
                loadToDatabase();

            } catch (MalformedURLException e) {
                System.err.println(String.format("Incorrect URL <%s>!", url));
                e.printStackTrace();
            } catch (UnknownHostException e) {
                System.err.println("Request timeout!");
                loadFromDatabase(imdbId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                System.err.println("Incorrect content of JSON file!");
                e.printStackTrace();
            }
            return movie;
        }

        private void loadFromDatabase(String imdbId){
            movie = appDatabase.movieDao().getMovieByImdbId(imdbId).createMovieInfo();
        }

        private void loadToDatabase(){
            new AsyncLoadMovieDetailsToDB().execute(movie);
        }

        private Movie parseMovie(String jsonText)
                throws ParseException {

            Movie newMovie = new Movie();
            Gson gson = new Gson();
            newMovie = gson.fromJson(jsonText, Movie.class);

            return newMovie;
        }

        private String sendRequest(String url) throws IOException {
            StringBuilder result = new StringBuilder();

            URL getReq = new URL(url);
            URLConnection movieConnection = getReq.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    movieConnection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                result.append(inputLine).append("\n");

            in.close();

            return result.toString();
        }
    }

}