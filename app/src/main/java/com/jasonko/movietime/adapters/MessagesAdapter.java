package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.MessageDetailActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.model.Message;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/14/15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    Activity mActivity;
    LayoutInflater mLayoutInflater;
    ArrayList<Message> mMessages;

    public MessagesAdapter(Activity activity, ArrayList<Message> messages) {
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mMessages = messages;
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
        TextView viewCount;
        TextView replyCount;
        View mView;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_message, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.author = (TextView) view.findViewById(R.id.text_userId);
        viewHolder.title = (TextView) view.findViewById(R.id.text_message_title);
        viewHolder.publish_date = (TextView) view.findViewById(R.id.text_publish_date);
        viewHolder.messageTag = (TextView) view.findViewById(R.id.message_tag_text);
        viewHolder.viewCount = (TextView) view.findViewById(R.id.text_message_viewcount);
        viewHolder.replyCount = (TextView) view.findViewById(R.id.text_message_replycount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Message message = mMessages.get(i);
        viewHolder.author.setText("作者:"+ message.getAuthor());
        viewHolder.title.setText(message.getTitle());
        viewHolder.publish_date.setText(message.getPub_date());
        viewHolder.messageTag.setText("["+message.getTag()+"]");
        viewHolder.viewCount.setText(Integer.toString(message.getLike_count())+"人喜歡");
        viewHolder.replyCount.setText(Integer.toString(message.getReply_size())+"人回覆");
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MessageDetailActivity.class);
                intent.putExtra("message id", message.getMessage_id());
                mActivity.startActivity(intent);
            }
        });
    }

}