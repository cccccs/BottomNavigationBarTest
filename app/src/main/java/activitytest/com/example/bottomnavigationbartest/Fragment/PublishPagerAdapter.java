package activitytest.com.example.bottomnavigationbartest.Fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pc on 2017/3/1.
 */

public class PublishPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list_Fragment;
    private List<String> list_Title;
    private final int PAGE_COUNT = 2;
    private Context mContext;

    public PublishPagerAdapter(FragmentManager fm, List<Fragment> list_Fragment, List<String> list_Title){
        super(fm);
        this.list_Fragment = list_Fragment;
        this.list_Title = list_Title;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position){
        return list_Fragment.get(position);
    }

    @Override
    public int getCount() {
        return  PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return list_Title.get(position%list_Title.size());
    }
}
