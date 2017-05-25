package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

/**
 * Created by pc on 2017/4/27.
 */

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseMainFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pc on 2016/11/15.
 */
public class FoursTabFragment extends BaseMainFragment {
    @BindView(R.id.myself_personal_CImage)
    CircleImageView mImageView;
    @BindView(R.id.myself_first_textView)
    TextView mFirstText;
    @BindView(R.id.myself_second_textView)
    TextView mSecondText;

    Toolbar mToolbar;

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
        mToolbar =(Toolbar) view.findViewById(R.id.toolbar);

        Glide.with(this).load(R.mipmap.ic_launcher).into(mImageView);

        mToolbar.setTitle("个人中心");

        mFirstText.setText("个人资料");
        mSecondText.setText("我的简历");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();

    }

}