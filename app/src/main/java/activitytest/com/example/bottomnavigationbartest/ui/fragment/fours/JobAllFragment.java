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

public class JobAllFragment extends BaseFragment {
    public static JobAllFragment newInstance(){
        Bundle args = new Bundle();

        JobAllFragment fragment = new JobAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_job_all,container,false);
        return view;
    }
}
