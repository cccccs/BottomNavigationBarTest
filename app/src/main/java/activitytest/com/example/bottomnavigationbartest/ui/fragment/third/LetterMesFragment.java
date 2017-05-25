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

public class LetterMesFragment extends BaseFragment {
    public static LetterMesFragment instance(){

        Bundle args = new Bundle();

        LetterMesFragment fragment = new LetterMesFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.message_letter,container, false);
        return view;
    }

}
