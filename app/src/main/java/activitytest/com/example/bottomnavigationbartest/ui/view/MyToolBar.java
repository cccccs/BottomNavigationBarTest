package activitytest.com.example.bottomnavigationbartest.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.first.SearchFragment;

/**
 * Created by pc on 2017/2/27.
 */

public class MyToolBar extends Toolbar {
    private LayoutInflater mInflater;
    private SearchView mSearchView;
    private TextView mTextTitle;
    private ImageButton mRightButton;
    private ImageButton mLeftButton;
    private View mView;
    private Context context;
    private OnClickRefresh onClickrefresh;
    public MyToolBar (Context context){
        this(context,null);
        this.context =context;
    }
    public MyToolBar(Context context,AttributeSet attrs){
        this(context,attrs,0);
        this.context =context;
    }
    public MyToolBar(Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        this.context =context;
        initView();
        setContentInsetsRelative(10,10);
        if(attrs != null){
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(),attrs, R.styleable.MyToolBar,defStyleAttr,0);
            final Drawable rightIcon = a.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
            if(rightIcon != null){
                setRightButtonIcon(rightIcon);
            }

            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolBar_isShowSearchView,false);
            //如果要显示searchview的时候
            if(isShowSearchView){
                ShowSearchView();
                hideTitleView();
            }


            a.recycle();
        }
    }
    public interface OnClickRefresh{
        public void onClick();
    }

    private void initView(){
        if(mView == null ){
            //初始化
            mInflater = LayoutInflater.from(getContext());
            //添加布局文件
            mView = mInflater.inflate(R.layout.toolbar,null);
            //绑定控件

            mSearchView= (SearchView) mView.findViewById(R.id.toolbar_searchview);
            mTextTitle = (TextView)mView.findViewById(R.id.toolbar_title);
            mRightButton = (ImageButton)mView.findViewById(R.id.toolbar_rightbutton);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CLIP_HORIZONTAL);
            addView(mView,lp);

            mRightButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickrefresh = (OnClickRefresh)context;
                    onClickrefresh.onClick();
                }
            });
            mSearchView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new StartBrotherEvent(SearchFragment.newInstance()));
                }
            });
        }
    }

    @Override
    public void setTitle(int resId){
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title){
        super.setTitle(title);
        initView();
        if(mTextTitle != null){
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    public void hideTitleView(){
        if (mTextTitle != null){
            mTextTitle.setVisibility(GONE);
        }
    }

    public void showTitleView(){
        if(mTextTitle != null){
           mTextTitle.setVisibility(VISIBLE);
        }
    }

    public void ShowSearchView(){
        mSearchView= (SearchView) mView.findViewById(R.id.toolbar_searchview);
            mSearchView.setVisibility(VISIBLE);
            mRightButton.setVisibility(VISIBLE);
            mSearchView.setIconifiedByDefault(true);
            mSearchView.setSubmitButtonEnabled(true);

    }

    public void hideSearchView(){
        if(mSearchView!=null&&mRightButton!=null){
            mSearchView.setVisibility(GONE);
            mRightButton.setVisibility(GONE);

        }
    }

    public void setRightButtonIcon(Drawable icon){
        if(mRightButton != null){
            mRightButton.setImageDrawable(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setLeftButtonIcon(Drawable icon){
        if(mLeftButton != null){
            mLeftButton.setImageDrawable(icon);
            mLeftButton.setVisibility(VISIBLE);
        }
    }

    public void setmRightButtonOnClickLinster(OnClickListener listener){
        mRightButton.setOnClickListener(listener);
    }

    public void setmLeftButtonOnClickLinster(OnClickListener listener){
        mLeftButton.setOnClickListener(listener);
    }
}
