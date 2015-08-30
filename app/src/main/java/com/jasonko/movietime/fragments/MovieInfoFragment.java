package com.jasonko.movietime.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Movie;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieInfoFragment extends Fragment{

    public static final String ARG_MOVIE_ID = "MOVIE_ID";

    private int mMovieID;
    private Movie mMovie;

    private CardView photoCardView;
    private CardView trailerCardView;
    private TextView readmoreTextView;

    private TextView photoText;
    private TextView trailerText;
    private TextView infoText;
    private TextView typeText;
    private TextView diretorText;
    private TextView actorText;
    private TextView officerText;
    private TextView titleText;
    private TextView titleEngText;
    private TextView classText;
    private TextView publishDateText;
    private ImageView mImage;

    private ImageLoader mImageLoader;

    public static MovieInfoFragment newInstance(int movie_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movie_id);
        MovieInfoFragment fragment = new MovieInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieID = getArguments().getInt(ARG_MOVIE_ID);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);
        photoCardView = (CardView) view.findViewById(R.id.movie_photos_card_view);
        trailerCardView = (CardView) view.findViewById(R.id.movie_trailers_card_view);
        readmoreTextView = (TextView) view.findViewById(R.id.textview_movie_info_readmore);

        titleText = (TextView) view.findViewById(R.id.movieinfo_text_title);
        titleEngText = (TextView) view.findViewById(R.id.movieinfo_text_title_eng);
        classText = (TextView) view.findViewById(R.id.movieinfo_text_class);
        publishDateText = (TextView) view.findViewById(R.id.movieinfo_text_publish_date);
        mImage = (ImageView) view.findViewById(R.id.movieinfo_image);

        photoText = (TextView) view.findViewById(R.id.movieinfo_text_photos);
        trailerText = (TextView) view.findViewById(R.id.movieinfo_text_trailers);
        infoText = (TextView) view.findViewById(R.id.movieinfo_text_info);
        typeText = (TextView) view.findViewById(R.id.movieinfo_text_type);
        diretorText = (TextView) view.findViewById(R.id.movieinfo_text_director);
        actorText = (TextView) view.findViewById(R.id.movieinfo_text_actors);
        officerText = (TextView) view.findViewById(R.id.movieinfo_text_officier);

        mImageLoader = new ImageLoader(getActivity());
        readmoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readmoreTextView.getTag() == null ||  (int) readmoreTextView.getTag() == 0){
                    infoText.setMaxLines(Integer.MAX_VALUE);
                    infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                    readmoreTextView.setTag(1);
                    readmoreTextView.setText("收起簡介內容");
                }else{
                    infoText.setMaxLines(4);
                    infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                    readmoreTextView.setTag(0);
                    readmoreTextView.setText("閱讀完整內容");
                }
            }
        });

        new SingleMovieTask().execute();
        return view;
    }

    private class SingleMovieTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mMovie = MovieAPI.getSingleMovie(mMovieID);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            titleText.setText(mMovie.getTitle());
            titleEngText.setText(mMovie.getTitle_eng());
            classText.setText(mMovie.getMovie_class()+ " 片長：" + mMovie.getMovie_length());
            publishDateText.setText("上映日期：" + mMovie.getPublish_date());

            infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
            typeText.setText("類型:\n" + mMovie.getMovie_type());
            diretorText.setText("導演:\n" + mMovie.getDirector());
            actorText.setText("演員:\n" + mMovie.getActors());
            officerText.setText("出品公司:\n" +mMovie.getOfficial());

            mImageLoader.DisplayImage(mMovie.getSmall_pic(),mImage);
        }
    }
}
