package com.example.mytaskapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProjectsAdaptor extends RecyclerView.Adapter<ProjectsAdaptor.ViewHolder> {

    Context context;
    List<ProjectModel> dataList;
    String id,token,km,veTypeId,c;
    SharedPreferences sharedPreferences;
    String name;

    public ProjectsAdaptor(Context context, List<ProjectModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_projects,viewGroup, false);
        return new ProjectsAdaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ProjectModel model=dataList.get(i);
        name=model.getName();
        viewHolder.tvName.setText(model.getName());
        viewHolder.tvType.setText(model.getDiscription());
        viewHolder.tvAddress.setText(model.getAddress());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvType,tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_project_name);
            tvType=itemView.findViewById(R.id.tv_discriptions);
            tvAddress=itemView.findViewById(R.id.tv_address);
            //tvCompletedday=itemView.findViewById(R.id.tv_completed_day);
        }
    }
}
