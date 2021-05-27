package ua.kpi.compsys.io8226.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TableMovies.class, SearchTable.class, Gallery.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TableMoviesDao movieDao();
    public abstract SearchTableDao searchTableDao();
    public abstract GalleryDao galleryDao();
}
