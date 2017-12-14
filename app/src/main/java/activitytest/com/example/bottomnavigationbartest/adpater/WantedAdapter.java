package activitytest.com.example.bottomnavigationbartest.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.db.Chat;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;

/**
 * Created by pc on 2017/11/8.
 */

public class WantedAdapter extends RecyclerView.Adapter<WantedAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Job> mItems = new ArrayList<>();


    private OnItemClickListener mMsgClickListener;

    public WantedAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Job> beans) {
        mItems.clear();
        mItems.addAll(beans);
        notifyDataSetChanged();//动态刷新列表
    }

    public void refreshMsg(Chat bean) {
        int index = mItems.indexOf(bean);
        if (index < 0) return;

        notifyItemChanged(index);//更新index处的数据
    }
     static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName, tvMsg, tvTime;

        public ViewHolder(View itemView){
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//将布局加载进来，然后创建一个ViewHolder实例，并把加载出来的布局传入到构造函数当中，最后返回ViewHolder实例
        View view = mInflater.inflate(R.layout.item_wanted, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMsgClickListener != null) {
                    mMsgClickListener.onItemClick(holder.getAdapterPosition(), v, holder);
                }
            }
        });
        return holder;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//用用于对RecyclerView子项的数据进行赋值
        Job item = mItems.get(position);

        holder.tvName.setText(item.getWorkName());

        holder.tvTime.setText(item.getPublishTime());
        holder.tvMsg.setText("查看报名信息");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnMsgClickListener(OnItemClickListener listener) {
        mMsgClickListener = listener;
    }

    public Job getMsg(int position) {
        return mItems.get(position);
    }


}
