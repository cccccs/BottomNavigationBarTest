package activitytest.com.example.bottomnavigationbartest.ui.fragment.third;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;

/**
 * Created by pc on 2017/4/27.
 */

public class InformMesFragment extends BaseFragment{
    public static InformMesFragment instance(){

        Bundle args = new Bundle();

        InformMesFragment fragment = new InformMesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.message_inform, container, false);
        return view;
    }

}
