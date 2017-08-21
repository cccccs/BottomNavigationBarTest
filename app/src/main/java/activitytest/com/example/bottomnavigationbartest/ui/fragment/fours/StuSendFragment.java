package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;

/**
 * Created by pc on 2017/8/20.
 */

public class StuSendFragment extends BaseBackFragment {
    Toolbar toolbar;
    public static StuSendFragment newInstance(){
        Bundle args = new Bundle();

        StuSendFragment fragment = new StuSendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_stu_send,container,false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("我的申请");
        initToolbarNav(toolbar);
        return view;
    }
}
