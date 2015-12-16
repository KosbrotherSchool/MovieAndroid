package com.jasonko.movietime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.MessageDetailActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.WebViewArticleActivity;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Message;

/**
 * Created by kolichung on 12/16/15.
 */
public class MessageHighLightFragment extends Fragment {

//    public static final String ARG_PHOTO_URL = "PHOTO_URL";
//    private String photoUrl;
    private ImageLoader imageLoader;
    private Message message;

    public static MessageHighLightFragment newInstance(Message message) {
        Bundle args = new Bundle();
        args.putSerializable("TheMessage", message);
        MessageHighLightFragment fragment = new MessageHighLightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = (Message) getArguments().getSerializable("TheMessage");
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_high_light_message, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.photo_fragment_image);
        TextView titleText = (TextView) view.findViewById(R.id.message_fragment_title);

        titleText.setText(message.getTitle());
        imageLoader = new ImageLoader(getActivity(),400);
        imageLoader.DisplayImage(message.getPic_url(),imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getBoard_id() != -1) {
                    Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                    intent.putExtra("message id", message.getMessage_id());
                    intent.putExtra("board_id", message.getBoard_id());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TheMessage", message);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent newIntent = new Intent(getActivity(), WebViewArticleActivity.class);
                    newIntent.putExtra("news_link", message.getLink_url());
                    newIntent.putExtra("news_title", message.getTitle());
                    startActivity(newIntent);
                }
            }
        });

        return view;
    }

}
