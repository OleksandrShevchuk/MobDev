package ua.kpi.compsys.io8226.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ua.kpi.compsys.io8226.FragmentLabel;
import ua.kpi.compsys.io8226.FragmentMoviesList;
import ua.kpi.compsys.io8226.FragmentDrawing;
import ua.kpi.compsys.io8226.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,
            R.string.tab_text_3};
    private final Context mContext;
    Drawable myDrawable;
    String title;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentLabel();
                break;
            case 1:
                fragment = new FragmentDrawing();
                break;
            case 2:
                fragment = new FragmentMoviesList();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                myDrawable = mContext.getResources().
                        getDrawable(R.drawable.ic_tab_label);
                title = mContext.getResources().getString(TAB_TITLES[0]);
                break;
            case 1:
                myDrawable = mContext.getResources().
                        getDrawable(R.drawable.ic_tab_drawing);
                title = mContext.getResources().getString(TAB_TITLES[1]);
                break;
            case 2:
                myDrawable = mContext.getResources().
                        getDrawable(R.drawable.ic_tab_movies_list);
                title = mContext.getResources().getString(TAB_TITLES[2]);
                break;
            default:
                //TODO: handle default selection
                break;
        }

        // space added before text for convenience
        SpannableStringBuilder sb = new SpannableStringBuilder(" \n" + title);

        myDrawable.setBounds(5, 5, myDrawable.getIntrinsicWidth(),
                myDrawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BASELINE);
        sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }


    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}