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
import android.widget.Toast;

import com.jasonko.movietime.api.MessageAPI;

/**
 * Created by kolichung on 10/18/15.
 */
public class WriteReplyActivity extends AppCompatActivity {

    EditText nicknameEditText;
    EditText contentEditText;

    Button sendButton;
    CheckBox nickNameCheckBox;

    boolean isPosting = false;
    boolean isPublished = false;

    private int message_id;

    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_reply);
        prefs = getSharedPreferences(null, 0);

        String title = getIntent().getStringExtra("title");
        message_id = getIntent().getIntExtra("message_id",0);

        nicknameEditText = (EditText) findViewById(R.id.write_comment_nickname_edittext);
        contentEditText = (EditText) findViewById(R.id.write_comment_content_edittext);

        sendButton = (Button) findViewById(R.id.write_comment_send_button);
        nickNameCheckBox = (CheckBox) findViewById(R.id.checkbox_nickname);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("回覆："+title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setBackgroundResource(R.color.pink_color);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPublished) {
                    if (!isPosting) {
                        if (!nicknameEditText.getText().toString().equals("")
                                && !contentEditText.getText().toString().equals("")
                               ) {
                            new PostTask().execute();
                        } else {
                            Toast.makeText(WriteReplyActivity.this, "暱稱,內容不可空白~", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(WriteReplyActivity.this, "上傳中...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WriteReplyActivity.this, "已經上傳過囉！", Toast.LENGTH_SHORT).show();
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
            upLoadToast = Toast.makeText(WriteReplyActivity.this, "上傳中...", Toast.LENGTH_SHORT);
            upLoadToast.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String a = nicknameEditText.getText().toString();
            String c = contentEditText.getText().toString();

            String result = MessageAPI.httpPostReply(a,c,message_id);
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
                Toast.makeText(WriteReplyActivity.this, "成功上傳", Toast.LENGTH_SHORT).show();
                isPublished = true;
                finish();
            }else {
                Toast.makeText(WriteReplyActivity.this, "未上傳成功", Toast.LENGTH_SHORT).show();
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