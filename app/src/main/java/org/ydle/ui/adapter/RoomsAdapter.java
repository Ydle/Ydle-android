package org.ydle.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.ydle.ui.rooms.AddRoomFragment;
import org.ydle.ui.rooms.RoomFragment;

import java.util.List;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class RoomsAdapter extends FragmentPagerAdapter {

    private static final String LOG_TAG = RoomsAdapter.class.getSimpleName();

    private final List mFragments;

    public RoomsAdapter(FragmentManager fm, List fragments) {
        super(fm);
        mFragments = fragments;
        mFragments.add(AddRoomFragment.newInstance());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mFragments.get(position) instanceof  RoomFragment) {
            return ((RoomFragment) mFragments.get(position)).getRoom().getName();
        }
        return "+";
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragments.get(position);
    }
}