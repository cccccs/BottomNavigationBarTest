package activitytest.com.example.bottomnavigationbartest.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.App;
import activitytest.com.example.bottomnavigationbartest.db.JobAdapter;
import activitytest.com.example.bottomnavigationbartest.MyItemDecoration;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.db.TypeAdapter;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.TypeOfJob;
import activitytest.com.example.bottomnavigationbartest.engine.Engine;
import activitytest.com.example.bottomnavigationbartest.model.BannerModel;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pc on 2016/11/12.
 */
public class HomeFragment extends Fragment implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String>{

    private SearchView mSearchView;
    private List<Job> jobList = new ArrayList<>();
    private Engine mEngine;
    private List<TypeOfJob> typeList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initJobs();
        initTypes();

        RecyclerView jobRecyclerView = (RecyclerView) view.findViewById(R.id.job_rec);
        RecyclerView typeRecyclerView = (RecyclerView) view.findViewById(R.id.type_rec);
        BGABanner hBgaBanner =(BGABanner) view.findViewById(R.id.banner);

        //兼职加载显示
        LinearLayoutManager jobLayoutManager = new LinearLayoutManager(getActivity());
        jobRecyclerView.setLayoutManager(jobLayoutManager);
        jobRecyclerView.addItemDecoration(new MyItemDecoration());
        JobAdapter jobAdapter = new JobAdapter(jobList);
        jobRecyclerView.setAdapter(jobAdapter);
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
        TypeAdapter typeAdapter = new TypeAdapter(typeList);
        typeRecyclerView.setAdapter(typeAdapter);
        typeRecyclerView.setNestedScrollingEnabled(false);
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

    private void loadData(final BGABanner banner, final int count) {
        ((App)getActivity().getApplication()).getInstance().getEngine().fetchItemsWithItemCount(count).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();

                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
//                banner.setAutoPlayAble(bannerModel.imgs.size() > 1);

                banner.setAdapter(HomeFragment.this);
                banner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

    private void initJobs(){
        jobList.clear();
        Job[] job = new Job[12];
        for(int i = 0 ; i < 12; i++)
        {
             job[i] = new Job("长期介绍报名","2016-08-17","招聘兼职文字编辑","金明>30KM","80元/天",R.drawable.work_image,i);
            jobList.add(job[i]);
        }
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
        item1.setJobType("推广");
        item1.setImageType(R.drawable.gridview_1);
        typeList.add(item1);
        TypeOfJob item2 = new TypeOfJob();
        item2.setJobType("家教");
        item2.setImageType(R.drawable.gridview_2);
        typeList.add(item2);
        TypeOfJob item3 = new TypeOfJob();
        item3.setJobType("实习");
        item3.setImageType(R.drawable.gridview_3);
        typeList.add(item3);
        TypeOfJob item4 = new TypeOfJob();
        item4.setJobType("保安");
        item4.setImageType(R.drawable.gridview_4);
        typeList.add(item4);
        TypeOfJob item5 = new TypeOfJob();
        item5.setJobType("促销");
        item5.setImageType(R.drawable.gridview_5);
        typeList.add(item5);
        TypeOfJob item6 = new TypeOfJob();
        item6.setJobType("托管");
        item6.setImageType(R.drawable.gridview_6);
        typeList.add(item6);

    }


}
