package activitytest.com.example.bottomnavigationbartest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/2/25.
 */

public class SearchResultActivity extends BaseActivity{

    private EditText searchEdit;
    private ImageButton searchDelete;
    List<String> mPlaces = new ArrayList<>();
    List<String> mTypes = new ArrayList<>();
    List<String> mOrders = new ArrayList<>();

    LinearLayout mPlaceAll;
    CheckBox mPlaceCb;
    LinearLayout mTypeAll;
    CheckBox mTypeCb;
    LinearLayout mOrderAll;
    CheckBox mOrderCb;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //上部搜索栏
        searchEdit = (EditText)findViewById(R.id.search_edit);
        searchDelete = (ImageButton) findViewById(R.id.search_delete);
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
        initView();
    }

    /**
     * 初始化数据
     */
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

    //初始化控件
    private void initView(){
        mPlaceAll = (LinearLayout) findViewById(R.id.place_linear);
        mTypeAll = (LinearLayout) findViewById(R.id.type_linear);
        mOrderAll = (LinearLayout) findViewById(R.id.order_linear);
        mPlaceCb = (CheckBox) findViewById(R.id.place_cb);
        mTypeCb = (CheckBox) findViewById(R.id.type_cb);
        mOrderCb = (CheckBox) findViewById(R.id.order_cb);

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

        //
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
}

