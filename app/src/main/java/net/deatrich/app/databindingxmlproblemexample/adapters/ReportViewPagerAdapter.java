package net.deatrich.app.databindingxmlproblemexample.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.deatrich.app.databindingxmlproblemexample.R;
import net.deatrich.app.databindingxmlproblemexample.fragments.ReportFragment;

import java.util.ArrayList;
import java.util.Date;


public class ReportViewPagerAdapter extends FragmentPagerAdapter {

    private static ArrayList<Integer> mLayoutIds = new ArrayList<>();
    private static ArrayList<String> mSectionTitles = new ArrayList<>();

    private static int numSections;
    private final Date reportDate;


    private final FragmentManager fragmentManager;


    public ReportViewPagerAdapter(FragmentManager fm, Context context, Date reportDate) {
        super(fm);
        fragmentManager = fm;
        this.reportDate = reportDate;

        mLayoutIds.add(R.layout.content_report_constraint);
        mSectionTitles.add("ConstraintLayout");

        mLayoutIds.add(R.layout.content_report_linear);
        mSectionTitles.add("LinearLayout");


        numSections = mLayoutIds.size();

    }

    @Override
    public int getCount() {

        return numSections;
    }


    @Override
    public Fragment getItem(int sectionIndex) {

        return ReportFragment.newInstance(mLayoutIds.get(sectionIndex), reportDate);


    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {

        if (mSectionTitles != null) {
            int numSections = mSectionTitles.size();

            if ((numSections > 0) && (position < numSections))
                return mSectionTitles.get(position);

        }

        return "Page " + position;

    }
}
