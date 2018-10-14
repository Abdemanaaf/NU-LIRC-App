package com.example.abdemanaaf.nulircapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private String[] tabArray = new String[] {"E-JOURNALS", "E-BOOKS", "ANTI PLAGIARISM", "THESES"};

    TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new EJournals();

            case 1:
                return new EBooks();

            case 2:
                return new AntiPlagiarism();

            case 3:
                return new Theses();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }
}
