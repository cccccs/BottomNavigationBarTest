package activitytest.com.example.bottomnavigationbartest.ui.fragment.third;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.MessagePagerAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseMainFragment;

/**
 * Created by pc on 2016/11/15.
 */
public class ThirdTabFragment extends BaseMainFragment{
     TabLayout mTabLayout ;
     ViewPager mViewPager;

    public static ThirdTabFragment newInstance(){
        Bundle args = new Bundle();
        ThirdTabFragment fragment = new ThirdTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_third, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){

        mTabLayout = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("系统通知"));
        mTabLayout.addTab(mTabLayout.newTab().setText("消息"));

        mViewPager.setAdapter(new MessagePagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
