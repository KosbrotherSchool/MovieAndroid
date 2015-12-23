package com.jasonko.movietime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.AreaGridAdapter;
import com.jasonko.movietime.model.Area;

import java.util.ArrayList;


/**
 * Created by larry on 2015/8/26.
 */
public class AreaFragment extends Fragment {

    ArrayList<Area> areas;
    private AreaGridAdapter adapter;
    private GridView gridView;

    public static AreaFragment newInstance() {
        AreaFragment fragment = new AreaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View areaView = inflater.inflate(R.layout.tab_area, container, false);
        gridView = (GridView) areaView.findViewById(R.id.gridViewArea);
        //取得areas物件arraylist
        areas = Area.getAreas();
        adapter = new AreaGridAdapter(getActivity(), areas);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);
        return areaView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

}
