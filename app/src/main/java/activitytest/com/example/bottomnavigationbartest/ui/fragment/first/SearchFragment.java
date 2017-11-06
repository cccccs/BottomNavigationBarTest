package activitytest.com.example.bottomnavigationbartest.ui.fragment.first;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.JobAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.ui.view.CommonFilterPop;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by pc on 2017/8/22.
 */

public class SearchFragment extends BaseBackFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_TYPE = "arg_type";
    private String getType=" ";
    private String getSearchText="";

    private List<Job> jobList;

    List<String> mPlaces = new ArrayList<>();
    List<String> mTypes = new ArrayList<>();
    List<String> mOrders = new ArrayList<>();


    private EditText searchEdit;
    private ImageButton searchDelete;

    //筛选pop
    private CommonFilterPop mPopupWindow;

    LinearLayout mPlaceAll;
    CheckBox mPlaceCb;
    LinearLayout mTypeAll;
    CheckBox mTypeCb;
    LinearLayout mOrderAll;
    CheckBox mOrderCb;

    //下拉刷新
    SwipeRefreshLayout mRefreshLayout;
    RecyclerView mRecy;

    JobAdapter mAdapter;

    User loginUser;
    MyApplication myApplication;

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

        myApplication = (MyApplication)getActivity().getApplication();
        loginUser =myApplication.getLoginUser();
        getType = (String)getArguments().getString(ARG_TYPE);
        initView(view);
        return view;
    }

    private void initView(View view){

        searchEdit = (EditText)view.findViewById(R.id.search_edit);
        searchDelete = (ImageButton) view.findViewById(R.id.search_delete);
        mPlaceAll = (LinearLayout)view.findViewById(R.id.place_linear);
        mPlaceCb = (CheckBox) view.findViewById(R.id.place_cb);
        mTypeAll = (LinearLayout)view.findViewById(R.id.type_linear);
        mTypeCb = (CheckBox) view.findViewById(R.id.type_cb);
        mOrderAll = (LinearLayout)view.findViewById(R.id.order_linear);
        mOrderCb = (CheckBox)view.findViewById(R.id.order_cb);
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        mRecy = (RecyclerView) view.findViewById(R.id.recycler_view);

        //上部搜索栏设置
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

        //回车键监听搜索
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    hideSoftInput();
                    mRefreshLayout.setRefreshing(true);
                    onRefresh();//搜索刷新

                    Log.d("SearchFragment","actionID "+event.getAction());
                    return true;
                }
                return false;
            }
        });


        Log.d("SearchFragment","getType ?= null "+String.valueOf(getType != null));
        //getArgument刷新
        if(getType != null){
            searchEdit.setText(getType);
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }

        //筛选框
        initDate();
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
                        mRefreshLayout.setRefreshing(true);
                        onRefresh();
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
                        mRefreshLayout.setRefreshing(true);
                        onRefresh();
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
                        mRefreshLayout.setRefreshing(true);
                        onRefresh();
                    }
                },mOrderCb,mPlaceCb,mTypeCb);
            }
        });

        //RecyclerView
        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new JobAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);

       //下拉刷新设置
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this);

    }//initView();

    @Override
    public void onRefresh(){
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                getSearchJob();
                mRefreshLayout.setRefreshing(false);

            }
        }, 2500);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(2000);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        jobList = initJobs();
//                        mAdapter.setDatas(jobList);
//                        mRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        }).start();
    }

    //网络请求数据
    public void getSearchJob(){
        String address="http://119.29.3.128:8080/JobHunter/search";

        JSONObject json = new JSONObject();
        try {
           json.put("keyword",searchEdit.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON,json.toString());

        HttpUtil.postOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity, "获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                jobList =Utility.handleJobResponse(response.body().string());
                if(jobList!=null) {

                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JobAdapter jobAdapter = new JobAdapter(_mActivity);
                            mRecy.setAdapter(jobAdapter);
                            jobAdapter.setDatas(jobList);
                            Toast.makeText(_mActivity, "获取成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "无相关兼职", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    //网络刷新数据
    private List<Job> initJobs(){
        //Switch（getSearchText);
        List<Job> jobList = new ArrayList<>();
        String workName[] = new String[]{"托管班兼职","招聘App推广"};
        int workPay[] = new int[]{40,60};
        String workPlace[] = new String[]{"金明>30KM","崂山区内"};
        String publishTime[] = new String[]{"2017-08-17","2017-08-19 "};
        String workType[] = new String[]{"家教 ", "其他"};
        String PeopleNum[] = new String[]{"1人","2人"};
        String jobSex[] = new String[]{"男","不限"};
        String workData[] = new String[]{"8月-9月", "长期接受报名"};
        String workTime[] = new String[]{" 下午3点到5点" ,"上午9点到下午5点"};
        String workContent[] = new String[]{"要求：有初中语文教学经验，善于沟通表达，课堂活跃的大学生老师。\n 要求汉语言专业大学生。 \n男大学生优先，女大学生也可以考虑。\n有意者请联系选师无忧伍老师13610045402 ","一、直招APP下载兼职：\n1、负责去建材市场、五金机电市场、婚庆花店、广告打印、水果蔬菜批发市场。带着优惠券和小礼品给商户讲解货拉拉这款拉货平台，对于有意向商户教给他们下载使用货拉拉软件。\n2、要求踏实认真、飞机王勿扰。二、兼职绩效标准：1、底薪100+提成+奖金。2、工资第二天结，打到银行卡。\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\n"};
        String havePeopleNum[] = new String[]{"10人","5人"};
        int imageId[] = new int[]{R.drawable.work_image,R.drawable.work_image};


        int index = (int) (Math.random() * 2);
        for (int i = 0; i < 15; i++) {
            Job job = new Job();
            job.setWorkName(workName[index]);
            job.setWorkPay(workPay[index]);
            job.setWorkPlace(workPlace[index]);
            job.setPublishTime(publishTime[index]);
            job.setWorkType(workType[index]);
            job.setPeopleNum(PeopleNum[index]);
            job.setJobSex(jobSex[index]);
            job.setWorkData(workData[index]);
            job.setWorkTime(workTime[index]);
            job.setWorkContent(workContent[index]);
            job.setHavePeopleNum(havePeopleNum[index]);
            job.setImageId(imageId[index]);
            jobList.add(job);
        }
        return jobList;
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
