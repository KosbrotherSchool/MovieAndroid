package com.jasonko.movietime;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

/**
 * Created by larry on 2015/8/12.
 */

public class TheatersActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings));




    }

    private static final String[] mStrings = new String[] {
            "基隆","台北東區","台北西區","台北北區","台北南區","新北市","桃園","新竹","苗栗","台中","彰化",
            "南投","雲林","嘉義","台南","高雄","屏東","宜蘭","花蓮","台東","澎湖","金門"
    };

}
