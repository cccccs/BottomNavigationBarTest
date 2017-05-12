package activitytest.com.example.bottomnavigationbartest.db;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import activitytest.com.example.bottomnavigationbartest.R;

/**
 * Created by pc on 2017/4/17.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private List<TypeOfJob> mtypeOfJobList;

    static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView typeImage;
        TextView typeName;

        public ViewHolder(View view){
            super(view);
            typeImage = (ImageView) view.findViewById(R.id.type_image);
            typeName = (TextView) view.findViewById(R.id.type_text);
        }
    }

    public TypeAdapter(List<TypeOfJob> typeList){
        mtypeOfJobList = typeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TypeOfJob typeOfJob = mtypeOfJobList.get(position);
        holder.typeImage.setImageResource(typeOfJob.getImageType());
        holder.typeName.setText(typeOfJob.getTypeName());

    }

    @Override
    public int getItemCount(){
        return mtypeOfJobList.size();
    }

}
