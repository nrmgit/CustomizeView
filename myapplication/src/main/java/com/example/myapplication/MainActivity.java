package com.example.myapplication;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private int[] data;
    private ArrayList<ImageView> imgList;
    private LinearLayout ll_yuandian;
    private View yuandian;
    private int lastEnablePoint = 0;
    private String[] textData;
    private TextView tv_des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAdapter();
        initCurrment();

    }

    private void initCurrment() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (vp!=null)
                            vp.setCurrentItem(vp.getCurrentItem()+1);
                    }
                });

            }
        },1000,3000);
    }

    private void initViews() {
        vp = (ViewPager) findViewById(R.id.vp_lunbotu);
        vp.addOnPageChangeListener(this);
        ll_yuandian = (LinearLayout) findViewById(R.id.ll_yuandian);
        tv_des = (TextView) findViewById(R.id.tv_desc);
        data = new int[]{R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        textData = new String[]{"文本1111","文本2222","文本3333","文本4444"};
        imgList = new ArrayList<>();
        for (int i = 0;i<data.length;i++){
           // tv_des.setText(textData[i]);
            ImageView img = new ImageView(this);
            img.setBackgroundResource(data[i]);
            imgList.add(img);
            yuandian = new View(this);
            yuandian.setEnabled(false);//原点默认设置为false
            yuandian.setBackgroundResource(R.drawable.selector_yuandian);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
            params.leftMargin=10;
            ll_yuandian.addView(yuandian,params);
        }
    }
    private void initAdapter() {
        ll_yuandian.getChildAt(0).setEnabled(true);//默认设置0号位被选中
        tv_des.setText(textData[0]);
        vp.setAdapter(new MyAdapter());
        vp.setCurrentItem(5000000);//在Adapter设置完之后在设置跳转页面

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newspo =position%4;
        ll_yuandian.getChildAt(lastEnablePoint).setEnabled(false);
        ll_yuandian.getChildAt(newspo).setEnabled(true);
        lastEnablePoint = newspo;
        tv_des.setText(textData[newspo]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newpostion = position%4;
            ImageView view = imgList.get(newpostion);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
