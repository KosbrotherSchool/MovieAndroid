package com.jasonko.movietime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by larry on 2015/8/30.
 * 實作TheatersOfArea中的RecyclerView內容
 */
public class TheatersListAdapter extends RecyclerView.Adapter<TheatersListAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private ArrayList<Theater> mData;



    //RecyclerView不能直接製作onItemClickListener
    //只好自己來
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener (OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public TheatersListAdapter(Context context, ArrayList<Theater> data){
        layoutInflater = LayoutInflater.from(context);
        mData = data;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View arg0){
            super(arg0);
        }

        TextView tv_theater_name;
        TextView tv_theater_address;
        TextView tv_theater_phone;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    //創建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = layoutInflater.inflate(R.layout.theater_in_area_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.tv_theater_name = (TextView) view.findViewById(R.id.tv_theater_name);
        viewHolder.tv_theater_address = (TextView) view.findViewById(R.id.tv_theater_address);
        viewHolder.tv_theater_phone = (TextView) view.findViewById(R.id.tv_theater_phone);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i){

        Theater mTheater = mData.get(i);
        viewHolder.tv_theater_name.setText(mTheater.getName());
        viewHolder.tv_theater_address.setText(mTheater.getAddress());
        viewHolder.tv_theater_phone.setText(mTheater.getPhone());


        if(mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(viewHolder.itemView, i);
                }
            });
        }

    }
}
