package resor.ahlback.rasmus.kollektivresoplanerare;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int pages;
    private String[] tabTitles;
    private Context mContext;

    public TabPagerAdapter(FragmentManager fm, int pages, String[] tabTitles, Context context) {
        super(fm);
        this.pages = pages;
        this.tabTitles = tabTitles;
        mContext = context;


    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new PlannerFragment();
            case 1:
                return new TrafficDisruptionsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return pages;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return null;
    }
}
