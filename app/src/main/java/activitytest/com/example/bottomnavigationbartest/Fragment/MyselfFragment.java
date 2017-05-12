package activitytest.com.example.bottomnavigationbartest.Fragment;

/**
 * Created by pc on 2017/4/27.
 */

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import activitytest.com.example.bottomnavigationbartest.MyNestScrollView;
import activitytest.com.example.bottomnavigationbartest.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pc on 2016/11/15.
 */
public class MyselfFragment extends Fragment {

    //    public static MyselfFragment newInstance(String param1) {
//       MyselfFragment fragment = new MyselfFragment();
//        Bundle args = new Bundle();
//        args.putString("agrs1", param1);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @BindView(R.id.myself_personal_CImage)
    CircleImageView mImageView;
    @BindView(R.id.myself_first_textView)
    TextView mFirstText;
    @BindView(R.id.myself_second_textView)
    TextView mSecondText;

    public MyselfFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myself, container, false);
//        Bundle bundle = getArguments();
//        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView)view.findViewById(R.id.tv_books);
////        tv.setText(agrs1);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.myself_toolbar);
//        toolbar.setTitle("个人中心");
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }
    private void initView(View view){
        AppBarLayout appBarLayout =(AppBarLayout) getActivity().findViewById(R.id.layout_appbar);
        LinearLayout myselfLinearLayout = (LinearLayout) view.findViewById(R.id.myself_linear_layout);
        MyNestScrollView nestScrollView =(MyNestScrollView)view.findViewById(R.id.nested_scroll_view);

//        myselfLinearLayout.removeView(mCollapsingToolbar);
//        Toolbar homeToolbar =(Toolbar)getActivity().findViewById(R.id.main_toolbar);
//        homeToolbar.setVisibility(View.GONE);

//        appBarLayout.removeView(homeToolbar);
//        homeToolbar.setTitle("个人中心");

        CoordinatorLayout.LayoutParams mLayoutParams = (CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
//        mLayoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
//        mLayoutParams.height= 250;
//        appBarLayout.setLayoutParams(mLayoutParams);
        // appBarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));

//        appBarLayout.addView(mCollapsingToolbar);
        Glide.with(this).load(R.mipmap.ic_launcher).into(mImageView);
//        AppBarLayout.LayoutParams Params = (AppBarLayout.LayoutParams)appBarLayout.getChildAt(1).getLayoutParams();
//        Params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);

        mFirstText.setText("个人资料");
        mSecondText.setText("我的简历");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
//        AppBarLayout appBarLayout =(AppBarLayout) getActivity().findViewById(R.id.layout_appbar);
//        Toolbar homeToolbar =(Toolbar)getActivity().findViewById(R.id.main_toolbar);
//        CoordinatorLayout.LayoutParams mLayoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        appBarLayout.setLayoutParams(mLayoutParams);
//        appBarLayout.removeView(mCollapsingToolbar);
//        homeToolbar.setVisibility(View.VISIBLE);
//        appBarLayout.setBackgroundColor(getResources().getColor(R.color.blue));
    }

}