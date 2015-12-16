package com.jasonko.movietime;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jasonko.movietime.adapters.MessageTagAdapter;
import com.jasonko.movietime.api.MessageAPI;

/**
 * Created by kolichung on 10/14/15.
 */
public class WriteMessageActivity extends AppCompatActivity {

    ImageView imageView;
    EditText nicknameEditText;
    EditText contentEditText;
    EditText titleEditText;
    EditText linkEditText;

    Button sendButton;
    CheckBox nickNameCheckBox;

    boolean isPosting = false;
    boolean isPublished = false;

    private RecyclerView tagRecycler;
    private SharedPreferences prefs;

    private int mHeadCheckedId = R.id.radio_1;
    private int headIndex = 1;

    int board_id;
    String tags[];
    MessageTagAdapter messageTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        prefs = getSharedPreferences(null, 0);

        board_id = getIntent().getIntExtra("board_id", 0);
        switch (board_id){
            case 0:
                tags = AppParams.keyAnnouncement;
                break;
            case 1:
                tags = AppParams.keyMovie;
                break;
            case 2:
                tags = AppParams.keyDrama;
                break;
            case 3:
                tags = AppParams.keyLife;
                break;
        }

        imageView = (ImageView) findViewById(R.id.write_comment_image);
        nicknameEditText = (EditText) findViewById(R.id.write_comment_nickname_edittext);
        contentEditText = (EditText) findViewById(R.id.write_comment_content_edittext);
        titleEditText = (EditText) findViewById(R.id.write_message_title_edittext);
        linkEditText = (EditText) findViewById(R.id.write_link_edittext);

        sendButton = (Button) findViewById(R.id.write_comment_send_button);
        nickNameCheckBox = (CheckBox) findViewById(R.id.checkbox_nickname);

        tagRecycler = (RecyclerView) findViewById(R.id.message_tag_recycler_view);
        tagRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tagRecycler.setLayoutManager(mLayoutManager);
        messageTagAdapter = new MessageTagAdapter(tags,this);
        tagRecycler.setAdapter(messageTagAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        switch (board_id){
            case 0:
                getSupportActionBar().setTitle("公告區");
                break;
            case 1:
                getSupportActionBar().setTitle("電影版");
                break;
            case 2:
                getSupportActionBar().setTitle("戲劇版");
                break;
            case 3:
                getSupportActionBar().setTitle("生活版");
                break;
            case 4:
                getSupportActionBar().setTitle("最近按讚");
                break;
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoseHeadDialog();
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
            headIndex = prefs.getInt("HeadIndex", 1);
            switchHead(headIndex);
            nicknameEditText.setText(nickName);
            nickNameCheckBox.setChecked(true);
        }else {
            nickNameCheckBox.setChecked(false);
        }
    }

    private void switchHead(int headIndex) {
        switch (headIndex){
            case 1:
                imageView.setImageResource(R.drawable.head_captain);
                break;
            case 2:
                imageView.setImageResource(R.drawable.head_iron_man);
                break;
            case 3:
                imageView.setImageResource(R.drawable.head_black_widow);
                break;
            case 4:
                imageView.setImageResource(R.drawable.head_thor);
                break;
            case 5:
                imageView.setImageResource(R.drawable.head_hulk);
                break;
            case 6:
                imageView.setImageResource(R.drawable.head_hawkeye);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nickNameCheckBox.isChecked()){
            if (nicknameEditText.getText()!=null && !nicknameEditText.getText().toString().equals("")) {
                prefs.edit().putString("NickName", nicknameEditText.getText().toString()).commit();
                prefs.edit().putInt("HeadIndex", headIndex).commit();
            }
        }
    }

    private void showChoseHeadDialog() {

        View contentView = this.getLayoutInflater().inflate(R.layout.dialog_chose_head,null);
        final RadioGroup radioGroup_1 = (RadioGroup) contentView.findViewById(R.id.radio_group_1);
        final RadioGroup radioGroup_2 = (RadioGroup) contentView.findViewById(R.id.radio_group_2);

        radioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1){
                    radioGroup_2.clearCheck();
                    mHeadCheckedId = checkedId;
                    group.check(checkedId);
                }
            }
        });

        radioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1){
                    radioGroup_1.clearCheck();
                    mHeadCheckedId = checkedId;
                    group.check(checkedId);
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WriteMessageActivity.this);

        alertDialogBuilder.setTitle("選擇頭像");
        alertDialogBuilder.setView(contentView);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switch (mHeadCheckedId){
                    case R.id.radio_1:
                        headIndex = 1;
                        imageView.setImageResource(R.drawable.head_captain);
                        break;
                    case R.id.radio_2:
                        headIndex = 2;
                        imageView.setImageResource(R.drawable.head_iron_man);
                        break;
                    case R.id.radio_3:
                        headIndex = 3;
                        imageView.setImageResource(R.drawable.head_black_widow);
                        break;
                    case R.id.radio_4:
                        headIndex = 4;
                        imageView.setImageResource(R.drawable.head_thor);
                        break;
                    case R.id.radio_5:
                        headIndex = 5;
                        imageView.setImageResource(R.drawable.head_hulk);
                        break;
                    case R.id.radio_6:
                        headIndex = 6;
                        imageView.setImageResource(R.drawable.head_hawkeye);
                        break;
                }
            }
        });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

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
            String l = linkEditText.getText().toString();
            String tag = messageTagAdapter.getTagString();

            String result = MessageAPI.httpPostMessage(board_id,a,t,tag,c,headIndex,l);
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
