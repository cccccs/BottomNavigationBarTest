package activitytest.com.example.bottomnavigationbartest;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import activitytest.com.example.bottomnavigationbartest.Fragment.HomeFragment;
import activitytest.com.example.bottomnavigationbartest.Fragment.JobDetailFragment;
import activitytest.com.example.bottomnavigationbartest.Fragment.MessageFragment;
import activitytest.com.example.bottomnavigationbartest.Fragment.MyselfFragment;
import activitytest.com.example.bottomnavigationbartest.Fragment.PublishFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private int bottomNavigationBarHeight;
    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private HomeFragment mHomeFragment;
    private PublishFragment mPublishFragment;
    private MessageFragment mMessageFragment;
    private MyselfFragment mMyselfFragment;
    private  MyToolBar toolBar;
    private NestedScrollView myNestScrollView;
    private JobDetailFragment mJobDetailFragment;
    private boolean havetablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= 21){
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            );
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_main);
        toolBar = (MyToolBar) findViewById(R.id.main_toolbar);
       // toolbar.setTitle("Title");//设getWindow().setStatusBarColor(Color.TRANSPARENT);置主标题
       // toolbar.setSubtitle("Subtitle");//设置子标题
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        myNestScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
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
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setAutoHideEnabled(true);

        setDefaultFragment();


    }
    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = new HomeFragment();
        transaction.replace(R.id.tb, mHomeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.layout_appbar);
        AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams)appBarLayout.getChildAt(0).getLayoutParams();

        mPublishFragment = new PublishFragment();
        //noinspection NullableProblems

        bottomNavigationBarHeight = bottomNavigationBar.getMeasuredHeight();
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                bottomNavigationBar.setAutoHideEnabled(true);
                transaction.replace(R.id.tb, mHomeFragment);
                toolBar.hideTitleView();
                toolBar.ShowSearchView();
                mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP|AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                initNestScrolledViewMargin();
                break;
            case 1:
                if (mPublishFragment == null) {
                    mPublishFragment =new PublishFragment();
                }
                transaction.replace(R.id.tb, mPublishFragment);
                bottomNavigationBar.setAutoHideEnabled(true);
                toolBar.hideSearchView();
                toolBar.setTitle("发布");
                mParams.setScrollFlags(0);
                setNestScrolledViewMargin();
                break;
            case 2:
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                }
                transaction.replace(R.id.tb, mMessageFragment);
                toolBar.hideSearchView();
                bottomNavigationBar.setAutoHideEnabled(true);
                mParams.setScrollFlags(0);
                setNestScrolledViewMargin();
                break;
            case 3:
                if (mMyselfFragment == null) {
                    mMyselfFragment = new MyselfFragment();
                }
                transaction.replace(R.id.tb, mMyselfFragment);
                toolBar.hideSearchView();
                toolBar.setTitle("个人中心");
                mParams.setScrollFlags(0);
                bottomNavigationBar.setAutoHideEnabled(false);
                //mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
                setNestScrolledViewMargin();
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }
    private void setNestScrolledViewMargin(){
        myNestScrollView.setPadding(0,0,0,bottomNavigationBarHeight);
    }
    private void initNestScrolledViewMargin(){
        myNestScrollView.setPadding(0,0,0,0);
    }
    private boolean settablayout(){
        return true;
    }
    /**
     * changeToClassifyFragment（）该方法与changFragmentByTag（）配合使用，可以实现Fragment点击底部标签切换，同样可以实现点击Fragment上的按钮
     * 来控制切换Frgment 在Fargment调用MainActivity的changeToClassifyFragment（）的方法为 ((MainActivity) getActivity()).changeToClassifyFragment();
     * 注意这里必须进行一下强转，Fragment才能调用MainActivity的方法。currentFragment 为当前显示的Fragment 。简单总结希望对大家有帮助
     *((MainActivity) getActivity()).changeToClassifyFragment();
     *  */
    public void changeToClassifyFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if ( mJobDetailFragment == null) {
            mJobDetailFragment = new JobDetailFragment();
        }

          changFragmentByTag(mHomeFragment ,mJobDetailFragment, ft, "");
    }

    public void changFragmentByTag(Fragment currFragment, Fragment chooseFragment, FragmentTransaction ft, String TAG) {
        if (currFragment != chooseFragment) {
            ft.hide(currFragment);
            toolBar.setVisibility(View.GONE);
            bottomNavigationBar.setVisibility(View.GONE);
            if (chooseFragment.isAdded()) {
                ft.show(chooseFragment);
            } else {
                ft.add(R.id.layout_main, chooseFragment, TAG);
            }
        }
        ft.addToBackStack(null);
        ft.commit();

    }
}

