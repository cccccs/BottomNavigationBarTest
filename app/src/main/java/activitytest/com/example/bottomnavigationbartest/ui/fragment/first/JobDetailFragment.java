package activitytest.com.example.bottomnavigationbartest.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Job;

/**
 * Created by pc on 2017/5/8.
 */

public class JobDetailFragment extends BaseBackFragment {
    private static final String ARG_JOB = "arg_job";

    private Job mJob;

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
    TextView mJobHadPeopleNum;
    LinearLayout mCollectLinear;
    LinearLayout mSignUpLinear;

    public static JobDetailFragment newInstance() {
        Bundle args = new Bundle();
        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobDetailFragment newInstance(Job job){
        Bundle args = new Bundle();
        args.putParcelable(ARG_JOB, job);
        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJob = getArguments().getParcelable(ARG_JOB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.first_job_detail,container, false);

        initView(view);
        return view;
    }

    public void initView(View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
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
        mJobHadPeopleNum = (TextView)view.findViewById(R.id.job_detail_had_people_num);
        mCollectLinear = (LinearLayout)view.findViewById(R.id.job_detail_collect);
        mSignUpLinear = (LinearLayout) view.findViewById(R.id.signUp);

        mJobDetailName.setText(mJob.getWorkName());
        mJobDetailSalre.setText(mJob.getWorkPay());
        mJobDetailLoc.setText(mJob.getWorkPlace());
        mJobDetailDate.setText(mJob.getPublishTime());
        mJobType.setText(mJob.getWorkType());
        mJobPeopleNum.setText(mJob.getPeopleNum());
        mJobSex.setText(mJob.getJobSex());
        mWorkDate.setText(mJob.getWorkData());
        mWorkTime.setText(mJob.getWorkTime());
        mWorkContent.setText(mJob.getWorkContent());
        mJobHadPeopleNum.setText(mJob.getHavePeopleNum());

        toolbar.setTitle("兼职详情");
       toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               _mActivity.onBackPressed();
            }
        });

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
    }

}
