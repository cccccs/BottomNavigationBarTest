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
import activitytest.com.example.bottomnavigationbartest.db.TypeOfJob;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;

/**
 * Created by pc on 2017/4/17.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    private List<TypeOfJob> mItems = new ArrayList<>();

    static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView typeImage;
        TextView typeName;

        public ViewHolder(View view){
            super(view);
            typeImage = (ImageView) view.findViewById(R.id.type_image);
            typeName = (TextView) view.findViewById(R.id.type_text);
        }
    }

    public TypeAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }
    public void setDatas(List<TypeOfJob> items){
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();//动态刷新列表
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.type_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null)
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition(),v,holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TypeOfJob typeOfJob = mItems.get(position);
        holder.typeImage.setImageResource(typeOfJob.getImageType());
        holder.typeName.setText(typeOfJob.getTypeName());

    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }

    public void setOnItemCLickLister(OnItemClickListener onItemCLickLister){
        mOnItemClickListener = onItemCLickLister;
    }

    public TypeOfJob getType(int position) {
        return mItems.get(position);
    }
}
