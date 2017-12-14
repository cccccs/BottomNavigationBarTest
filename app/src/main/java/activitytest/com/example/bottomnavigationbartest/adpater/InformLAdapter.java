package activitytest.com.example.bottomnavigationbartest.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.db.InformL;

/**
 * Created by pc on 2017/12/14.
 */
public class InformLAdapter extends RecyclerView.Adapter<InformLAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<InformL> items;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime;
        private TextView tvJob;


        public ViewHolder(View view){
            super(view);
            tvTime =(TextView)view.findViewById(R.id.tv_time);
            tvJob = (TextView)view.findViewById(R.id.tv_job);

        }
    }

    public InformLAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<InformL> mItems){
        items = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_informl,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.tvJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                InformL inform = items.get(position);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        InformL item = items.get(position);


        holder.tvJob.setText(item.getJob());
        holder.tvTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        try{
            return items.size();

        }catch (Exception e){
            return 0;
        }

    }



}
