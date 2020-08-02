package com.example.navigation2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewbyAdapter extends RecyclerView.Adapter<ViewbyAdapter.ViewHolder>{
    private List<SeenbyData> listitems;
    private Context context;

    public ViewbyAdapter(List<SeenbyData> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.seenby,parent,false);
         ViewbyAdapter.ViewHolder viewHolder=new ViewbyAdapter.ViewHolder(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeenbyData listitem=listitems.get(position);
        holder.viewbyname.setText(listitem.getName());
        holder.viewbyemail.setText(listitem.getEmail());

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView viewbyname,viewbyemail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearLayoutseenby);
            viewbyname=(TextView)itemView.findViewById(R.id.viewby_name);
            viewbyemail=(TextView)itemView.findViewById(R.id.viewby_email);
        }
    }
}
