package activitytest.com.example.bottomnavigationbartest.ui.fragment.first;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.ui.view.CommonFilterPop;

/**
 * Created by pc on 2017/8/22.
 */

public class SearchFragment extends BaseBackFragment {

    private static final String ARG_TYPE = "arg_type";
    private String getType=" ";

    private EditText searchEdit;
    private ImageButton searchDelete;
    List<String> mPlaces = new ArrayList<>();
    List<String> mTypes = new ArrayList<>();
    List<String> mOrders = new ArrayList<>();


    //筛选pop
    private CommonFilterPop mPopupWindow;

    LinearLayout mPlaceAll;
    CheckBox mPlaceCb;
    LinearLayout mTypeAll;
    CheckBox mTypeCb;
    LinearLayout mOrderAll;
    CheckBox mOrderCb;

    public static SearchFragment newInstance(){
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchFragment newInstance(String param1){
        SearchFragment fragment = new SearchFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TYPE,param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        getType = (String)getArguments().getString(ARG_TYPE);
        initView(view);
        return view;
    }

    private void initView(View view){
        //上部搜索栏
        searchEdit = (EditText)view.findViewById(R.id.search_edit);
        searchDelete = (ImageButton) view.findViewById(R.id.search_delete);
        searchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdit.setText("");
            }
        });

        /*
        设置文本输入变化监听
         */
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    searchDelete.setVisibility(View.VISIBLE);
                }else{
                    searchDelete.setVisibility(View.GONE);
                }
            }
        });
        initDate();

        Log.d("SearchFragment","getType ?= null"+String.valueOf(getType != null));
        //firstTab快捷搜索1
        if(getType != null){
            searchEdit.setText(getType);
        }

        //筛选框
        mPlaceAll = (LinearLayout) view.findViewById(R.id.place_linear);
        mTypeAll = (LinearLayout) view.findViewById(R.id.type_linear);
        mOrderAll = (LinearLayout) view.findViewById(R.id.order_linear);
        mPlaceCb = (CheckBox) view.findViewById(R.id.place_cb);
        mTypeCb = (CheckBox) view.findViewById(R.id.type_cb);
        mOrderCb = (CheckBox) view.findViewById(R.id.order_cb);

        mPlaceAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlaceCb.isChecked())
                    mPlaceCb.setChecked(false);
                else
                    mPlaceCb.setChecked(true);
            }
        });

        mTypeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTypeCb.isChecked())
                    mTypeCb.setChecked(false);
                else
                    mTypeCb.setChecked(true);
            }
        });

        mOrderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrderCb.isChecked())
                    mOrderCb.setChecked(false);
                else
                    mOrderCb.setChecked(true);
            }
        });

        mPlaceCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,mPlaceAll,mPlaces,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mPlaceCb.setText(mPlaces.get(position));
                    }
                },mPlaceCb,mTypeCb,mOrderCb);
            }
        });

        mTypeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,mTypeAll,mTypes,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mTypeCb.setText(mTypes.get(position));
                    }
                },mTypeCb,mPlaceCb,mOrderCb);
            }
        });

        mOrderCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,mOrderAll,mOrders,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mOrderCb.setText(mOrders.get(position));
                    }
                },mOrderCb,mPlaceCb,mTypeCb);
            }
        });
    }



    //初始化数据
    private void initDate(){
        //初始化区域
        mPlaces.add("全青岛");
        mPlaces.add("校内");
        mPlaces.add("崂山区");
        mPlaces.add("李沧区");
        mPlaces.add("市北区");
        mPlaces.add("市南区");
        mPlaces.add("四方区");
        mPlaces.add("城阳区");
        //初始化类型
        mTypes.add("不限");
        mTypes.add("校内四助");
        mTypes.add("家教");
        mTypes.add("派单");
        mTypes.add("服务员");
        mTypes.add("销售");
        mTypes.add("调研");
        mTypes.add("调研");
        mTypes.add("实习");
        mTypes.add("其他");
        //初始化
        mOrders.add("综合排序");
        mOrders.add("最新发布");
        mOrders.add("离我最近");
        mOrders.add("薪资最高");

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
        mPopupWindow = new CommonFilterPop(_mActivity,itemTexts);
        mPopupWindow.setOnDismissListener(dismissListener);

        //绑定筛选点击事件
        mPopupWindow.setOnItemSelectedListener(itemClickListener);
        //如果透明度设置为0,则默认设置成0
        if(0 == alpha){
            alpha = 0.6f;
        }

        //设置背景透明度
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        _mActivity.getWindow().setAttributes(lp);

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
        Log.d("SearchResultActivity","isCheacked"+String.valueOf(isChecked));
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
            },0);
        } else{
            //关闭checkBox时直接隐藏popupwindows
            hidPopListView();
        }
    }
/*
*自定义OnDismissListener
 */
private class CustomerDismissListener implements PopupWindow.OnDismissListener{
    @Override
    public void onDismiss(){
        //当pop消失时，重置背景透明度q2w
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        _mActivity.getWindow().setAttributes(lp);
    }
}


    //隐藏pop
    public void hidPopListView(){
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
