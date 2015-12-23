package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.MessageDetailActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.api.MessageAPI;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FavoriteMessage;
import com.jasonko.movietime.dao.FavoriteMessageDao;
import com.jasonko.movietime.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolichung on 10/14/15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    Activity mActivity;
    LayoutInflater mLayoutInflater;
    ArrayList<Message> mMessages;
    int board_id;
    private DaoMaster.DevOpenHelper helper;

    public MessagesAdapter(Activity activity, ArrayList<Message> messages, int board_id) {
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

    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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
        final Message message = mMessages.get(i);
        viewHolder.author.setText(message.getAuthor());
        viewHolder.title.setText(message.getTitle());
        viewHolder.publish_date.setText(message.getPub_date());
        viewHolder.messageTag.setText("[" + message.getTag() + "]");


        final FavoriteMessage theFavMessage = checkLike(message);
        // check if liked
        viewHolder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theFavMessage == null) {
                    viewHolder.likeImage.setImageResource(R.drawable.like_blue);
                    addLikeMessage(message);
                    viewHolder.likeCount.setText(Integer.toString(message.getLike_count() + 1));
                    //get like + 1 to server
                    new AddTask().execute(message.getMessage_id());
                }
            }
        });
        if (theFavMessage != null){
            viewHolder.likeImage.setImageResource(R.drawable.like_blue);
            if (message.getLike_count() < theFavMessage.getLike_count()) {
                viewHolder.likeCount.setText(Integer.toString(theFavMessage.getLike_count()));
            }else{
                viewHolder.likeCount.setText(Integer.toString(message.getLike_count()));
            }
        }else{
            viewHolder.likeImage.setImageResource(R.drawable.like_gray);
            viewHolder.likeCount.setText(Integer.toString(message.getLike_count()));
        }


        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MessageDetailActivity.class);
                intent.putExtra("message id", message.getMessage_id());
                intent.putExtra("board_id", board_id);
                Bundle bundle = new Bundle();
                bundle.putSerializable("TheMessage", message);
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
    }



    private FavoriteMessage checkLike(Message message) {
        if (helper!=null){
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            FavoriteMessageDao likeMessageDao = daoSession.getFavoriteMessageDao();
            List favoriteMessageList = likeMessageDao.queryBuilder().where(FavoriteMessageDao.Properties.Message_id.eq(message.getMessage_id())).list();
            if (favoriteMessageList.size() > 0){
                return (FavoriteMessage)favoriteMessageList.get(0);
            }else {
                return null;
            }
        }
        return null;
    }

    private void addLikeMessage(Message message) {
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FavoriteMessageDao likeMessageDao = daoSession.getFavoriteMessageDao();

        FavoriteMessage newFavMessage = new FavoriteMessage(null,message.getMessage_id(),message.getAuthor(),message.getTitle(),
                message.getTag(),message.getContent(),message.getPub_date(),message.getView_count(),message.getLike_count()+1,
                message.getReply_size(),message.getHead_index(),message.is_head(),message.getLink_url());
        likeMessageDao.insert(newFavMessage);

        // if like message > 250, delete first 50
        List favoriteMessageList = likeMessageDao.queryBuilder().orderAsc(FavoriteMessageDao.Properties.Id).list();
        if (favoriteMessageList.size()>250){
            for(int i=0;i<50;i++){
                likeMessageDao.delete((FavoriteMessage)favoriteMessageList.get(i));
            }
        }
    }

    private class AddTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            Integer message_id = params[0];
            MessageAPI.getMessageLikePlusOne(message_id);
            return null;
        }
    }


}
