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
import activitytest.com.example.bottomnavigationbartest.db.Msg;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;

/**
 * Created by pc on 2017/8/8.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Msg> mItems = new ArrayList<>();

    private OnItemClickListener mClickListener;

    public MsgAdapter(Context context){
        mContext =context;
        mInflater = LayoutInflater.from(context);
    }

    public void addMsg(Msg bean){
        mItems.add(bean);
        notifyItemInserted(mItems.size()-1);//在size()-1出添加数据
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMsg;
        private ImageView imgAvatar;
        public ViewHolder(View view){
            super(view);
            tvMsg = (TextView)view.findViewById(R.id.tv_msg);
            imgAvatar = (ImageView)view.findViewById(R.id.img_avatar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
       View view =  mInflater.inflate(R.layout.item_msg,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null)
                    mClickListener.onItemClick(holder.getAdapterPosition(),v,holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Msg item = mItems.get(position);

        holder.tvMsg.setText(item.getMsg());
    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

}
