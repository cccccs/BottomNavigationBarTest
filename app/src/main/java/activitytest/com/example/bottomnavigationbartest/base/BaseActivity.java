package activitytest.com.example.bottomnavigationbartest.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.PopupWindow;

import java.util.List;

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

    public void showFilterPopupWindow(View parentView,List<String> itemTexts, AdapterView.OnItemClickListener itemClickListener,CustomerDismissListener dismissListener){
    }
    /*
    *列表选择popupWindow
    * @param parentView 父view
    * @param itemTexts 列表项文本集合
    * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView, List<String> itemTexts, AdapterView.OnItemClickListener itemClickListener, CustomerDismissListener dismissListener, float alpha){
        //判断当前是否显示
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        mPopupWindow = new CommonFilterPop(activity,itemTexts);
        mPopupWindow.setOnDismissListener(dismissListener);

        //绑定筛选点击事件
        mPopupWindow.setOnItemSelectedListener(itemClickListener);
        //如果透明度设置为0,则默认设置成0
        if(0 == alpha){
            alpha = 0.6f;
        }

        //设置背景透明度
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);

        //显示pop
        mPopupWindow.showAsDropDown(parentView);
    }

    /**
     * Tab筛选栏切换
     *
     * @param isChecked 选中状态
     *
     * @param showView 展示pop的根布局
     * @param showMes 展示选择的数据源
     * @param itemClickListener 点击回调
     *  @param tabs 所有的cb
     */
    public void filterTabToggleT(boolean isChecked, View showView, List<String> showMes, AdapterView.OnItemClickListener itemClickListener, final CheckBox...tabs) {
        if (isChecked) {
            if (tabs.length <= 0) {
                return;
            }
            //第一个checkBox为当前选中的cb,其他cb进行setCheck(false)
            for (int i = 1; i < tabs.length; i++) {
                tabs[i].setChecked(false);
            }
            showFilterPopupWindow(showView, showMes, itemClickListener, new CustomerDismissListener() {
                @Override
                public void onDismiss() {
                    super.onDismiss();
                    tabs[0].setChecked(false);
                }
            });
        } else{
            //关闭checkBox时直接隐藏popupwindows
            hidPopListView();
        }
    }
    /*
    *自定义OnDismissListener
     */
    public class CustomerDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss(){
            //当pop消失时，重置背景透明度q2w
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1.0f;
            activity.getWindow().setAttributes(lp);
        }
    }


    //隐藏pop
    public void hidPopListView(){
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
