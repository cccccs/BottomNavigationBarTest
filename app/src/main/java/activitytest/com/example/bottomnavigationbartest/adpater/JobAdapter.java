package activitytest.com.example.bottomnavigationbartest.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.first.JobDetailFragment;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;

/**
 * Created by pc on 2017/2/16.
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    public List<Job> mJobList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView workImage;
        TextView workName;
        TextView publishTime;
        TextView workPlace;
        TextView workPay;
        TextView workTime;
        View jobView;

        public ViewHolder(View view){
            super(view);
            jobView = view;
            workImage = (ImageView) view.findViewById(R.id.work_image);
            workName = (TextView) view.findViewById(R.id.work_name);
            publishTime = (TextView) view.findViewById(R.id.publish_time);
            workPlace = (TextView) view.findViewById(R.id.work_place);
            workPay = (TextView) view.findViewById(R.id.work_pay);
            workTime = (TextView)view.findViewById(R.id.work_time);
        }
    }

    public JobAdapter(List<Job> jobList){
        mJobList = jobList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item,parent,false);
       final ViewHolder holder = new ViewHolder(view);
        holder.jobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position  = holder.getAdapterPosition();
                Job job = mJobList.get(position);
                EventBus.getDefault().post(new StartBrotherEvent(JobDetailFragment.newInstance()));

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Job job = mJobList.get(position);
        holder.workImage.setImageResource(job.getImageId());
        holder.workTime.setText(job.getWorkTime());
        holder.workPay.setText(job.getWorkPay());
        holder.workPlace.setText(job.getWorkPlace());
        holder.workName.setText(job.getWorkName());
        holder.publishTime.setText(job.getPublishTime());
    }
    @Override
    public int getItemCount(){
        return mJobList.size();
    }
}
