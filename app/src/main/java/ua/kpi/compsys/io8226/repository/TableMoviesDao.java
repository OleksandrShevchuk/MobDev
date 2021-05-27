package ua.kpi.compsys.io8226.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TableMoviesDao {
    @Query("SELECT * FROM TableMovies")
    List<TableMovies> getAllMovies();

    @Query("SELECT * FROM TableMovies WHERE imdbID = :imdbId")
    TableMovies getMovieByImdbId(String imdbId);

    @Query("UPDATE TableMovies " +
            "SET rated = :rated, released = :released, runtime = :runtime, " +
            "genre = :genre, director = :director, writer = :writer, actors = :actors, " +
            "plot = :plot, language = :language, country = :country, awards = :awards, " +
            "imdbRating = :imdbRating, imdbVotes = :imdbVotes, production = :production " +
            "WHERE imdbID = :imdbId")
    void setDetailsByImdbId(String imdbId, String rated, String released, String runtime,
                            String genre, String director, String writer, String actors,
                            String plot, String language, String country, String awards,
                            String imdbRating, String imdbVotes, String production);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TableMovies tableMovies);

    @Update
    void update(TableMovies tableMovies);

    @Delete
    void delete(TableMovies tableMovies);
}
