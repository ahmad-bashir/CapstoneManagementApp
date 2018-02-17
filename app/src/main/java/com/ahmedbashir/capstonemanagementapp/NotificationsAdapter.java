package com.ahmedbashir.capstonemanagementapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ahmed on 2/17/2018.
 */

public class NotificationsAdapter  extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>{
    private String[] data;

    public NotificationsAdapter(String[] data){
        this.data = data;
    }
    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notifications_list,parent,false);
        return new NotificationsAdapter.NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder holder, int position) {
        String notificationText = data[position];
        holder.setNotificationText(notificationText);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder{
        public TextView notificationText;


        public NotificationsViewHolder(View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.notification_text_view);

        }

        public void setNotificationText(String text){
            notificationText.setText(text);
        }


    }
}
