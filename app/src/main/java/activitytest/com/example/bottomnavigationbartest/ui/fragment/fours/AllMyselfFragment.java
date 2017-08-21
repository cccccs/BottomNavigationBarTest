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

public class AllMyselfFragment extends BaseBackFragment {
    Toolbar toolbar;

    public static AllMyselfFragment newInstance(){
        Bundle args = new Bundle();
        AllMyselfFragment fragment = new AllMyselfFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_all_myself,container,false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("我的资料");
        initToolbarNav(toolbar);
        return view;
    }
}
