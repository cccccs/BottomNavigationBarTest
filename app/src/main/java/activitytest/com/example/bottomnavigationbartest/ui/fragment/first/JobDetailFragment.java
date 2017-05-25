package activitytest.com.example.bottomnavigationbartest.ui.fragment.first;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;

/**
 * Created by pc on 2017/5/8.
 */

public class JobDetailFragment extends BaseBackFragment {

    TextView mJobDetailName;
    TextView mJobDetailSalre;
    TextView mJobDetailLoc;
    TextView mJobDetailDate;
    TextView mJobType;
    TextView mJobPeopleNum;
    TextView mJobSex;
    TextView mWorkDate;
    TextView mWorkTime;
    TextView mWorkContent;
    LinearLayout mCollectLinear;
    LinearLayout mSignUpLinear;

    public static JobDetailFragment newInstance() {
        JobDetailFragment fragment = new JobDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_job_detail,container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        mJobDetailName = (TextView)view.findViewById(R.id.job_detail_name);
        mJobDetailSalre = (TextView)view.findViewById(R.id.job_detail_salary);
        mJobDetailLoc = (TextView) view.findViewById(R.id.job_detail_place);
        mJobDetailDate = (TextView) view.findViewById(R.id.job_detail_publish_time);
        mJobType = (TextView) view.findViewById(R.id.job_detail_job_type);
        mJobPeopleNum = (TextView) view.findViewById(R.id.job_detail_person_num);
        mJobSex = (TextView) view.findViewById(R.id.job_detail_sex);
        mWorkDate = (TextView) view.findViewById(R.id.job_detail_job_date);
        mWorkTime =(TextView)view.findViewById(R.id.job_detail_work_time);
        mWorkContent = (TextView) view.findViewById(R.id.job_detail_job_content);
        mCollectLinear = (LinearLayout)view.findViewById(R.id.job_detail_collect);
        mSignUpLinear = (LinearLayout) view.findViewById(R.id.signUp);
        mJobDetailName.setText("招聘兼职文字编辑");
        mJobDetailSalre.setText("80元/天");
        mJobDetailLoc.setText("金明>30KM");
        mJobDetailDate.setText("2016-08-17");
        mJobType.setText("家教");
        mJobPeopleNum.setText("1人");
        mJobSex.setText("不限");
        mWorkDate.setText("8月-9月");
        mWorkTime.setText("下午3点到5点");
        mWorkContent.setText("要求：有初中语文教学经验，善于沟通表达，课堂活跃的大学生老师。要求汉语言专业大学生。男大学生优先，女大学生也可以考虑。有意者请联系选师无忧伍老师13610045402（微");

    }
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
    @Override
    public void onDetach(){
        super.onDetach();
        Toolbar mToolbar =(Toolbar)getActivity().findViewById(R.id.main_toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar)getActivity().findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setVisibility(View.VISIBLE);
    }

}
