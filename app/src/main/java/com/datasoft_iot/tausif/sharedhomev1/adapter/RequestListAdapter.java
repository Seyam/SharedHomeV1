package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.RequestListModel;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<RequestListModel> list;

    public RequestListAdapter(Context context, ArrayList<RequestListModel> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(context).inflate(R.layout.single_received_view, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specific_request_list, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final RequestListModel receivedItemClass=list.get(i);

        viewHolder.name.setText(receivedItemClass.getName());
        viewHolder.mobile.setText(receivedItemClass.getMobile());
        viewHolder.lr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,tankClass.getTank_name(),Toast.LENGTH_SHORT).show();
                receivedItemClass.setSelected(!receivedItemClass.isSelected());
                viewHolder.itemView.setBackgroundColor(receivedItemClass.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, mobile;
        LinearLayout lr;

        public ViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.text_view_shared_user_name);
            mobile=itemView.findViewById(R.id.text_view_shared_user_mobile);
            lr=itemView.findViewById(R.id.lr);
        }
    }

}
