package activitytest.com.example.bottomnavigationbartest.ui.fragment.first;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.JobAdapter;
import activitytest.com.example.bottomnavigationbartest.adpater.TypeAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseMainFragment;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.TypeOfJob;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;
import activitytest.com.example.bottomnavigationbartest.ui.view.MyItemDecoration;
import activitytest.com.example.bottomnavigationbartest.ui.view.MyToolBar;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;


/**
 * Created by pc on 2016/11/12.
 */
public class FirstTabFragment extends BaseMainFragment implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String>, MyToolBar.OnClickRefresh{
    private  List<Job> jobList =new ArrayList<>();
    private List<TypeOfJob> typeList = new ArrayList<>();


    RecyclerView jobRecyclerView;
    RecyclerView typeRecyclerView;
    BGABanner hBgaBanner;
    NestedScrollView mNestScrollView;
    SwipeRefreshLayout mRefreshLayout;
    public static FirstTabFragment newInstance(){
        Bundle args = new Bundle();
        FirstTabFragment fragment = new FirstTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_first, container, false);
    //    sendRequestWithHttpURLConnection();
        initJobs();
        initTypes();
        initView(view);

        //        mEngine = ((App)getActivity().getApplication()).getInstance().getEngine();
//            loadData(hBgaBanner, 5);
//        mSearchView=(SearchView) view.findViewById(R.id.home_searchview);
//        mSearchView.setIconified(false);
//        mSearchView.setIconifiedByDefault(false);
//        mSearchView.setFocusable(false);
//        mSearchView.setFocusableInTouchMode(false);
//        mSearchView.requestFocus();
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.));
        return view;
    }
    @Override
    public  void onClick(){
    requestJob();

    }

    private void initView(View view){
        jobRecyclerView = (RecyclerView) view.findViewById(R.id.job_rec);
        typeRecyclerView = (RecyclerView) view.findViewById(R.id.type_rec);
        hBgaBanner =(BGABanner) view.findViewById(R.id.banner);
        mNestScrollView = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);
     //   mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);

        //兼职加载显示
        LinearLayoutManager jobLayoutManager = new LinearLayoutManager(getActivity());
        jobRecyclerView.setLayoutManager(jobLayoutManager);
        jobRecyclerView.addItemDecoration(new MyItemDecoration());
        requestJob();
        jobRecyclerView.setNestedScrollingEnabled(false);

        //广告加载显示
        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.drawable.holder));
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.drawable.myself_image));
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.drawable.holder));
        hBgaBanner.setData(views);

        //兼职类型加载显示
        LinearLayoutManager typeLayoutManager = new LinearLayoutManager(getActivity());
        typeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeRecyclerView.setLayoutManager(typeLayoutManager);
        final TypeAdapter typeAdapter = new TypeAdapter(_mActivity);
        typeRecyclerView.setAdapter(typeAdapter);
        typeAdapter.setDatas(typeList);
        typeRecyclerView.setNestedScrollingEnabled(false);

        typeAdapter.setOnItemCLickLister(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                EventBus.getDefault().post(new StartBrotherEvent(SearchFragment.newInstance(typeAdapter.getType(position).getTypeName())));
            }
        });

       // mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
       // mRefreshLayout.setOnRefreshListener(this);

    }


    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
               requestJob();

            }
        }, 2500);
    }
    //获取兼职信息
    public void requestJob(){
        String url  ="http://119.29.3.128:8080/JobHunter/search";

        JSONObject object = new JSONObject();
        try {
            object.put("keyword","all");
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON,object.toString());

        HttpUtil.postOkHttpRequest(url,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity,"请求失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().toString();
                final List<Job> jobList = Utility.handleJobResponse(responseText);
                if(jobList != null){
                    showJobListInfo(jobList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity,"刷新成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    showJobListInfo(jobList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity,"无信息", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

    private void showJobListInfo(List<Job> jobList){
        JobAdapter jobAdapter = new JobAdapter(_mActivity);
        jobRecyclerView.setAdapter(jobAdapter);
        if(jobList!=null) {
            jobAdapter.setDatas(jobList);
        }else{
            jobList = initJobs();
            jobAdapter.setDatas(jobList);
        }

    }

    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("FirstFragment","status");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://119.29.3.128:8080/JobHunter/login")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("FirstFragment","status is");
                    parseJSONWithJSONObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{

            Log.d("FirstFragment","jsonData "+jsonData);

            JSONObject object = new JSONObject(jsonData);
            String status = object.getString("status");
            Log.d("FirstFragment","status is is "+ status);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

//    private void loadData(final BGABanner banner, final int count) {
//        ((App)getActivity().getApplication()).getInstance().getEngine().fetchItemsWithItemCount(count).enqueue(new Callback<BannerModel>() {
//            @Override
//            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
//                BannerModel bannerModel = response.body();
//
//                /**
//                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
//                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
//                 */
////                banner.setAutoPlayAble(bannerModel.imgs.size() > 1);
//
//                banner.setAdapter(FirstTabFragment.this);
//                banner.setData(bannerModel.imgs, bannerModel.tips);
//            }
//
//            @Override
//            public void onFailure(Call<BannerModel> call, Throwable t) {
//                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //待设置为private
    private List<Job> initJobs(){

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



        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 2);
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

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
        Toast.makeText(banner.getContext(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
    }

    private void initTypes(){
        typeList.clear();
        TypeOfJob item1 = new TypeOfJob();
        item1.setJobType("校内四助");
        item1.setImageType(R.drawable.gridview_1);
        typeList.add(item1);
        TypeOfJob item2 = new TypeOfJob();
        item2.setJobType("家教");
        item2.setImageType(R.drawable.gridview_7);
        typeList.add(item2);
        TypeOfJob item3 = new TypeOfJob();
        item3.setJobType("派单");
        item3.setImageType(R.drawable.gridview_10);
        typeList.add(item3);
        TypeOfJob item4 = new TypeOfJob();
        item4.setJobType("服务员");
        item4.setImageType(R.drawable.gridview_9);
        typeList.add(item4);
        TypeOfJob item5 = new TypeOfJob();
        item5.setJobType("销售");
        item5.setImageType(R.drawable.gridview_5);
        typeList.add(item5);
        TypeOfJob item6 = new TypeOfJob();
        item6.setJobType("调研");
        item6.setImageType(R.drawable.gridview_6);
        typeList.add(item6);

    }

    @Override
    public void onDetach(){
        super.onDetach();

    }


}
