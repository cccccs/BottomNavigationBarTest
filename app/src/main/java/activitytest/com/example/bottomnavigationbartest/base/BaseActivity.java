package activitytest.com.example.bottomnavigationbartest.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import activitytest.com.example.bottomnavigationbartest.ActivityCollector;
import activitytest.com.example.bottomnavigationbartest.ui.view.CommonFilterPop;

/**
 * Created by pc on 2017/4/22.
 */

public class BaseActivity extends AppCompatActivity {

    //筛选pop
    private CommonFilterPop mPopupWindow;
    //当前上下文实例
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("BaseAcitivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
        this.activity = this;
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
