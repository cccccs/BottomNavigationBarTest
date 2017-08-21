package activitytest.com.example.bottomnavigationbartest.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.db.Choice;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;

/**
 * Created by pc on 2017/8/19.
 */

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {
    private List<Choice> mItems = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView choiceImage;
        TextView choiceText;
        private ViewHolder(View view){
            super(view);
            choiceImage = (ImageView)view.findViewById(R.id.choice_image);
            choiceText = (TextView)view.findViewById(R.id.choice_text);
        }
    }

    public ChoiceAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Choice> beans) {
        mItems.clear();
        mItems.addAll(beans);
        notifyDataSetChanged();//动态刷新列表
    }

    public void refreshMsg(Choice bean) {
        int index = mItems.indexOf(bean);
        if (index < 0) return;

        notifyItemChanged(index);//更新index处的数据
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = mInflater.inflate(R.layout.choice_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null)
                    mClickListener.onItemClick(holder.getAdapterPosition(),v,holder);
                else
                    Log.d("MainActivity","BBBBB");
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Choice choice = mItems.get(position);
        holder.choiceImage.setImageResource(choice.getImageId());
        holder.choiceText.setText(choice.getChoice());
    }
    @Override
    public int getItemCount(){
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

}
