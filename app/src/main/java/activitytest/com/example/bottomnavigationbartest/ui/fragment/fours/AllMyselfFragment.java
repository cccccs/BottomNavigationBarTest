package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.User;

/**
 * Created by pc on 2017/8/20.
 */

public class AllMyselfFragment extends BaseBackFragment {
    Toolbar toolbar;
    EditText userName,userAge,userSex,userPhone,userGrade;
    LinearLayout gradeLinear;
    CheckBox checkBox1,checkBox2;

    MyApplication myApplication;
    User loginUser;

    public static AllMyselfFragment newInstance(){
        Bundle args = new Bundle();
        AllMyselfFragment fragment = new AllMyselfFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_all_myself,container,false);
        myApplication = (MyApplication)getActivity().getApplication();
        loginUser =myApplication.getLoginUser();

        initView(view);
        return view;
    }

    private void initView(View view){
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        gradeLinear = (LinearLayout) view.findViewById(R.id.linear_grade);
        checkBox1 = (CheckBox) view.findViewById(R.id.cb1);
        checkBox2 = (CheckBox) view.findViewById(R.id.cb2);

        initToolbarNav(toolbar);

        if(loginUser instanceof Employer)
            gradeLinear.setVisibility(View.INVISIBLE);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkBox2.setChecked(false);
                }else {
                    checkBox2.setChecked(true);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkBox1.setChecked(false);
                }
                else {
                    checkBox1.setChecked(true);
                }
            }
        });
    }
}
