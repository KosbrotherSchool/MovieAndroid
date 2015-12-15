package com.jasonko.movietime;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jasonko.movietime.api.MessageAPI;

/**
 * Created by kolichung on 10/14/15.
 */
public class WriteMessageActivity extends AppCompatActivity {

    EditText nicknameEditText;
    EditText contentEditText;
    EditText titleEditText;
    EditText tagEditText;

    Button sendButton;
    CheckBox nickNameCheckBox;

    boolean isPosting = false;
    boolean isPublished = false;


    private SharedPreferences prefs;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        prefs = getSharedPreferences(null, 0);


        nicknameEditText = (EditText) findViewById(R.id.write_comment_nickname_edittext);
        contentEditText = (EditText) findViewById(R.id.write_comment_content_edittext);
        titleEditText = (EditText) findViewById(R.id.write_message_title_edittext);
        tagEditText = (EditText) findViewById(R.id.write_message_tag_edittext);

        sendButton = (Button) findViewById(R.id.write_comment_send_button);
        nickNameCheckBox = (CheckBox) findViewById(R.id.checkbox_nickname);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_message_tag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("哈拉區");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setBackgroundResource(R.color.pink_color);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPublished) {
                    if (!isPosting) {
                        if (!nicknameEditText.getText().toString().equals("")
                                && !contentEditText.getText().toString().equals("")
                                && !titleEditText.getText().toString().equals("")) {
                            new PostTask().execute();
                        } else {
                            Toast.makeText(WriteMessageActivity.this, "暱稱,標題,內容不可空白~", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(WriteMessageActivity.this, "上傳中...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WriteMessageActivity.this, "已經上傳過囉！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        nickNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    prefs.edit().putBoolean("is save nickname", true).commit();
                }else {
                    prefs.edit().putBoolean("is save nickname", false).commit();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isSaveNickName = prefs.getBoolean("is save nickname", false);
        if (isSaveNickName){
            String nickName = prefs.getString("NickName","");
            nicknameEditText.setText(nickName);
            nickNameCheckBox.setChecked(true);
        }else {
            nickNameCheckBox.setChecked(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nickNameCheckBox.isChecked()){
            if (nicknameEditText.getText()!=null && !nicknameEditText.getText().toString().equals("")) {
                prefs.edit().putString("NickName", nicknameEditText.getText().toString()).commit();
            }
        }
    }


    private class PostTask extends AsyncTask {

        Toast upLoadToast;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            upLoadToast = Toast.makeText(WriteMessageActivity.this, "上傳中...", Toast.LENGTH_SHORT);
            upLoadToast.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String a = nicknameEditText.getText().toString();
            String t = titleEditText.getText().toString();
            String c = contentEditText.getText().toString();
            String tag="";
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.radio_1:
                    tag = "分享";
                    break;
                case R.id.radio_2:
                    tag = "推薦";
                    break;
                case R.id.radio_3:
                    try {
                        tag = tagEditText.getText().toString();
                    }catch (Exception e){

                    }
                    break;
            }

            String result = MessageAPI.httpPostMessage(a,t,c,tag);
            if (result.equals("ok")){
                return true;
            }else {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Object result) {
            upLoadToast.cancel();
            if ((boolean)result){
                Toast.makeText(WriteMessageActivity.this, "成功上傳", Toast.LENGTH_SHORT).show();
                isPublished = true;
                finish();
            }else {
                Toast.makeText(WriteMessageActivity.this, "未上傳成功", Toast.LENGTH_SHORT).show();
            }
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
