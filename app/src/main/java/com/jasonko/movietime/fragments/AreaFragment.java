package com.jasonko.movietime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.jasonko.movietime.R;
import com.jasonko.movietime.TheatersOfArea;
import com.jasonko.movietime.model.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by larry on 2015/8/26.
 */
public class AreaFragment extends Fragment {
    ArrayList<Area> areas;


    ArrayList<HashMap<String, String>> areaNameList = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter adapter;

    private GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View areaView = inflater.inflate(R.layout.tab_area, container, false);
        return areaView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart(){
        super.onStart();
        //set gridView

        if(gridView == null){
            gridView = (GridView)getActivity().findViewById(R.id.gridViewArea);
            //取得areas物件arraylist
            areas = Area.getAreas();

            for(int i = 0; i < areas.size(); i++){
                Area tempArea = areas.get(i);
                HashMap<String,String> item = new HashMap<String,String>();
                item.put("areaName", tempArea.getName());
                areaNameList.add(item);
            }
    


            adapter = new SimpleAdapter(getActivity(),areaNameList,R.layout.grid_item,
                    new String[]{"areaName"}, new int[]{R.id.textArea});
            gridView.setAdapter(adapter);
            gridView.setNumColumns(3);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), TheatersOfArea.class);
                intent.putExtra("area_id", areas.get(position).getArea_id());
                intent.putExtra("area_name", areas.get(position).getName());
                startActivity(intent);

            }

        });

    }
}
