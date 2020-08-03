package com.example.navigation2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter  {
    private List<ChatSend> listitems;
    private Context context;
    public ChatAdapter(List<ChatSend> listitems, Context context) {
        this.context=context;
        this.listitems=listitems;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlayout,parent,false);


        return new UserViewHolder(v);}
        else {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatadminlayout,parent,false);


            return new AdminViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatSend chatSend=listitems.get(position);

        if(chatSend.getName().equals("User")){
            return 1;
        }
        else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ChatSend listitem=listitems.get(position);

        switch (holder.getItemViewType()){
            case 1:
                ( (UserViewHolder)holder).mail.setText(listitem.getMail());
                ( (UserViewHolder)holder).message.setText(listitem.getMsage());
                break;
            case 2:
                ((AdminViewHolder)holder).mail.setText(listitem.getMail());
                ((AdminViewHolder)holder).message.setText(listitem.getMsage());

        }




    }
    @Override
    public int getItemCount() {
        return listitems.size();
    }
    public  class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView mail,message;
        public LinearLayout linearLayout;

        public UserViewHolder(View itemView){
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearlayoutchat);
            mail=(TextView)itemView.findViewById(R.id.chatmail);
            message=(TextView)itemView.findViewById(R.id.msgchat);





        }
    }
    public  class AdminViewHolder extends RecyclerView.ViewHolder{
        public TextView mail,message;
        public LinearLayout linearLayout;

        public AdminViewHolder(View itemView){
            super(itemView);
            linearLayout=itemView.findViewById(R.id.chatadminlayout);
            mail=(TextView)itemView.findViewById(R.id.chatadminmail);
            message=(TextView)itemView.findViewById(R.id.msgadminchat);





        }
    }
}
