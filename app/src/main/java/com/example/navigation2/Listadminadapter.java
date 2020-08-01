package com.example.navigation2;



import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation2.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Listadminadapter extends RecyclerView.Adapter<Listadminadapter.ViewHolder> implements Filterable {
    public List<Listitem> listitems;
    private List<Listitem> listitemsall;
    private Context context;
    public Listadminadapter(List<Listitem> listitems,Context context) {
        this.listitems=listitems;
        this.listitemsall=new ArrayList<>(listitems);
        this.context=context;
    }

    @NonNull
    @Override
    public Listadminadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        final Listadminadapter.ViewHolder viewHolder=new ViewHolder(v);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Listadminadapter.ViewHolder holder, final int position) {

        Listitem listitem=listitems.get(position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(),DeletActivity.class);
                intent.putExtra("link",listitems.get(position).getLink1());
                intent.putExtra("date",listitems.get(position).getDate2());
                intent.putExtra("department",listitems.get(position).getDepartment1());
                intent.putExtra("Tittle",listitems.get(position).getTittle());
                intent.putExtra("circularno",listitems.get(position).getCircular1());
                context.startActivity(intent);

            }
        });
        holder.title.setText(listitem.getTittle());
        holder.date4.setText(listitem.getDate2());
        holder.circularno.setText(listitem.getCircular1());
        holder.department.setText(listitem.getDepartment1());
        holder.link.setText(listitem.getLink1());

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public void filteredList(ArrayList<Listitem> filterlist) {
        listitems=filterlist;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Listitem> filteredlist=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredlist.addAll(listitemsall);
            }
            else {
                for(Listitem item:listitemsall){
                    if(item.getTittle().toLowerCase().contains(constraint.toString().toLowerCase())
                            || item.getCircular1().toLowerCase().contains(constraint.toString().toLowerCase())
                    ){
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listitems.clear();
            listitems.addAll((Collection<? extends Listitem>) results.values);
            notifyDataSetChanged();

        }
    };

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title,date4,circularno,department,link;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView){
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearlayoutlist);
            title=(TextView)itemView.findViewById(R.id.tittle);
            date4=(TextView)itemView.findViewById(R.id.date4);
            circularno=(TextView)itemView.findViewById(R.id.circularno);
            department=(TextView)itemView.findViewById(R.id.department);
            link=(TextView)itemView.findViewById(R.id.circularlink);




        }
    }
}

