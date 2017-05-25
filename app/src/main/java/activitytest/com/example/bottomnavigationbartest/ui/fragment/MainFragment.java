package activitytest.com.example.bottomnavigationbartest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.event.TabSelectedEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.first.FirstTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.FoursTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.second.SecondTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.third.ThirdTabFragment;
import me.yokeyword.fragmentation.SupportFragment;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static java.lang.Boolean.FALSE;

/**
 * Created by pc on 2017/5/17.
 */

public class MainFragment extends BaseFragment implements BottomNavigationBar.OnTabSelectedListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURS = 3;
    int lastSelectedPosition = 0;
    int prePosition = 0;

    private SupportFragment[] mFragments = new SupportFragment[4];

    BottomNavigationBar bottomNavigationBar;
    FrameLayout mFrameLayout;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState == null) {
            mFragments[FIRST] = FirstTabFragment.newInstance();
            mFragments[SECOND] = SecondTabFragment.newInstance();
            mFragments[THIRD] = ThirdTabFragment.newInstance();
            mFragments[FOURS] = FoursTabFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURS]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findChildFragment(FirstTabFragment.class);
            mFragments[SECOND] = findChildFragment(SecondTabFragment.class);
            mFragments[THIRD] = findChildFragment(ThirdTabFragment.class);
            mFragments[FOURS] = findChildFragment(FoursTabFragment.class);
        }

        initView(view);
        return view;

    }

    private void initView(View view) {
        EventBus.getDefault().register(this);
        bottomNavigationBar = (BottomNavigationBar) view.findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setActiveColor(R.color.white)
                .setBarBackgroundColor(R.color.blue);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE
                );
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_list_black_24dp, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_add_black_24dp, "发布"))
                .addItem(new BottomNavigationItem(R.drawable.ic_notifications_none_black_24dp, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_outline_black_24dp, "我"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setAutoHideEnabled(true);

        mFrameLayout =(FrameLayout) view.findViewById(R.id.fl_tab_container);

        setDefaultFragment();


    }
    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
   //     loadRootFragment(R.id.fl_tab_container, mFragments[FIRST]);
    }

    @Override
    public void onTabSelected(int position) {
        showHideFragment(mFragments[position], mFragments[prePosition]);
        prePosition = position;
        if(position == 0) {
            bottomNavigationBar.setAutoHideEnabled(true);

            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)mFrameLayout.getLayoutParams();
            lp.bottomMargin=0;
            mFrameLayout.setLayoutParams(lp);
        }else {
            bottomNavigationBar.setAutoHideEnabled(FALSE);

            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)mFrameLayout.getLayoutParams();
            lp.bottomMargin=52;
            mFrameLayout.setLayoutParams(lp);
        }
        }

    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {
        // 这里推荐使用EventBus来实现 -> 解耦
        // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
        // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
        EventBus.getDefault().post(new TabSelectedEvent(position));
    }

    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }



}



