package com.jasonko.movietime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.VideoGridAdapter;
import com.jasonko.movietime.model.MyYoutubeVideo;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class VideosGridFragment extends Fragment {
    public static final String ARG_VIDEOS = "VIDEOS_ID";

    private ArrayList<MyYoutubeVideo> mVideos;
    private GridView mGridView;
    private VideoGridAdapter videoGridAdapter;

    public static VideosGridFragment newInstance(ArrayList<MyYoutubeVideo> videos) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_VIDEOS, videos);
        VideosGridFragment fragment = new VideosGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideos = getArguments().getParcelableArrayList(ARG_VIDEOS);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        mGridView = (GridView) view.findViewById(R.id.fragment_gridview);
        videoGridAdapter = new VideoGridAdapter(getActivity(), mVideos);
        mGridView.setAdapter(videoGridAdapter);
        
        return view;
    }
    
}