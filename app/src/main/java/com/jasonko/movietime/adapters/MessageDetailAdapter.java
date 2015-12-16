package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.WebViewArticleActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.api.MessageAPI;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FavoriteMessage;
import com.jasonko.movietime.dao.FavoriteMessageDao;
import com.jasonko.movietime.dao.FavoriteReply;
import com.jasonko.movietime.dao.FavoriteReplyDao;
import com.jasonko.movietime.model.Message;
import com.jasonko.movietime.model.Reply;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolichung on 10/17/15.
 */
public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolder> {

    public Message mMessage;
    public ArrayList<Reply> replies;
    private DaoMaster.DevOpenHelper helper;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView reply_image;
        TextView reply_author;
        TextView reply_publish_date;
        ImageView reply_like_image;
        TextView reply_like_count;
        TextView reply_content;
        CardView messageDetailCardView;
        CardView replyCardView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            reply_image = (ImageView) mView.findViewById(R.id.reply_image);
            reply_author = (TextView) mView.findViewById(R.id.text_reply_userId);
            reply_content = (TextView) mView.findViewById(R.id.text_reply_content);
            reply_publish_date = (TextView) mView.findViewById(R.id.text_reply_publish_date);
            reply_like_image = (ImageView) mView.findViewById(R.id.reply_like_image);
            reply_like_count = (TextView) mView.findViewById(R.id.reply_like_count);
            messageDetailCardView = (CardView) mView.findViewById(R.id.message_detail_card_view);
            replyCardView = (CardView) mView.findViewById(R.id.reply_card_view);
        }

    }

    public MessageDetailAdapter(Activity activity, Message message, ArrayList<Reply> replies) {
        this.mMessage = message;
        this.replies = replies;
        mActivity = activity;
        helper = new DaoMaster.DevOpenHelper(mActivity, "expense", null);
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
            ImageView headImage = (ImageView) holder.messageDetailCardView.findViewById(R.id.comment_image);
            TextView author = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_userId);
            TextView publish_date = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_publish_date);
            final ImageView likeImage = (ImageView) holder.messageDetailCardView.findViewById(R.id.message_like_image);
            final TextView likeCount = (TextView) holder.messageDetailCardView.findViewById(R.id.message_like_count);
            TextView title = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_title);
            TextView content = (TextView) holder.messageDetailCardView.findViewById(R.id.text_message_tontent);
            CardView webButton = (CardView) holder.messageDetailCardView.findViewById(R.id.webbutton_cardview);

            if(mMessage.getLink_url().equals("")){
                webButton.setVisibility(View.GONE);
            }else{
                webButton.setVisibility(View.VISIBLE);
                webButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(mActivity, WebViewArticleActivity.class);
                        newIntent.putExtra("news_link", mMessage.getLink_url());
                        newIntent.putExtra("news_title", mMessage.getTitle());
                        mActivity.startActivity(newIntent);
                    }
                });
            }

            author.setText(mMessage.getAuthor());
            title.setText(mMessage.getTitle());
            publish_date.setText(mMessage.getPub_date());
            content.setText(mMessage.getContent());
            likeCount.setText(Integer.toString(mMessage.getLike_count()));
            switch (mMessage.getHead_index()){
                case 1:
                    headImage.setImageResource(R.drawable.head_captain);
                    break;
                case 2:
                    headImage.setImageResource(R.drawable.head_iron_man);
                    break;
                case 3:
                    headImage.setImageResource(R.drawable.head_black_widow);
                    break;
                case 4:
                    headImage.setImageResource(R.drawable.head_thor);
                    break;
                case 5:
                    headImage.setImageResource(R.drawable.head_hulk);
                    break;
                case 6:
                    headImage.setImageResource(R.drawable.head_hawkeye);
                    break;
            }

            likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!checkLike(mMessage)){
                        likeImage.setImageResource(R.drawable.like_blue);
                        addLikeMessage(mMessage);
                        likeCount.setText(Integer.toString(mMessage.getLike_count() + 1));
                        //get like + 1 to server
                        new AddTask().execute(mMessage.getMessage_id());
                    }
                }
            });
            if (checkLike(mMessage)){
                likeImage.setImageResource(R.drawable.like_blue);
            }else{
                likeImage.setImageResource(R.drawable.like_gray);
            }
        }else {
            holder.messageDetailCardView.setVisibility(View.GONE);
        }

        if (replies.size() == 0){
            holder.replyCardView.setVisibility(View.GONE);
        }else {
            final Reply theReply = replies.get(position);
            holder.replyCardView.setVisibility(View.VISIBLE);
            holder.reply_author.setText(theReply.getAuthor());
            holder.reply_publish_date.setText(theReply.getPub_date());
            holder.reply_content.setText(theReply.getContent());
            final TextView replyCount = holder.reply_like_count;
            replyCount.setText(Integer.toString(theReply.getLike_count()));
            switch (theReply.getHead_index()){
                case 1:
                    holder.reply_image.setImageResource(R.drawable.head_captain);
                    break;
                case 2:
                    holder.reply_image.setImageResource(R.drawable.head_iron_man);
                    break;
                case 3:
                    holder.reply_image.setImageResource(R.drawable.head_black_widow);
                    break;
                case 4:
                    holder.reply_image.setImageResource(R.drawable.head_thor);
                    break;
                case 5:
                    holder.reply_image.setImageResource(R.drawable.head_hulk);
                    break;
                case 6:
                    holder.reply_image.setImageResource(R.drawable.head_hawkeye);
                    break;
            }
            final ImageView replyLikeImage = holder.reply_like_image;
            holder.reply_like_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkReplyLike(theReply)) {
                        replyLikeImage.setImageResource(R.drawable.like_blue);
                        addLikeReply(theReply);
                        replyCount.setText(Integer.toString(theReply.getLike_count() + 1));
                        //get like + 1 to server
                        new AddReplyTask().execute(theReply.getReply_id());
                    }
                }
            });
            if (checkReplyLike(theReply)){
                replyLikeImage.setImageResource(R.drawable.like_blue);
            }else{
                replyLikeImage.setImageResource(R.drawable.like_gray);
            }

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (replies.size() == 0){
            return 1;
        }else {
            return replies.size();
        }
    }

    private boolean checkLike(Message message) {
        if (helper!=null){
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            FavoriteMessageDao likeMessageDao = daoSession.getFavoriteMessageDao();
            List favoriteMessageList = likeMessageDao.queryBuilder().where(FavoriteMessageDao.Properties.Message_id.eq(message.getMessage_id())).list();
            if (favoriteMessageList.size() > 0){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    private boolean checkReplyLike(Reply theReply) {
        if (helper!=null){
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            FavoriteReplyDao likeReplyDao = daoSession.getFavoriteReplyDao();
            List favoriteReplyList = likeReplyDao.queryBuilder().where(FavoriteReplyDao.Properties.Reply_id.eq(theReply.getReply_id())).list();
            if (favoriteReplyList.size() > 0){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    private void addLikeReply(Reply reply) {
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FavoriteReplyDao likeReplyDao = daoSession.getFavoriteReplyDao();

        FavoriteReply newFavReply = new FavoriteReply(null,reply.getReply_id(),reply.getMessage_id(),reply.getAuthor(),reply.getContent(),reply.getPub_date(),reply.getHead_index(),reply.getLike_count());
        likeReplyDao.insert(newFavReply);
    }

    private void addLikeMessage(Message message) {
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FavoriteMessageDao likeMessageDao = daoSession.getFavoriteMessageDao();

        FavoriteMessage newFavMessage = new FavoriteMessage(null,message.getMessage_id(),message.getAuthor(),message.getTitle(),
                message.getTag(),message.getContent(),message.getPub_date(),message.getView_count(),message.getLike_count(),
                message.getReply_size(),message.getHead_index(),message.is_head(),message.getLink_url());
        likeMessageDao.insert(newFavMessage);
    }

    private class AddTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            Integer message_id = params[0];
            MessageAPI.getMessageLikePlusOne(message_id);
            return null;
        }
    }

    private class AddReplyTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            Integer reply_id = params[0];
            MessageAPI.getReplyLikePlusOne(reply_id);
            return null;
        }
    }
}

