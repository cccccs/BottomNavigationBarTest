package activitytest.com.example.bottomnavigationbartest.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pc on 2017/4/27.
 */

public class MessagePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragment;
    private List<String> mListTitle;
    private final int PagerNum = 2;

    public MessagePagerAdapter(FragmentManager fm, List<Fragment> list_Fragment, List<String> list_Title){
        super(fm);
        this.mListFragment = list_Fragment;
        this.mListTitle= list_Title;
    }

    @Override
    public Fragment getItem(int position){
        return mListFragment.get(position);
    }

    @Override
    public int getCount(){
        return PagerNum;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mListTitle.get(position%mListTitle.size());
    }
}
