package activitytest.com.example.bottomnavigationbartest.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import activitytest.com.example.bottomnavigationbartest.ui.fragment.second.HadPublishedFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.second.NotPublishFragment;

/**
 * Created by pc on 2017/3/1.
 */

public  class PublishPagerAdapter extends FragmentPagerAdapter {

   private String[] mTab = new String[]{"已发布","未发布"};

    public PublishPagerAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position){
            if (position == 0 )
                return NotPublishFragment.instance();
            else
                return HadPublishedFragment.instance();
    }

    @Override
    public int getCount() {
        return  mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTab[position];
    }
}
