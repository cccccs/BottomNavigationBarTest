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
import activitytest.com.example.bottomnavigationbartest.adpater.EmpPublishPagerAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;

/**
 * Created by pc on 2017/8/20.
 */

public class EmpPublishFragment extends BaseBackFragment {
    Toolbar toolbar;
    ViewPager mViewPager;
    TabLayout mTab;

    public static EmpPublishFragment newInstance(){
        Bundle args = new Bundle();

        EmpPublishFragment fragment = new EmpPublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_emp_publish,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        mTab = (TabLayout)view.findViewById(R.id.tab);
        mViewPager = (ViewPager)view.findViewById(R.id.viewPager);

        toolbar.setTitle("我的发布");
        initToolbarNav(toolbar);

        mTab.addTab(mTab.newTab().setText("待审核"));
        mTab.addTab(mTab.newTab().setText("正在招聘"));
        mTab.addTab(mTab.newTab().setText("截止报名"));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mViewPager.setAdapter(new EmpPublishPagerAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }
}
