package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.MessageDetailActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.FavoriteMessage;

import java.util.List;

/**
 * Created by kolichung on 12/16/15.
 */
public class FavoriteMessageAdapter extends RecyclerView.Adapter<FavoriteMessageAdapter.ViewHolder> {

    Activity mActivity;
    LayoutInflater mLayoutInflater;
    List<FavoriteMessage> mMessages;
    int board_id;
    private DaoMaster.DevOpenHelper helper;

    public FavoriteMessageAdapter(Activity activity, List<FavoriteMessage> messages, int board_id) {
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mMessages = messages;
        this.board_id = board_id;
        helper = new DaoMaster.DevOpenHelper(mActivity, "expense", null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
            mView = arg0;
        }

        TextView author;
        TextView title;
        TextView publish_date;
        TextView messageTag;
        View mView;
        ImageView likeImage;
        TextView likeCount;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public FavoriteMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_message, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.author = (TextView) view.findViewById(R.id.text_userId);
        viewHolder.title = (TextView) view.findViewById(R.id.text_message_title);
        viewHolder.publish_date = (TextView) view.findViewById(R.id.text_publish_date);
        viewHolder.messageTag = (TextView) view.findViewById(R.id.message_tag_text);
        viewHolder.likeImage = (ImageView) view.findViewById(R.id.like_image);
        viewHolder.likeCount = (TextView) view.findViewById(R.id.like_count);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final FavoriteMessage message = mMessages.get(i);
        viewHolder.author.setText(message.getAuthor());
        viewHolder.title.setText(message.getTitle());
        viewHolder.publish_date.setText(message.getPub_date());
        viewHolder.messageTag.setText("[" + message.getTag() + "]");
        viewHolder.likeCount.setText(Integer.toString(message.getLike_count()));
        viewHolder.likeImage.setImageResource(R.drawable.like_blue);


        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MessageDetailActivity.class);
                intent.putExtra("message id", message.getMessage_id());
                intent.putExtra("board_id", board_id);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FavMessage", message);
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
    }

}
