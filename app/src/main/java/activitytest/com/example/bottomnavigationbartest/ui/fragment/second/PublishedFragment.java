package activitytest.com.example.bottomnavigationbartest.ui.fragment.second;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.ui.view.CommonFilterPop;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by pc on 2017/3/1.
 */

public class PublishedFragment extends BaseBackFragment {
    Toolbar mToolbar;
    CheckBox mJobType,mJobPay,mJobSex,mJobPlace ;

    Button btSend;
    LinearLayout linearTypes;
    LinearLayout linearPays;
    LinearLayout linearSexs;
    LinearLayout linearPlaces;
    List<String> mTypes=new ArrayList<>();
    List<String> mPays=new ArrayList<>();
    List<String> mSexs=new ArrayList<>();
    List<String> mPlaces=new ArrayList<>();

    EditText jobName;
    EditText startTime;
    EditText endTime;
    EditText publishTime;
    EditText phoneNum;

    EditText salary;
    EditText jobDetail;
    EditText workTime;
    EditText workHour;

    Employer loginUser;
    MyApplication myApplication;
    private CommonFilterPop mPopupWindow;

    public static PublishedFragment newInstance(){
        Bundle args = new Bundle();
        PublishedFragment fragment = new PublishedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.publish,container,false);

        myApplication = (MyApplication)getActivity().getApplication();
        loginUser =(Employer)myApplication.getLoginUser();
        initView(view);
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        linearTypes = (LinearLayout)view.findViewById(R.id.linear_type);
        linearPays = (LinearLayout)view.findViewById(R.id.linear_pay);
        linearSexs = (LinearLayout)view.findViewById(R.id.linear_sex);
        linearPlaces = (LinearLayout)view.findViewById(R.id.linear_place);
        mJobType = (CheckBox)view.findViewById(R.id.type_cb);
        mJobPay = (CheckBox)view.findViewById(R.id.pay_cb);
        mJobSex = (CheckBox) view.findViewById(R.id.sex_cb);
        mJobPlace = (CheckBox)view.findViewById(R.id.place_cb);
        btSend = (Button) view.findViewById(R.id.btn_send);

        jobName = (EditText)view.findViewById(R.id.job_name);
        jobDetail = (EditText) view.findViewById(R.id.job_detail_content);
        startTime = (EditText)view.findViewById(R.id.start_time);
        endTime = (EditText)view.findViewById(R.id.end_time);
        phoneNum =(EditText)view.findViewById(R.id.job_phone);
        salary  =(EditText)view.findViewById(R.id.job_pay);
        workTime = (EditText) view.findViewById(R.id.work_time);
        workHour = (EditText)view.findViewById(R.id.work_hours);


        initDate();
        mToolbar.setTitle("发布兼职");
        initToolbarNav(mToolbar);
        mJobType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,linearTypes,mTypes,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mJobType.setText(mTypes.get(position));

                    }
                },mJobType,mJobPay,mJobSex,mJobPlace);
            }
        });
        mJobPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,linearPays,mPays,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mJobPay.setText(mPays.get(position));

                    }
                },mJobPay,mJobType,mJobSex,mJobPlace);
            }
        });
        mJobSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,linearSexs,mSexs,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mJobSex.setText(mSexs.get(position));

                    }
                },mJobSex,mJobPay,mJobType,mJobPlace);
            }
        });

        mJobPlace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked){
                filterTabToggleT(isChecked,linearPlaces,mPlaces,new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,View view,int position,long l){
                        hidPopListView();
                        mJobPlace.setText(mPlaces.get(position));

                    }
                },mJobPlace,mJobType,mJobSex,mJobPay);
            }
        });


        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJobInfo();

            }
        });


    }

    public void sendJobInfo() {
        String url  ="http://119.29.3.128:8080/JobHunter/Employer/publishJob";




        JSONObject json = new JSONObject();
        try {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyy-MM-dd");
            json.put("companyName",loginUser.getCompany()==null?"null":loginUser.getCompany());
            json.put("jobName",jobName.getText());
            json.put("employer_Id",loginUser.getUserId());
            json.put("address",mJobPlace.getText().toString());
            json.put("phoneNumber",phoneNum.getText());
            json.put("salary",salary.getText());
            json.put("detail",jobDetail.getText());
            json.put("workTime",workTime.getText());
            json.put("workHour",workHour.getText());
            json.put("startTime",startTime.getText());
            json.put("endTime",endTime.getText());
            json.put("publishTime",format.format(date));
            Log.d("PublishFragment","DATE"+format.format(date));

        }catch (JSONException e){
            Toast.makeText(_mActivity, e+ "", Toast.LENGTH_SHORT).show();
        }
        RequestBody requestBody = RequestBody.create(JSON,json.toString());
        HttpUtil.postOkHttpRequestWithSession(url, loginUser.getSession(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity, "发送失败 Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(Utility.handleStatusResponse(response.body().string())) {
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "发送成功！", Toast.LENGTH_SHORT).show();


                        }
                    });
                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "发送失败 STATUS", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    //初始化数据
    private void initDate() {
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
        //初始化工资方式
        mPays.add("元/小时");
        mPays.add("元/天");
        mPays.add("元/月");
        mPays.add("元/次");
        mPays.add("元/单");
        //初始化性别
        mSexs.add("不限");
        mSexs.add("男");
        mSexs.add("女");
        //初始化区域
        mPlaces.add("全青岛");
        mPlaces.add("校内");
        mPlaces.add("崂山区");
        mPlaces.add("李沧区");
        mPlaces.add("市北区");
        mPlaces.add("市南区");
        mPlaces.add("四方区");
        mPlaces.add("城阳区");
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
