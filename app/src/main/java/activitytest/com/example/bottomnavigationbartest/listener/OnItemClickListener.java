package activitytest.com.example.bottomnavigationbartest.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pc on 2017/8/6.
 */

public interface OnItemClickListener {
    void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
}
