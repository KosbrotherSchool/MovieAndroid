package com.jasonko.movietime.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.AboutUsActivity;
import com.jasonko.movietime.CreditCardActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.TicketActivity;
import com.jasonko.movietime.tool.GMailSender;

/**
 * Created by kolichung on 12/15/15.
 */
public class TabOtherFragment extends Fragment {

    public static TabOtherFragment newInstance() {
        TabOtherFragment fragment = new TabOtherFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_others, container, false);
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText("其他");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        CardView ticketCard = (CardView) root.findViewById(R.id.other_ticket_card_view);
        ticketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFollow = new Intent(getActivity(), TicketActivity.class);
                startActivity(intentFollow);
            }
        });

        CardView starCard = (CardView) root.findViewById(R.id.other_star_card_view);
        starCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.jasonko.movietime";
                Intent intentGood = new Intent(Intent.ACTION_VIEW);
                intentGood.setData(Uri.parse(url));
                startActivity(intentGood);
            }
        });

        CardView reportCard = (CardView) root.findViewById(R.id.other_report_card_view);
        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProblemDialog();
            }
        });

        CardView aboutCard = (CardView) root.findViewById(R.id.other_about_card_view);
        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAbout = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intentAbout);
            }
        });


        CardView creditCard = (CardView) root.findViewById(R.id.other_credit_card_view);
        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAbout = new Intent(getActivity(), CreditCardActivity.class);
                startActivity(intentAbout);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (menuItem.getItemId() == R.id.action_love) {
//            Intent searchIntent = new Intent(getActivity(), FavoriteTheaterActivity.class);
//            startActivity(searchIntent);
//        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showProblemDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View replyView = getActivity().getLayoutInflater().inflate(R.layout.mail_content_view, null);
        final EditText contentEditText = (EditText) replyView.findViewById(R.id.mail_content_edit_text);
        dialog.setView(replyView);
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }

        });
        dialog.setPositiveButton("送出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (!contentEditText.getText().toString().equals("")) {
                    String[] strings = {"電影即時通問題或建議(Android)", contentEditText.getText().toString()};
                    new SendMailTask().execute(strings);
                } else {
                    Toast.makeText(getActivity(), "空白內容無法傳送", Toast.LENGTH_SHORT).show();
                }
            }

        });
        dialog.show();

    }

    private class SendMailTask extends AsyncTask<String, Void, String> {

        Toast mailToast;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mailToast = Toast.makeText(getActivity(),"傳送中...", Toast.LENGTH_SHORT);
            mailToast.show();
        }

        @Override
        protected String doInBackground(String... params) {
            GMailSender sender = new GMailSender("movietimeautomail@gmail.com", "MovieTime2015");
            try {
                sender.sendMail(params[0],
                        params[1],
                        "movietimeautomail@gmail.com", "kosbrotherschool@gmail.com");
            }catch (Exception e){
                return "error";
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            mailToast.cancel();
            if (s.equals("ok")){
                Toast.makeText(getActivity(),"已成功上傳", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),"未成功上傳", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
