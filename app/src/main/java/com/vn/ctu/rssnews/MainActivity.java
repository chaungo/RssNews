package com.vn.ctu.rssnews;


import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity {
    public static ViewPager viewPager;
    static myViewPageAdp adp;
    public static String Rssurl = "http://vnexpress.net/rss/tin-moi-nhat.rss";
    public static String chude = "Tin mới nhất";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.color.den10));
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adp = new myViewPageAdp(getSupportFragmentManager());



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adp);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.tinmoi:{
                Rssurl = "http://vnexpress.net/rss/tin-moi-nhat.rss";
                chude = "Tin mới nhất";
                break;
            }
            case R.id.thoisu:{
                Rssurl = "http://vnexpress.net/rss/thoi-su.rss";
                chude = "Thời sự";
                break;
            }
            case R.id.thegioi:{
                Rssurl = "http://vnexpress.net/rss/the-gioi.rss";
                chude = "Thế giới";
                break;
            }
            case R.id.thethao:{
                Rssurl = "http://vnexpress.net/rss/the-thao.rss";
                chude = "Thể thao";
                break;
            }
            case R.id.phapluat:{
                Rssurl = "http://vnexpress.net/rss/phap-luat.rss";
                chude = "Pháp luật";
                break;
            }
            case R.id.giaoduc:{
                Rssurl = "http://vnexpress.net/rss/giao-duc.rss";
                chude = "Giáo dục";
                break;
            }
            case R.id.suckhoe:{
                Rssurl = "http://vnexpress.net/rss/suc-khoe.rss";
                chude = "Sức khoẻ";
                break;
            }
            case R.id.doisong:{
                Rssurl = "http://vnexpress.net/rss/doi-song.rss";
                chude = "Đời sống";
                break;
            }
            case R.id.dulich:{
                Rssurl = "http://vnexpress.net/rss/du-lich.rss";
                chude = "Du lịch";
                break;
            }
            case R.id.khoahoc:{
                Rssurl = "http://vnexpress.net/rss/khoa-hoc.rss";
                chude = "Khoa học";
                break;
            }
            default:return true;

        }
        viewPager.setCurrentItem(0);
        adp = new myViewPageAdp(getSupportFragmentManager());
        viewPager.setAdapter(adp);


        return super.onOptionsItemSelected(item);
    }










}
