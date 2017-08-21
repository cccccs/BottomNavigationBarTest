package activitytest.com.example.bottomnavigationbartest.ui.fragment.third;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.InformAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;
import activitytest.com.example.bottomnavigationbartest.db.Inform;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.event.TabSelectedEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.MainFragment;

/**
 * Created by pc on 2017/4/27.
 */

public class InformMesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout mRefreshLayout;
    RecyclerView mRecy;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private InformAdapter mAdapter;

    public static InformMesFragment instance(){
        Bundle args = new Bundle();

        InformMesFragment fragment = new InformMesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.message_inform, container, false);

        initView(view);
        return view;
    }

    public void initView(View view){
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });
        mAdapter = new InformAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });



        List<Inform> informList = initDatas();
        mAdapter.setDatas(informList);
    }

    public List<Inform> initDatas() {
        List<Inform> informList = new ArrayList<>();
        List<Job> jobList = initJobs();

        String[] phone = new String[]{"554545", "5757545", "8888877"};
        String[] time = new String[]{"2017.8.20","2017.8.21","2017.8.22"};
        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 3);
            Inform inform = new Inform();
            inform.setPhone(phone[index]);
            inform.setTime(time[index]);
            inform.setJob(jobList.get(i));
            informList.add(inform);
        }
        return informList;
    }

    public List<Job> initJobs(){

        List<Job> jobList = new ArrayList<>();
        String workName[] = new String[]{"托管班兼职","招聘兼职文字编辑"};
        String workPay[] = new String[]{"40元/时","80元/天"};
        String workPlace[] = new String[]{"金明>30KM","崂山区内"};
        String publishTime[] = new String[]{"2017-08-17","2017-08-19 "};
        String workType[] = new String[]{"家教 ", "其他"};
        String PeopleNum[] = new String[]{"1人","2人"};
        String jobSex[] = new String[]{"男","不限"};
        String workData[] = new String[]{"8月-9月", "长期接受报名"};
        String workTime[] = new String[]{" 下午3点到5点" ,"上午9点到下午5点"};
        String workContent[] = new String[]{"要求：有初中语文教学经验，善于沟通表达，课堂活跃的大学生老师。\n 要求汉语言专业大学生。 \n男大学生优先，女大学生也可以考虑。\n有意者请联系选师无忧伍老师13610045402 ","一、直招APP下载兼职：\n1、负责去建材市场、五金机电市场、婚庆花店、广告打印、水果蔬菜批发市场。带着优惠券和小礼品给商户讲解货拉拉这款拉货平台，对于有意向商户教给他们下载使用货拉拉软件。\n2、要求踏实认真、飞机王勿扰。二、兼职绩效标准：1、底薪100+提成+奖金。2、工资第二天结，打到银行卡。\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\n"};
        String havePeopleNum[] = new String[]{"10人","5人"};



        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 2);
            Job job = new Job();
            job.setWorkName(workName[index]);
            job.setWorkPay(workPay[index]);
            job.setWorkPlace(workPlace[index]);
            job.setPublishTime(publishTime[index]);
            job.setWorkType(workType[index]);
            job.setPeopleNum(PeopleNum[index]);
            job.setJobSex(jobSex[index]);
            job.setWorkData(workData[index]);
            job.setWorkTime(workTime[index]);
            job.setWorkContent(workContent[index]);
            job.setHavePeopleNum(havePeopleNum[index]);
            jobList.add(job);
        }
        return jobList;
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.SECOND) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }

}
