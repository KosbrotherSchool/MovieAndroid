package com.jasonko.movietime.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.TheatersListAdapter;
import com.jasonko.movietime.model.MovieTime;

import java.util.ArrayList;

/**
 * Created by larry on 2015/9/2.
 */
public class MoviesListOfTheaterAdapter extends RecyclerView.Adapter<MoviesListOfTheaterAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<MovieTime> mData;




    public MoviesListOfTheaterAdapter(Context context, ArrayList<MovieTime> data){
        layoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View arg0){  super(arg0);  }

        public TextView tv_movie_title;
        public TextView tv_movie_remark;
        public TextView tv_movie_time;
        public ImageView iv_movie_cover;
    }




    @Override
    public int getItemCount(){
        return mData.size();
    }



    @Override
    public MoviesListOfTheaterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = layoutInflater.inflate(R.layout.item_movietime_in_movielist, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.iv_movie_cover = (ImageView)view.findViewById(R.id.iv_movie_cover);
        viewHolder.tv_movie_title = (TextView)view.findViewById(R.id.tv_movie_title);
        viewHolder.tv_movie_remark = (TextView) view.findViewById(R.id.tv_movie_remark);
        viewHolder.tv_movie_time = (TextView)view.findViewById(R.id.tv_movie_time);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i){
        MovieTime movieTime = mData.get(i);
        viewHolder.tv_movie_title.setText(movieTime.getMovie_title());
        viewHolder.tv_movie_remark.setText(movieTime.getRemark());
        viewHolder.tv_movie_time.setText(movieTime.getMovie_time());

    }


}
