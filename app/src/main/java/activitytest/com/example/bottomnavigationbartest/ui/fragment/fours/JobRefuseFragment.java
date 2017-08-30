package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;

/**
 * Created by pc on 2017/8/30.
 */

public class JobRefuseFragment extends BaseFragment {
    public static JobRefuseFragment newInstance(){
        Bundle args = new Bundle();
        JobRefuseFragment fragment = new JobRefuseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_job_refuse,container,false);
        return view;
    }
}
