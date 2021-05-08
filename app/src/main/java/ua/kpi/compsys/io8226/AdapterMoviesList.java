package ua.kpi.compsys.io8226;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class AdapterMoviesList extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> titles;
    ArrayList<String> titlesFiltered;
    ArrayList<String> years;
    ArrayList<String> imdbIDs;
    ArrayList<String> types;
    ArrayList<String> posters;

    Context mContext;


    public AdapterMoviesList(Context context, ArrayList<String> titles, ArrayList<String> years,
                             ArrayList<String> imdbIDs, ArrayList<String> types,
                             ArrayList<String> posters) {

        super(context, R.layout.listview_item);
        this.titles = titles;
        this.titlesFiltered = titles;
        this.years = years;
        this.imdbIDs = imdbIDs;
        this.types = types;
        this.posters = posters;

        this.mContext = context;
    }


    @Override
    public int getCount() {
        return titlesFiltered.size();
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

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.mTitle.setText(titles.get(position));
            mViewHolder.mYear.setText(years.get(position));
            mViewHolder.mImdbID.setText(imdbIDs.get(position));
            mViewHolder.mType.setText(types.get(position));

            mViewHolder.mPoster.setImageResource(mContext.getResources().getIdentifier(
                    posters.get(position), "drawable", mContext.getPackageName()));

        } catch (Resources.NotFoundException | IndexOutOfBoundsException |
                NumberFormatException e) {

            e.printStackTrace();
        }

        return convertView;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = titles.size();
                    filterResults.values = titles;
                } else {
                    String searchStr = charSequence.toString().toLowerCase();
                    ArrayList<String> resultData = new ArrayList<>();

                    for (String title: titles) {
                        if (title.contains(searchStr)) {
                            resultData.add(title);
                        }

                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                titlesFiltered = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }

    private static class ViewHolder {
        TextView mTitle;
        TextView mYear;
        TextView mImdbID;
        TextView mType;
        ImageView mPoster;
    }
}