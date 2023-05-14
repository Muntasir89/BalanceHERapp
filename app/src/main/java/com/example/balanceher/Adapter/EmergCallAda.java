package com.example.balanceher.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balanceher.Data.EmergCallData;
import com.example.balanceher.EmergCallModel;
import com.example.balanceher.R;

import java.util.List;

public class EmergCallAda extends RecyclerView.Adapter<EmergCallAda.InfoViewHolder> {
    static Context context;
    List<EmergCallModel>EmergCallList;
    EmergCallData data = EmergCallData.getInstance();

    public EmergCallAda(Context cntx){
        context = cntx;
        EmergCallList = data.getEmergCallData();
    }
    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.emerg_card, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.profileImg.setImageResource(EmergCallList.get(position).getImgId());
        holder.nameTV.setText(EmergCallList.get(position).getName());
        holder.designationTV.setText(EmergCallList.get(position).getDesignation());
        holder.workPlaceTV.setText(EmergCallList.get(position).getWorkPlace());
        holder.contactTV.setText(EmergCallList.get(position).getContact());
    }

    @Override
    public int getItemCount() {
        return EmergCallList.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImg;
        TextView nameTV, designationTV, workPlaceTV, contactTV;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profileImg);
            nameTV = itemView.findViewById(R.id.nameTV);
            designationTV = itemView.findViewById(R.id.designationTV);
            workPlaceTV = itemView.findViewById(R.id.workPlaceTV);
            contactTV = itemView.findViewById(R.id.contactTV);
        }
    }
}
