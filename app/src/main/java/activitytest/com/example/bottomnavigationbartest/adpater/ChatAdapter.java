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
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;

/**
 * Created by pc on 2017/8/6.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Chat> mItems = new ArrayList<>();

    private OnItemClickListener mClickListener;

    public ChatAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Chat> beans) {
        mItems.clear();
        mItems.addAll(beans);
        notifyDataSetChanged();//动态刷新列表
    }

    public void refreshMsg(Chat bean) {
        int index = mItems.indexOf(bean);
        if (index < 0) return;

        notifyItemChanged(index);//更新index处的数据
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//将布局加载进来，然后创建一个ViewHolder实例，并把加载出来的布局传入到构造函数当中，最后返回ViewHolder实例
        View view = mInflater.inflate(R.layout.item_chat, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(holder.getAdapterPosition(), v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//用用于对RecyclerView子项的数据进行赋值
        Chat item = mItems.get(position);

        holder.tvName.setText(item.name);
        holder.tvMsg.setText(item.message);
        holder.tvTime.setText("昨天");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public Chat getMsg(int position) {
        return mItems.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName, tvMsg, tvTime;

        public ViewHolder(View itemView){
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
