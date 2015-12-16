package com.jasonko.movietime.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidpagecontrol.PageControl;
import com.jasonko.movietime.MessageBoardActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.api.MessageAPI;
import com.jasonko.movietime.model.Message;

import java.util.ArrayList;

/**
 * Created by kolichung on 12/15/15.
 */
public class TabHallaFragment extends Fragment {

    ArrayList<Message> messages = new ArrayList<>();
    private ViewPager viewPager;
    private PageControl pageControl;

    public static TabOtherFragment newInstance() {
        TabOtherFragment fragment = new TabOtherFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_halla, container, false);
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText("哈拉區");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) root.findViewById(R.id.image_pager);
        pageControl = (PageControl) root.findViewById(R.id.page_control);

        CardView pubCardView = (CardView) root.findViewById(R.id.halla_public_card_view);
        pubCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageBoardActivity.class);
                intent.putExtra("board_id",0);
                startActivity(intent);
            }
        });

        CardView movieCardView = (CardView) root.findViewById(R.id.halla_movie_card_view);
        movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageBoardActivity.class);
                intent.putExtra("board_id",1);
                startActivity(intent);
            }
        });

        CardView dramaCardView = (CardView) root.findViewById(R.id.halla_drama_card_view);
        dramaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageBoardActivity.class);
                intent.putExtra("board_id",2);
                startActivity(intent);
            }
        });

        CardView lifeCardView = (CardView) root.findViewById(R.id.halla_life_card_view);
        lifeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageBoardActivity.class);
                intent.putExtra("board_id",3);
                startActivity(intent);
            }
        });

        CardView recentLikeCardView = (CardView) root.findViewById(R.id.halla_recent_card_view);
        recentLikeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageBoardActivity.class);
                intent.putExtra("board_id",4);
                startActivity(intent);
            }
        });

        new NewsTask().execute();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Fragment getItem(int position) {
            return MessageHighLightFragment.newInstance(messages.get(position));
        }
    }


    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            messages = MessageAPI.getHighLightMessages();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(adapter);
            pageControl.setViewPager(viewPager);
        }
    }
}
