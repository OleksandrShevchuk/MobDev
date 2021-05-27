package ua.kpi.compsys.io8226.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import ua.kpi.compsys.io8226.tabs.tab_movies.model.Movie;


@Entity
public class TableMovies {
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String imdbRating;
    private String imdbVotes;
    private String production;
    @NonNull
    @PrimaryKey
    private String imdbID;
    private String type;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] poster;


    public TableMovies(@NotNull String imdbID, String title, String year, String rated, String released,
                       String runtime, String genre, String director, String writer,
                       String actors, String plot, String language, String country,
                       String awards, String imdbRating, String imdbVotes, String production,
                       String type) {

        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.production = production;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = null;
    }

    @Ignore
    public TableMovies(@NotNull String imdbID, String title, String year, String type){
        this.title = title;
        this.year = year;
        this.rated = "";
        this.released = "";
        this.runtime = "";
        this.genre = "";
        this.director = "";
        this.writer = "";
        this.actors = "";
        this.plot = "";
        this.language = "";
        this.country = "";
        this.awards = "";
        this.imdbRating = "";
        this.imdbVotes = "";
        this.production = "";
        this.imdbID = imdbID;
        this.type = type;
        this.poster = null;
    }


    public Movie makeMovie() {
        return new Movie(title, year, imdbID, type, convertPosterToBitmap());
    }

    public Movie createMovieInfo(){
        Movie movie = makeMovie();

        movie.setRated(rated);
        movie.setReleased(released);
        movie.setRuntime(runtime);
        movie.setGenre(genre);
        movie.setDirector(director);
        movie.setWriter(writer);
        movie.setActors(actors);
        movie.setPlot(plot);
        movie.setLanguage(language);
        movie.setCountry(country);
        movie.setAwards(awards);
        movie.setImdbRating(imdbRating);
        movie.setImdbVotes(imdbVotes);
        movie.setProduction(production);

        return movie;
    }

    public void setPosterBitmap(Bitmap bitmap){
        poster = getBitmapAsByteArray(bitmap);
    }

    public Bitmap convertPosterToBitmap() {
        return BitmapFactory.decodeByteArray(poster, 0, poster.length);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public void setInfo(String rated, String released, String runtime, String genre,
                        String director, String writer, String actors, String plot,
                        String language, String country, String awards, String rating,
                        String votes, String production) {

        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.imdbRating = rating;
        this.imdbVotes = votes;
        this.production = production;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    @NotNull
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(@NotNull String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
}
