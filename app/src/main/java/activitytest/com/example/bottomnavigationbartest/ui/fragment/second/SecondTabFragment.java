package activitytest.com.example.bottomnavigationbartest.ui.fragment.second;

/**
 * Created by pc on 2017/4/27.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.PublishPagerAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseMainFragment;
import butterknife.ButterKnife;

/**
 * Created by pc on 2016/11/15.
 */
public class SecondTabFragment extends BaseMainFragment {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    Toolbar mToolbar;

    public static SecondTabFragment newInstance(){
        Bundle args = new Bundle();
        SecondTabFragment fragment = new SecondTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_second, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    private void initView(View view){
         mToolbar =(Toolbar) view.findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        mToolbar.setTitle("发布");

        mTabLayout.addTab(mTabLayout.newTab().setText("申请发布"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已发布"));

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);


    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mViewPager.setAdapter(new PublishPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDetach(){
        super.onDetach();

    }
}