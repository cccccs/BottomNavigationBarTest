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

public class StuStarFragment extends BaseBackFragment {
    Toolbar toolbar;
    public static StuStarFragment newInstance(){
        Bundle args = new Bundle();

        StuStarFragment fragment = new StuStarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_stu_star,container,false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("我的收藏");
        initToolbarNav(toolbar);
        return view;
    }


}
