package com.jasonko.movietime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by kolichung on 9/7/15.
 */
public class SettingActivity extends AppCompatActivity {

    private CheckBox theDayCheckBox;
    private CheckBox customDayCheckBox;
    private EditText customEditText;
    private SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSharedPreference = getSharedPreferences(null, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("我的設定");

        theDayCheckBox = (CheckBox) findViewById(R.id.setting_the_day_checkbox);
        customDayCheckBox = (CheckBox) findViewById(R.id.setting_custom_day_checkbox);
        customEditText = (EditText) findViewById(R.id.setting_custom_day_edittext);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckBoxs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveParams();
    }

    private void saveParams() {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        boolean theDayBoolean = theDayCheckBox.isChecked();
        editor.putBoolean("the_day", theDayBoolean);
        boolean customDayBoolean = customDayCheckBox.isChecked();
        editor.putBoolean("custom_day", customDayBoolean);
        int customDayInt = -1;
        try {
            customDayInt = Integer.parseInt(customEditText.getText().toString());
        }catch (Exception e){

        }
        if (customDayInt != -1) {
            editor.putInt("custom_day_int", customDayInt);
        }
        editor.commit();

    }

    private void setCheckBoxs() {
        boolean theDayBoolean = mSharedPreference.getBoolean("the_day", true);
        if (theDayBoolean){
            theDayCheckBox.setChecked(true);
        }else {
            theDayCheckBox.setChecked(false);
        }
        boolean customDayBoolean = mSharedPreference.getBoolean("custom_day", false);
        if (customDayBoolean){
            customDayCheckBox.setChecked(true);
        }else {
            customDayCheckBox.setChecked(false);
        }
        int customDayInt = mSharedPreference.getInt("custom_day_int", 3);
        if (customDayInt != -1){
            customEditText.setText(Integer.toString(customDayInt));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}