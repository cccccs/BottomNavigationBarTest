package activitytest.com.example.bottomnavigationbartest.Fragment;

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

/**
 * Created by pc on 2016/11/15.
 */
public class MessageFragment extends Fragment {

    TabLayout mTabLayout ;
    ViewPager mViewPager;
    private InformMesFragment mInformMesFragment;
    private LetterMesFragment mLetterMesFragment;
    private List<Fragment> mListFragment;
    private List<String> mListTitle;
    private PublishPagerAdapter mPublishPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initiView(view);
        return view;
    }

    private void initiView(View view){
        Toolbar mToolbar =(Toolbar)getActivity().findViewById(R.id.main_toolbar);
        mTabLayout = (TabLayout) view.findViewById(R.id.message_tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.message_viewpager);
        mInformMesFragment = new InformMesFragment();
        mLetterMesFragment = new LetterMesFragment();

        removeTableLayout(view);

        mToolbar.setVisibility(View.GONE);
        //添加tab消息碎片
        mListFragment = new ArrayList<>();
        mListFragment.add(mInformMesFragment);
        mListFragment.add(mLetterMesFragment);
        //添加tab名称
        mListTitle = new ArrayList<>();
        mListTitle.add("系统通知");
        mListTitle.add("私信");
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(mListTitle.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mListTitle.get(1)));
        //加载viewpager
        mPublishPagerAdapter = new PublishPagerAdapter(getChildFragmentManager(),mListFragment,mListTitle);
        mViewPager.setAdapter(mPublishPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void removeTableLayout(View  view){
        AppBarLayout appBarLayout =(AppBarLayout) getActivity().findViewById(R.id.layout_appbar);
        LinearLayout messageLinearLayout = (LinearLayout) view.findViewById(R.id.message_linear_layout);
        messageLinearLayout.removeView(mTabLayout);
        appBarLayout.addView(mTabLayout);

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
