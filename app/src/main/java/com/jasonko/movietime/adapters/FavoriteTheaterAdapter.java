package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.AppParams;
import com.jasonko.movietime.TheaterActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.WebTheaterActivity;
import com.jasonko.movietime.dao.FavoriteTheater;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/21/15.
 */
public class FavoriteTheaterAdapter extends RecyclerView.Adapter<FavoriteTheaterAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private ArrayList<FavoriteTheater> mData;
    private Activity mActivity;

    //RecyclerView不能直接製作onItemClickListener
    //只好自己來
    //2015.9.9 其實是可以的，請看最下面
    //對每一個sub view都做一個listener
    //說起來有點浪費資源，但是方便
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener (OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }



    public FavoriteTheaterAdapter(Activity activity, ArrayList<FavoriteTheater> data){
        layoutInflater = LayoutInflater.from(activity);
        mData = data;
        mActivity = activity;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View arg0){
            super(arg0);
            mView = arg0;
        }

        View mView;
        TextView tv_theater_name;
        TextView tv_theater_address;
        TextView tv_theater_phone;
        ImageView imageViewMap;
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
        viewHolder.imageViewMap = (ImageView) view.findViewById(R.id.image_map);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i){

        final FavoriteTheater mTheater = mData.get(i);
        viewHolder.tv_theater_name.setText(mTheater.getName());
        viewHolder.tv_theater_address.setText(mTheater.getAddress());
        viewHolder.tv_theater_phone.setText(mTheater.getPhone());


        //一個有點麻煩的onClick
        if(mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(viewHolder.itemView, i);
                }
            });
        }


        //kerli的onCLick方法
        //如果點擊了最近瀏覽的影院，就跳到MovieListOfTheaterInAreaActivity，用這個activity來幫忙展示資料
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mTheater.getTheater_id()!=12 && mTheater.getTheater_id()!=25
                        && mTheater.getTheater_id() != 6 && mTheater.getTheater_id()!=33) {
                    Intent intent = new Intent(v.getContext(), TheaterActivity.class);
                    intent.putExtra("theater_name", mTheater.getName());
                    intent.putExtra("theater_phone", mTheater.getPhone());
                    intent.putExtra("theater_address", mTheater.getAddress());
                    intent.putExtra("theater_id", mTheater.getTheater_id());
                    v.getContext().startActivity(intent);
                }else {
                    Intent intent = new Intent(v.getContext(), WebTheaterActivity.class);
                    switch (mTheater.getTheater_id()){
                        case 6:
                            intent.putExtra("theater_link", "http://www.westin-taipei.com/01_text.asp?sn=40");
                            intent.putExtra("theater_id", mTheater.getTheater_id());
                            intent.putExtra("theater_title", mTheater.getName());
                            intent.putExtra("theater_address", mTheater.getAddress());
                            break;
                        case 12:
                            intent.putExtra("theater_link", "http://www.spot-hs.org.tw/schedule/schedule.html");
                            intent.putExtra("theater_id", mTheater.getTheater_id());
                            intent.putExtra("theater_title", mTheater.getName());
                            intent.putExtra("theater_address", mTheater.getAddress());
                            break;
                        case 25:
                            intent.putExtra("theater_link", "http://www.spot.org.tw/schedule/schedule_one.html");
                            intent.putExtra("theater_id", mTheater.getTheater_id());
                            intent.putExtra("theater_title", mTheater.getName());
                            intent.putExtra("theater_address", mTheater.getAddress());
                            break;
                        case 33:
                            intent.putExtra("theater_link", "http://web.fuzhong15.ntpc.gov.tw/films/schedule.aspx");
                            intent.putExtra("theater_id", mTheater.getTheater_id());
                            intent.putExtra("theater_title", mTheater.getName());
                            intent.putExtra("theater_address", mTheater.getAddress());
                            break;
                    }
                    v.getContext().startActivity(intent);
                }

            }
        });

        viewHolder.imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + mTheater.getAddress()));
                mActivity.startActivity(intent);
            }
        });

    }


}
