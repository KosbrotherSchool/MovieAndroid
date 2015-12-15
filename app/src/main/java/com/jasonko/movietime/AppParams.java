package com.jasonko.movietime;

import android.app.Activity;
import android.content.SharedPreferences;

import com.jasonko.movietime.model.DrawerItem;
import com.jasonko.movietime.model.OrderTheater;

/**
 * Created by kolichung on 8/31/15.
 */
public class AppParams {

    public static final OrderTheater[] orderTheaters ={
            new OrderTheater("EZ訂", "http://www.ezding.com.tw/mmb.do", R.drawable.theater_icon_5),
            new OrderTheater("DingOK", "http://m.dingok.com/", R.drawable.theater_icon_6),
            new OrderTheater("636影城通", "http://emome.636.com.tw/smartphone/pages/home.php", R.drawable.theater_icon_7),
            new OrderTheater("威秀", "http://www.vscinemas.com.tw/Mobile/SelectSession.aspx", R.drawable.theater_icon_9),
            new OrderTheater("美麗華影城", "https://web.miramarcinemas.com.tw/visInternetTicketing/visChooseSession.aspx?visLang=2&AspxAutoDetectCookieSupport=1", R.drawable.theater_icon_10),
            new OrderTheater("新光影城", "http://www.skcinemas.com/Order.aspx", R.drawable.theater_icon_11),
            new OrderTheater("SBC星橋", "https://www.sbcmovies.com.tw/visSelect.aspx?visSearchBy=cin&visCinID=1001&visLang=2", R.drawable.theater_icon_4),
            new OrderTheater("in89影城", "http://www.in89.com.tw/booking_00.php", R.drawable.theater_icon_3),
            new OrderTheater("喜滿客美奇萊影城", "http://maichilai.ehosting.com.tw/login.aspx", R.drawable.theater_icon_2),
            new OrderTheater("樂聲影城", "http://tix.luxcinema.com.tw/Booking/index.php/login", R.drawable.theater_icon_1),
            new OrderTheater("博客來訂票", "http://tickets.books.com.tw/movie/", R.drawable.theater_icon_8),
    };

//    public static final Blogger[] bloggers ={
//            new Blogger("Das Kino波電影", "http://bernd97.pixnet.net/blog", R.drawable.blogger_5),
//            new Blogger("鯨鯊的塗鴨影評", "http://whale005.pixnet.net/blog", R.drawable.blogger_1),
//            new Blogger("火行者的電影部落格", "http://firewalker-movie.blogspot.tw/", R.drawable.blogger_4)
//    };


    public static final DrawerItem[] drawerItems={
           new DrawerItem("我的最愛", R.drawable.drawer_love),
           new DrawerItem("我的設定", R.drawable.drawer_setting),
            new DrawerItem("問題回報", R.drawable.drawer_problem),
            new DrawerItem("給星星", R.drawable.drawer_star),
            new DrawerItem("關於我們", R.drawable.drawer_about)
    };

    public static boolean isShowIntersitialAd(Activity activity){
           SharedPreferences myPref = activity.getPreferences(0);
           int click_count = myPref.getInt("click_count", 0);
           click_count = click_count + 1;
           if (click_count == 5){
             myPref.edit().putInt("click_count", 0).commit();
             return  true;
           }else {
             myPref.edit().putInt("click_count", click_count).commit();
             return  false;
           }
    }

}
