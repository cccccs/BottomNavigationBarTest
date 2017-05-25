package activitytest.com.example.bottomnavigationbartest.ui.fragment.second;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;

/**
 * Created by pc on 2017/3/1.
 */

public class NotPublishFragment extends BaseFragment {
    public static NotPublishFragment instance(){
        Bundle args = new Bundle();
        NotPublishFragment fragment = new NotPublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.publish_not, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"FAB clicked",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
