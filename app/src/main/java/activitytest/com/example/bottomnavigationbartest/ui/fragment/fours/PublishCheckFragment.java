package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;

/**
 * Created by pc on 2017/8/27.
 */

public class PublishCheckFragment extends BaseFragment {

    public static PublishCheckFragment newInstance(){
        PublishCheckFragment fragment = new PublishCheckFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.publish_checked,container,false);
        return view;
        }

}
