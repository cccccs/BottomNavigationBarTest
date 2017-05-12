package activitytest.com.example.bottomnavigationbartest;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/4/23.
 */

public class CommonFilterPop extends PopupWindow {

    //布局填充器
    private LayoutInflater mInflater;
    //上下文
    private Context mContext;
    //只显示string类型的数据
    private List<String> mDatas = new ArrayList<>();
    //pop整体view
    private View popupView;
    //选择条件的list
    private ListView contentLv;
    //筛选条件选择后的回调
    AdapterView.OnItemClickListener itemClickListener;
    //适配器
   private CommPopAdapter adapter;

    /*
    构造函数
    @param context
    @param mDatas
     */

    public CommonFilterPop(Context context,List<String> mDatas){
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
        popupView = mInflater.inflate(R.layout.common_popup_list_dialog,null);

        //设置view
        this.setContentView(popupView);
        //设置弹出窗体的高度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //初始控件
        initPopView();
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        //设置动画效果
        //this.setAnimationStyle();
        this.update();
    }

    private void initPopView(){
        contentLv = (ListView)popupView.findViewById(R.id.lv_pop);
        adapter = new CommPopAdapter(mContext,mDatas);
        contentLv.setAdapter(adapter);
    }
    /*
    listview点击事件
    @param itemClickListener
     */
    public void setOnItemSelectedListener(AdapterView.OnItemClickListener itemClickListener){
        if(null != itemClickListener && null != contentLv){
            contentLv.setOnItemClickListener(itemClickListener);
        }
    }
}
