package ua.kpi.compsys.io8226.repository;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity
public class SearchTable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String searchQueue;
    @TypeConverters({MoviePackager.class})
    public ArrayList<String> foundMovies;
}
