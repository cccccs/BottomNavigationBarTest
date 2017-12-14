package activitytest.com.example.bottomnavigationbartest.adpater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MainActivity;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.db.Inform;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.first.JobDetailFragment;
/**
 * Created by pc on 2017/8/21.
 */

public class InformAdapter extends RecyclerView.Adapter<InformAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Inform> items;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime;
        private TextView tvJob;
        private TextView tvPhone;

        public ViewHolder(View view){
            super(view);
            tvTime =(TextView)view.findViewById(R.id.tv_time);
            tvJob = (TextView)view.findViewById(R.id.tv_job);
            tvPhone = (TextView)view.findViewById(R.id.tv_phone);
        }
    }

    public InformAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Inform> mItems){
        items = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_inform,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.tvJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Inform inform = items.get(position);
                EventBus.getDefault().post(new StartBrotherEvent(JobDetailFragment.newInstance(inform.getJob())));
            }
        });
        holder.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Intent.ACTION_DIAL);
                int position = holder.getAdapterPosition();
                Inform inform = items.get(position);
                intent.setData(Uri.parse("tel:"+inform.getPhone()));
                ((MainActivity)mContext).getApplication().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Inform item = items.get(position);

        holder.tvPhone.setText(item.getPhone());
        holder.tvJob.setText(item.getJob().getWorkName());
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
