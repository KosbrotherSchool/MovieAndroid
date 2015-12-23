package com.jasonko.movietime.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.CommentActivity;
import com.jasonko.movietime.MoviePhotosActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.TrailersActivity;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FollowMovie;
import com.jasonko.movietime.dao.FollowMovieDao;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieInfoFragment extends Fragment{

    public static final String ARG_MOVIE_ID = "MOVIE_ID";

    private int mMovieID;
    private Movie mMovie;

    private CardView photoCardView;
    private CardView trailerCardView;
    private CardView reviewCardView;
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
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;
    private TextView noNetWorkText;

    private ImageView follow_bottom_image;
//    private LinearLayout linearAddFollow;
//    private LinearLayout linearShare;
//    private LinearLayout linearReview;
    private RatingBar ratingBar;
    private TextView rateText;
//    private TextView reviewNumTExt;
    private TextView reviewText;
    private LinearLayout linearImdb;
    private LinearLayout linearPotato;
    private TextView imdbText;
    private TextView potatoText;
    private TextView textClass;

    private DaoMaster.DevOpenHelper helper;

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
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.movieinfo_layout);
        noNetWorkText = (TextView) view.findViewById(R.id.no_network_text);

        photoCardView = (CardView) view.findViewById(R.id.movie_photos_card_view);
        trailerCardView = (CardView) view.findViewById(R.id.movie_trailers_card_view);
        reviewCardView = (CardView) view.findViewById(R.id.movie_review_card_view);

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

        follow_bottom_image = (ImageView) view.findViewById(R.id.follow_movie_bottom_image);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        rateText = (TextView) view.findViewById(R.id.rate_text);
        reviewText = (TextView) view.findViewById(R.id.movieinfo_text_review);

        linearImdb = (LinearLayout) view.findViewById(R.id.imdb_linear);
        linearPotato = (LinearLayout) view.findViewById(R.id.potato_linear);
        imdbText = (TextView) view.findViewById(R.id.imdb_text);
        potatoText = (TextView) view.findViewById(R.id.potato_text);
        textClass = (TextView) view.findViewById(R.id.movie_thisweek_class_text);

        LayerDrawable stars = (LayerDrawable) ratingBar
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP); // for empty stars


        mImageLoader = new ImageLoader(getActivity(),200);
        readmoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readmoreTextView.getTag() == null || (int) readmoreTextView.getTag() == 0) {
                    infoText.setMaxLines(Integer.MAX_VALUE);
                    infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                    readmoreTextView.setTag(1);
                    readmoreTextView.setText("收起簡介內容");
                } else {
                    infoText.setMaxLines(4);
                    infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                    readmoreTextView.setTag(0);
                    readmoreTextView.setText("閱讀完整內容");
                }
            }
        });

        if (NetworkUtil.getConnectivityStatus(getActivity()) != 0 ){
            new SingleMovieTask().execute();
        }else {
            noNetWorkText.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }

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

            if (mMovie != null) {
                titleText.setText(mMovie.getTitle());
                if (mMovie.getTitle_eng() == null || mMovie.getTitle_eng().equals("")){
                    titleEngText.setVisibility(View.GONE);
                }else {
                    titleEngText.setText(mMovie.getTitle_eng());
                }
                classText.setText("片長：" + mMovie.getMovie_length());
                publishDateText.setText("上映日期：" + mMovie.getPublish_date());

                if (mMovie.getMovie_class().indexOf("限") != -1){
                    textClass.setBackgroundResource(R.drawable.class_red_selector);
                    textClass.setText("限");
                    textClass.setVisibility(View.VISIBLE);
                }else if(mMovie.getMovie_class().indexOf("保") != -1){
                    textClass.setBackgroundResource(R.drawable.class_blue_selector);
                    textClass.setText("保");
                    textClass.setVisibility(View.VISIBLE);
                }else if(mMovie.getMovie_class().indexOf("輔") != -1){
                    textClass.setBackgroundResource(R.drawable.class_yellow_selector);
                    textClass.setText("輔");
                    textClass.setVisibility(View.VISIBLE);
                }else if(mMovie.getMovie_class().indexOf("普") != -1){
                    textClass.setBackgroundResource(R.drawable.class_green_selector);
                    textClass.setText("普");
                    textClass.setVisibility(View.VISIBLE);
                }else {
                    textClass.setVisibility(View.GONE);
                }

                infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                typeText.setText(mMovie.getMovie_type());
                diretorText.setText(mMovie.getDirector());
                actorText.setText(mMovie.getActors());
                officerText.setText(mMovie.getOfficial());
                photoText.setText("圖集(" + Integer.toString(mMovie.getPhoto_size()+1) + ")");
                trailerText.setText("影片(" + Integer.toString(mMovie.getTrailer_size()) + ")");

                mImageLoader.DisplayImage(mMovie.getSmall_pic(), mImage);

                ratingBar.setRating((float) (mMovie.getPoints() / 2));
                rateText.setText(Double.toString(mMovie.getPoints()));
                reviewText.setText("留言(" + Integer.toString(mMovie.getReview_size()) + ")");

                mImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(getActivity(), MoviePhotosActivity.class);
                        newIntent.putExtra("photo_size", mMovie.getPhoto_size());
                        newIntent.putExtra("movie_id", mMovie.getMovie_id());
                        newIntent.putExtra("big_photo_url", mMovie.getLarge_pic());
                        getActivity().startActivity(newIntent);
                    }
                });

                photoCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(getActivity(), MoviePhotosActivity.class);
                        newIntent.putExtra("photo_size", mMovie.getPhoto_size());
                        newIntent.putExtra("movie_id", mMovie.getMovie_id());
                        newIntent.putExtra("big_photo_url", mMovie.getLarge_pic());
                        getActivity().startActivity(newIntent);
                    }
                });

                trailerCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(getActivity(), TrailersActivity.class);
                        newIntent.putExtra("movie_id", mMovie.getMovie_id());
                        newIntent.putExtra("movie_title", mMovie.getTitle());
                        getActivity().startActivity(newIntent);
                    }
                });

                reviewCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent reviewIntent = new Intent(getActivity(), CommentActivity.class);
                        reviewIntent.putExtra("movie_id", mMovieID);
                        reviewIntent.putExtra("title", mMovie.getTitle());
                        reviewIntent.putExtra("point_str", Double.toString(mMovie.getPoints()));
                        reviewIntent.putExtra("review_size_str", Integer.toString(mMovie.getReview_size()));
                        getActivity().startActivity(reviewIntent);
                    }
                });

                mProgressBar.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);


                follow_bottom_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkFollow()) {
                            deleteFollow();
                            follow_bottom_image.setImageResource(R.drawable.icon_love);
                            Toast.makeText(getActivity(), "取消我的最愛", Toast.LENGTH_SHORT).show();
                        } else {
                            addFollow();
                            follow_bottom_image.setImageResource(R.drawable.icon_love_full);
                            Toast.makeText(getActivity(), "加入我的最愛", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                if (!mMovie.getImdb_point().equals("0.0")){
                    imdbText.setText(mMovie.getImdb_point());
                }else {
                    imdbText.setText("未提供");
                }

                if (!mMovie.getPotato_point().equals("0.0")){
                    potatoText.setText(mMovie.getPotato_point()+"%");
                }else {
                    potatoText.setText("未提供");
                }

                linearImdb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mMovie.getImdb_link().equals("")){
                            String url = mMovie.getImdb_link();
                            Intent intentGood = new Intent(Intent.ACTION_VIEW);
                            intentGood.setData(Uri.parse(url));
                            startActivity(intentGood);
                        }else {
                            Toast.makeText(getActivity(), "IMDB尚無此電影", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                linearPotato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mMovie.getPotato_link().equals("")){
                            String url = mMovie.getPotato_link();
                            Intent intentGood = new Intent(Intent.ACTION_VIEW);
                            intentGood.setData(Uri.parse(url));
                            startActivity(intentGood);
                        }else {
                            Toast.makeText(getActivity(), "爛番茄尚無此電影", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                addThisMovieToRecentMovie();
                helper = new DaoMaster.DevOpenHelper(getActivity(), "expense", null);

                if (checkFollow()) {
                    follow_bottom_image.setImageResource(R.drawable.icon_love_full);
                }
            }else {
                noNetWorkText.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }


    private void addFollow() {

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao followMovieDao = daoSession.getFollowMovieDao();

        Date publish_date_date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            publish_date_date = sdf.parse(mMovie.getPublish_date());
        }catch (Exception e){

        }

        FollowMovie newFollowMovie = new FollowMovie(null, mMovie.getTitle(), mMovie.getMovie_class(), mMovie.getSmall_pic(), mMovie.getMovie_id(), publish_date_date, new Date());
        followMovieDao.insert(newFollowMovie);

    }

    private void deleteFollow() {

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao followMovieDao = daoSession.getFollowMovieDao();
        List followMovieList = followMovieDao.queryBuilder().where(FollowMovieDao.Properties.Movie_id.eq(mMovie.getMovie_id())).list();
        followMovieDao.delete((FollowMovie)followMovieList.get(0));

    }

    private boolean checkFollow() {

        if (helper!=null){
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            FollowMovieDao followMovieDao = daoSession.getFollowMovieDao();
            List followMovieList = followMovieDao.queryBuilder().where(FollowMovieDao.Properties.Movie_id.eq(mMovie.getMovie_id())).list();
            if (followMovieList.size() > 0){
                return true;
            }else {
                return false;
            }
        }
        return false;

    }

//    private void addThisMovieToRecentMovie() {
//
//        helper = new DaoMaster.DevOpenHelper(getActivity(), "expense", null);
//        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//
//        RecentMovieDao movieDao = daoSession.getRecentMovieDao();
//        List recentMovies = movieDao.queryBuilder()
//                .where(RecentMovieDao.Properties.Movie_id.eq(mMovie.getMovie_id()))
//                .list();
//        if (recentMovies.size() == 0) {
//            RecentMovie newRencentMovie = new RecentMovie(null, mMovie.getTitle(), mMovie.getMovie_class(),
//                    mMovie.getMovie_type(), mMovie.getActors(), mMovie.getPublish_date(), mMovie.getSmall_pic(), mMovie.getMovie_id(), new Date());
//            movieDao.insert(newRencentMovie);
//        }else {
//            RecentMovie theMovie = (RecentMovie)recentMovies.get(0);
//            theMovie.setUpdate_date(new Date());
//            movieDao.update(theMovie);
//        }
//
//        List allRecentMovies = movieDao.queryBuilder().orderAsc(RecentMovieDao.Properties.Update_date).list();
//        if (allRecentMovies.size()> 10){
//            movieDao.delete((RecentMovie)allRecentMovies.get(0));
//        }
//
//    }

}
