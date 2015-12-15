package com.jasonko.movietime.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.model.Message;

/**
 * Created by kolichung on 10/17/15.
 */
public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolder> {

    public Message mMessage;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView reply_count;
        TextView reply_author;
        TextView reply_content;
        TextView reply_publish_date;
        CardView messageDetailCardView;
        CardView replyCardView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            reply_count = (TextView) mView.findViewById(R.id.text_reply_count);
            reply_author = (TextView) mView.findViewById(R.id.text__reply_userId);
            reply_content = (TextView) mView.findViewById(R.id.text_reply_content);
            reply_publish_date = (TextView) mView.findViewById(R.id.text_reply_publish_date);
            messageDetailCardView = (CardView) mView.findViewById(R.id.message_detail_card_view);
            replyCardView = (CardView) mView.findViewById(R.id.reply_card_view);
        }

    }

    public MessageDetailAdapter(Message message) {
        this.mMessage = message;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reply, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position == 0){
            holder.messageDetailCardView.setVisibility(View.VISIBLE);
            TextView author = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_userId);
            TextView title = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_title);
            TextView publish_date = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_publish_date);
            TextView messageTag = (TextView) holder.messageDetailCardView.findViewById(R.id.message_tag_text);
            TextView viewCount= (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_viewcount);
            TextView content = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_tontent);

            author.setText("作者:" + mMessage.getAuthor());
            title.setText(mMessage.getTitle());
            publish_date.setText(mMessage.getPub_date());
//            messageTag.setText("["+mMessage.getMessage_tag()+"]");
            viewCount.setText(Integer.toString(mMessage.getView_count()) + "人瀏覽");
            content.setText(mMessage.getContent());
        }else {
            holder.messageDetailCardView.setVisibility(View.GONE);
        }

//        if (mMessage.getReplies().size() == 0){
//            holder.replyCardView.setVisibility(View.GONE);
//        }else {
//            holder.replyCardView.setVisibility(View.VISIBLE);
//            holder.reply_count.setText("回覆" + Integer.toString(position+1));
//            holder.reply_author.setText(mMessage.getReplies().get(position).getAuthor());
//            holder.reply_publish_date.setText(mMessage.getReplies().get(position).getPub_date());
//            holder.reply_content.setText(mMessage.getReplies().get(position).getContent());
//        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        if (mMessage.getReplies().size() == 0){
//            return 1;
//        }else {
//            return mMessage.getReplies().size();
//        }
        return  0;
    }
}

