package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.ChoiceAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseMainFragment;
import activitytest.com.example.bottomnavigationbartest.db.Choice;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.event.LoginCancelEvent;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherResEvent;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pc on 2016/11/15.
 */
public class FoursTabFragment extends BaseMainFragment implements LoginFragment.OnLoginSuccessListener {
    CardView loginCardView;
    CircleImageView ImgMys;
    TextView TvName;
    RecyclerView mRecyclerView;
    TextView logOut;
    Toolbar mToolbar;
    MyApplication mApplication;
    User loginUser;
    ChoiceAdapter adapter;

    private List<Choice> choiceList ;

    private static final int REQ_MODIFY_FRAGMENT = 100;

    public static FoursTabFragment newInstance() {
        FoursTabFragment fragment = new FoursTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static FoursTabFragment newInstance(String param1) {
        FoursTabFragment fragment = new FoursTabFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fours, container, false);
        mApplication =(MyApplication)getActivity().getApplication();
        loginUser = mApplication.getLoginUser();

//        Bundle bundle = getArguments();
//        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView)view.findViewById(R.id.tv_books);
////        tv.setText(agrs1);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.myself_toolbar);
//        toolbar.setTitle("个人中心");

        initView(view);
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d("MainActivity","FoursOnStart");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d("MainActivity","FoursResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d("MainActivity","FoursPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d("MainActivity","FoursStop");
    }
    private void initView(View view){
        EventBus.getDefault().register(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mToolbar =(Toolbar) view.findViewById(R.id.toolbar);
        loginCardView = (CardView)view.findViewById(R.id.login_my_home);
        ImgMys= (CircleImageView) view.findViewById(R.id.img_mys);
        TvName = (TextView)view.findViewById(R.id.name_mys);
        logOut = (TextView)view.findViewById(R.id.logout_textView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });

        adapter = new ChoiceAdapter(_mActivity);
        mRecyclerView.setAdapter(adapter);



        adapter.setOnItemClickListener(new OnItemClickListener() {//点击事件
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                Log.d("MainActivity","loginUser.getlogin()"+loginUser.getlogin());
                Log.d("MainFragment","loginUser instanceof Employer "+String.valueOf(loginUser instanceof Employer));
                if(!loginUser.getlogin()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("未登录");
                    dialog.setMessage("请先登录！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EventBus.getDefault().post(new StartBrotherResEvent(LoginFragment.newInstance()));
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();


                }else{
                    if(loginUser instanceof Employer){
                        Log.d("MainActivity","FoursStop");
                        switch (position){

                            case 0:
                                EventBus.getDefault().post(new StartBrotherEvent(AllMyselfFragment.newInstance()));
                                break;
                            case 1:
                                EventBus.getDefault().post(new StartBrotherEvent(EmpDetailFragment.newInstance()));
                                break;
                            case 2:
                                EventBus.getDefault().post(new StartBrotherEvent(EmpPublishFragment.newInstance()));
                                break;
                        }


                    }else {
                        switch (position){
                            case 0:
                                Log.d("MainActivity","ssss1");
                                EventBus.getDefault().post(new StartBrotherEvent(AllMyselfFragment.newInstance()));
                                break;
                            case 1:
                                Log.d("MainActivity","ssss2");
                                EventBus.getDefault().post(new StartBrotherEvent(StuStarFragment.newInstance()));
                                break;
                            case 2:
                                Log.d("MainActivity","ssss3");
                                EventBus.getDefault().post(new StartBrotherEvent(StuJobFragment.newInstance()));
                                break;
//                            case 3:
//                                Log.d("MainActivity","ssss4");
//                                EventBus.getDefault().post(new StartBrotherEvent());
//                                break;
                        }
                    }
                }
            }
        });


        Glide.with(this).load(R.mipmap.ic_launcher).into(ImgMys);

        mToolbar.setTitle("个人中心");


       if(!loginUser.getlogin()){
            logOut.setVisibility(View.INVISIBLE);
           choiceList = initStuChoice();
           adapter.setDatas(choiceList);
           adapter.notifyDataSetChanged();
        }else{
           logOut.setVisibility(View.VISIBLE);
           TvName.setText(loginUser.getName());
           ImgMys.setImageResource(R.mipmap.ic_launcher);
           if(loginUser instanceof Employer){
               choiceList = initEmpChoice();
               adapter.setDatas(choiceList);
               adapter.notifyDataSetChanged();

           }else{
               choiceList = initStuChoice();
               adapter.setDatas(choiceList);
               adapter.notifyDataSetChanged();
           }
       }

        loginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!loginUser.getlogin())
                EventBus.getDefault().post(new StartBrotherResEvent(LoginFragment.newInstance()));

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("退出登录");
                dialog.setMessage("是否确认退出登录？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        mApplication.userLogout();
                        EventBus.getDefault().post(new StartBrotherResEvent(LoginFragment.newInstance()));
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }

    private List<Choice> initStuChoice(){
        List<Choice> choiceList = new ArrayList<>();
        Choice item1 = new Choice();
//        item1.setChoice("个人资料");
//        item1.setImageId(R.drawable.ic_create_black_24dp);
//        choiceList.add(item1);
        Choice item2 = new Choice();
        item2.setChoice("我的简历");
        item2.setImageId(R.drawable.ic_assignment_black_24dp);
        choiceList.add(item2);
        Choice item3 = new Choice();
        item3.setChoice("我的收藏");
        item3.setImageId(R.drawable.ic_star_black);
        choiceList.add(item3);
        Choice item4 = new Choice();
        item4.setChoice("我的申请");
        item4.setImageId(R.drawable.ic_near_me_black_24dp);
        choiceList.add(item4);
        Choice item5 = new Choice();
        item5.setChoice("意见反馈");
        item5.setImageId(R.drawable.ic_near_me_black_24dp);
        choiceList.add(item5);
        return choiceList;
    }

    private List<Choice> initEmpChoice(){
        List<Choice> choiceList = new ArrayList<>();
        Choice item1 = new Choice();
        item1.setChoice("个人资料");
        item1.setImageId(R.drawable.ic_create_black_24dp);
        choiceList.add(item1);
        Choice item2 = new Choice();
        item2.setChoice("商家简介");
        item2.setImageId(R.drawable.ic_location_city_black_24dp);
        choiceList.add(item2);
        Choice item5 = new Choice();
        item5.setChoice("我的发布");
        item5.setImageId(R.drawable.ic_flag_black_24dp);
        choiceList.add(item5);
        Choice item6 = new Choice();
        item6.setChoice("意见反馈");
        item6.setImageId(R.drawable.ic_near_me_black_24dp);
        choiceList.add(item6);
        return choiceList;
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }

//    @Subscribe
//    public void loginSuccess(LoginSuccessEvent event){
//        TvName.setText(event.getAccount());
//        ImgMys.setImageResource(R.mipmap.ic_launcher);
//        logOut.setVisibility(View.VISIBLE);
//        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
//        if(loginUser.getUserType()== student){
//            choiceList = initStuChoice();
//            adapter.setDatas(choiceList);
//            adapter.notifyDataSetChanged();
//        }else{
//            choiceList = initEmpChoice();
//            adapter.setDatas(choiceList);
//            adapter.notifyDataSetChanged();
//        }
//
//    }

    @Subscribe
    public void loginCancel(LoginCancelEvent event){
        loginUser = mApplication.getLoginUser();
        if(!loginUser.getlogin()){
            logOut.setVisibility(View.INVISIBLE);
            TvName.setText("请登录/注册");
            choiceList = initStuChoice();
            adapter.setDatas(choiceList);
            adapter.notifyDataSetChanged();
        }
    }


    //接口实现改变界面
    @Override
    public void onLoginSuccess(String account) {
        TvName.setText(account);
        ImgMys.setImageResource(R.mipmap.ic_launcher);
        logOut.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
        mApplication=(MyApplication)getActivity().getApplication();
        loginUser = mApplication.getLoginUser();
        if(loginUser instanceof Employer){
            choiceList = initEmpChoice();
            adapter.setDatas(choiceList);
            adapter.notifyDataSetChanged();

        }else{
            choiceList = initStuChoice();
            adapter.setDatas(choiceList);
            adapter.notifyDataSetChanged();
        }
        Log.d("FoursTabFragment","SessionTest:"+loginUser.getSession());
    }

}