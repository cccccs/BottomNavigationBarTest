package activitytest.com.example.bottomnavigationbartest;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by pc on 2017/3/5.
 */

public class MyNestScrollView extends NestedScrollView {
    private GestureDetector mGestureDetector;
    public MyNestScrollView(Context context) {
        this(context, null);
    }
    public MyNestScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context,new YNestScrollDetector());
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        return super.onInterceptTouchEvent(ev)&&mGestureDetector.onTouchEvent(ev);
    }
    class YNestScrollDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2 , float distanceX, float distanceY){
            return (Math.abs(distanceY)>Math.abs(distanceX));
        }
    }
}
