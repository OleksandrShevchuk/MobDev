package ua.kpi.compsys.io8226.tabs.tab_movies;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.kpi.compsys.io8226.R;
import ua.kpi.compsys.io8226.repository.App;
import ua.kpi.compsys.io8226.repository.AppDatabase;
import ua.kpi.compsys.io8226.tabs.tab_movies.model.Movie;


public class AdapterMoviesList extends ArrayAdapter<String> {

    private static AppDatabase appDatabase;

    ArrayList<Movie> movies;
    Context mContext;


    public AdapterMoviesList(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.listview_item);
        this.movies = movies;
        this.mContext = context;

        appDatabase = App.getInstance().getDatabase();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder mViewHolder = new ViewHolder();
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.listview_item, parent, false);


                mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
                mViewHolder.mYear = (TextView) convertView.findViewById(R.id.year);
                mViewHolder.mImdbID = (TextView) convertView.findViewById(R.id.imdbID);
                mViewHolder.mType = (TextView) convertView.findViewById(R.id.type);
                mViewHolder.mPoster = (ImageView) convertView.findViewById(R.id.poster);
                mViewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.progressBar2);

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.mTitle.setText(movies.get(position).getTitle());
            mViewHolder.mYear.setText(movies.get(position).getYear());
            mViewHolder.mImdbID.setText(movies.get(position).getImdbID());
            mViewHolder.mType.setText(movies.get(position).getType());

            if (movies.get(position).getPoster() == null ||
                    movies.get(position).getPoster().trim().equals("") ||
                    movies.get(position).getPoster().trim().equals("N/A")) {

                mViewHolder.mPoster.setImageResource(android.R.drawable.ic_media_play);

            } else {

                mViewHolder.mPoster.setVisibility(View.GONE);
                mViewHolder.mProgressBar.setVisibility(View.VISIBLE);

                ViewHolder finalMViewHolder = mViewHolder;

                Movie movie = movies.get(position);

                if (movie.getPosterBitmap() != null) {
                    mViewHolder.mPoster.setImageBitmap(movie.getPosterBitmap());
                    mViewHolder.mProgressBar.setVisibility(View.GONE);
                    mViewHolder.mPoster.setVisibility(View.VISIBLE);

                } else {
                    Picasso.get().load(movies.get(position).getPoster()).into(mViewHolder.mPoster,
                            new com.squareup.picasso.Callback() {

                                @Override
                                public void onSuccess() {
                                    finalMViewHolder.mProgressBar.setVisibility(View.GONE);
                                    finalMViewHolder.mPoster.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    e.printStackTrace();
                                    finalMViewHolder.mProgressBar.setVisibility(View.GONE);
                                    finalMViewHolder.mPoster.setVisibility(View.VISIBLE);
                                }
                            });
                }
            }

        } catch (Resources.NotFoundException | IndexOutOfBoundsException |
                NumberFormatException e) {

            e.printStackTrace();
        }

        return convertView;
    }


    @Override
    public int getCount() {
        return movies.size();
    }

    public void update(ArrayList<Movie> results) {
        movies = new ArrayList<>();
        movies.addAll(results);
        notifyDataSetChanged();
    }

    public void clear() {
        update(new ArrayList<>());
        notifyDataSetChanged();
    }


    private static class ViewHolder {
        TextView mTitle;
        TextView mYear;
        TextView mImdbID;
        TextView mType;
        ImageView mPoster;
        ProgressBar mProgressBar;
    }
}