package activitytest.com.example.bottomnavigationbartest.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.JobAllFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.JobGetFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.JobRefuseFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.JobSendFragment;

/**
 * Created by pc on 2017/8/30.
 */

public class StuJobPagerAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"全部","已报名","已录取","已拒绝"};

    public StuJobPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                return JobAllFragment.newInstance();
            case 1:
                return JobSendFragment.newInstance();
            case 2:
                return JobGetFragment.newInstance();
            case 3:
                return JobRefuseFragment.newInstance();
            default:
                return null;
        }
    }
    @Override
    public int getCount(){
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTab[position];
    }



}
