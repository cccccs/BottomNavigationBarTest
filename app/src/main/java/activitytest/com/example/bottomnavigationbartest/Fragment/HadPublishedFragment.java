package activitytest.com.example.bottomnavigationbartest.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitytest.com.example.bottomnavigationbartest.R;

/**
 * Created by pc on 2017/3/1.
 */

public class HadPublishedFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.publish_done,container,false);

        return view;
    }


}
