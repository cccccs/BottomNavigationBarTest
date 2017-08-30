package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.StuJobPagerAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;

/**
 * Created by pc on 2017/8/20.
 */

public class StuJobFragment extends BaseBackFragment {
    Toolbar toolbar;
    TabLayout mTab;
    ViewPager mViewPager;

    public static StuJobFragment newInstance(){
        Bundle args = new Bundle();

        StuJobFragment fragment = new StuJobFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_stu_send,container,false);
        initView(view);
        return view;
    }
    private void initView(View view){
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        mTab = (TabLayout)view.findViewById(R.id.tab);
        mViewPager =(ViewPager)view.findViewById(R.id.viewPager);

        mTab.addTab(mTab.newTab().setText("全部"));
        mTab.addTab(mTab.newTab().setText("已报名"));
        mTab.addTab(mTab.newTab().setText("已录取"));
        mTab.addTab(mTab.newTab().setText("已拒绝"));



        toolbar.setTitle("我的兼职");
        initToolbarNav(toolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mViewPager.setAdapter(new StuJobPagerAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }
}
