package com.jasonko.movietime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;

/**
 * Created by kolichung on 9/3/15.
 */
public class PhotoFragment extends Fragment {

    public static final String ARG_PHOTO_URL = "PHOTO_URL";
    private String photoUrl;
    private ImageLoader imageLoader;

    public static PhotoFragment newInstance(String photo_url) {
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_URL, photo_url);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoUrl = getArguments().getString(ARG_PHOTO_URL);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.photo_fragment_image);

        imageLoader = new ImageLoader(getActivity(),200);
        imageLoader.DisplayImage(photoUrl,imageView);

        return view;
    }

}