package resor.ahlback.rasmus.kollektivresoplanerare;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TabActivity extends AppCompatActivity {

    private static final int numberOfPages = 4;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        String[] tabTitles = new String[]{"Tab 1", "Tab 2", "Tab 3", "Tab 4"};

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),numberOfPages, tabTitles, this);
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

        int[] imageResId = {
                R.drawable.icons8_black_cat_24,
                R.drawable.icons8_cat_24,
                R.drawable.icons8_cat_footprint_24,
                R.drawable.icons8_cat_profile_24
            };

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void testApiPlannerButton(View v){

    }
}
