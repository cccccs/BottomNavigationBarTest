package activitytest.com.example.bottomnavigationbartest.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pc on 2017/2/19.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,RecyclerView.State state){
        outRect.set(0,0,0,0);
    }
}
