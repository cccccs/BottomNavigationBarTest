package activitytest.com.example.bottomnavigationbartest.Fragment;

/**
 * Created by pc on 2017/4/27.
 */

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 2016/11/15.
 */
public class PublishFragment extends Fragment {

//    private DropDownMenu dropDownMenu;
//
//    private List<View> popupViews = new ArrayList<>();
//    private String headers[] = {"职位", "地区", "工作时间", "综合排序"};
//    private String jobs[] = {"不限", "家教", "调研", "送餐员", "促销", "礼仪", "销售", "服务员", "临时工", "校内", "设计", "传单", "其他"};
//    private String places[] = {"全开封", "郊", "南关", "鼓楼", "顺河回族", "金明"};
//    private String workTimes[] = {"不限", "工作日", "周末"};
//    private String orders[] = {"综合排序", "最新发布", "离我最近"};

    //    private GirdDropDownAdapter jobsAdapter;
//    private ListDropDownAdapter placesAdapter;
//    private ListDropDownAdapter workTimesAdapter;
//    private ListDropDownAdapter ordersAdapter;
    @BindView(R.id.publish_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.publish_viewpager)
    ViewPager mViewPager;
    Toolbar mToolbar;
    private PublishPagerAdapter mPublishPagerAdapter;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private HadPublishedFragment hadPublishedFragment;
    private NotPublishFragment notPublishFragment;
    private boolean isremove = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        ButterKnife.bind(this,view);
        initControls(view);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.publish_toolbar);
//        toolbar.setTitle("发布兼职");
        return view;
    }

    private void initControls(View view){
        Toolbar mToolbar =(Toolbar)getActivity().findViewById(R.id.main_toolbar);
        //初始化各fragment
        hadPublishedFragment = new HadPublishedFragment();
        notPublishFragment = new NotPublishFragment();
        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(notPublishFragment);
        list_fragment.add(hadPublishedFragment);
        //添加tablayout
        list_title = new ArrayList<>();
        list_title.add("申请发布");
        list_title.add("已发布");
        removeTableLayout(view);
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(1)));

        mPublishPagerAdapter = new PublishPagerAdapter(getChildFragmentManager(),list_fragment,list_title);
//        mViewPager.addOnPageChangeListener(new  TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//        mViewPager.setAdapter(mpublishFragmentPagerAdapter);
        //viewpager加载adpter
        mViewPager.setAdapter(mPublishPagerAdapter);
        //TabLayout加载
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private void removeTableLayout(View  view){
        if(isremove == false){
            AppBarLayout appBarLayout =(AppBarLayout) getActivity().findViewById(R.id.layout_appbar);
            LinearLayout publishLinearLayout = (LinearLayout) view.findViewById(R.id.publish_linear_layout);
            publishLinearLayout.removeView(mTabLayout);
            appBarLayout.addView(mTabLayout);
        }
        isremove = true;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        Toolbar mToolbar =(Toolbar)getActivity().findViewById(R.id.main_toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        AppBarLayout appBarLayout =(AppBarLayout) getActivity().findViewById(R.id.layout_appbar);
        appBarLayout.removeView(mTabLayout);
    }
}