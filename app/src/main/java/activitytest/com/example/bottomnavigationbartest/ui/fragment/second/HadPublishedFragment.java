package activitytest.com.example.bottomnavigationbartest.ui.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;

/**
 * Created by pc on 2017/3/1.
 */

public class HadPublishedFragment extends BaseFragment {
    public static HadPublishedFragment instance(){
        Bundle args = new Bundle();
        HadPublishedFragment fragment = new HadPublishedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.publish_done,container,false);

        return view;
    }


}
