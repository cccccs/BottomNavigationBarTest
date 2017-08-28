package activitytest.com.example.bottomnavigationbartest.ui.fragment.second;

/**
 * Created by pc on 2017/4/27.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseMainFragment;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.EmpPublishFragment;

/**
 * Created by pc on 2016/11/15.
 */
public class SecondTabFragment extends BaseMainFragment {
    Toolbar mToolbar;
    CardView mPublishJob;
    CardView mMyPublish;

    public static SecondTabFragment newInstance(){
        Bundle args = new Bundle();
        SecondTabFragment fragment = new SecondTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_second, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
         mToolbar =(Toolbar) view.findViewById(R.id.toolbar);
        mPublishJob = (CardView)view.findViewById(R.id.publish_job);
        mMyPublish = (CardView)view.findViewById(R.id.my_publish);

        mPublishJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(PublishedFragment.newInstance()));
            }
        });

        mMyPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(EmpPublishFragment.newInstance()));
            }
        });



        mToolbar.setTitle("发布");


    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }
}