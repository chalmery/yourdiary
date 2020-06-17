package com.example.yourdiary;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.yourdiary.fragment.DateFragment;
import com.example.yourdiary.fragment.DiaryFragment;
import com.example.yourdiary.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private RadioGroup rg;

    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();

        //初始化数据
        initData();

    }

    private void initData() {
        fragments =new ArrayList<>();
        fragments.add(new DiaryFragment());
        fragments.add(new DateFragment());
        fragments.add(new MyFragment());


        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        //初始页面的按钮颜色处理
        RadioButton rb =(RadioButton)rg.getChildAt(0);
        rb.setChecked(true);

    }

    //初始化控件
    private void initView() {

        vp =findViewById(R.id.vp);
        //页面切换的监听器
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {//传入值position即指定当前页面是哪个页面
                //当某个页面被选中，相应的按钮也被选中(颜色进行变化)
               RadioButton rb=(RadioButton) rg.getChildAt(position);
               changeCheckedColor(position);
                rb.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        rg =findViewById(R.id.rg);
        //点击按钮，按钮颜色进行改变，页面随之改变
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_diary:
                        vp.setCurrentItem(0);
                        changeCheckedColor(0);
                        break;

                    case R.id.rb_date:
                        vp.setCurrentItem(1);
                        changeCheckedColor(1);
                        break;

                    case R.id.rb_my:
                        vp.setCurrentItem(2);
                        changeCheckedColor(2);
                        break;
                }
            }
        });
    }

    //把选中的按钮颜色与没有选中的进行区别
    private  void changeCheckedColor(int position){
        for(int i =0;i <rg.getChildCount();i++){
            RadioButton rb = (RadioButton) rg.getChildAt(i);

            if(i==position){
                rb.setTextColor(Color.parseColor("#EFEFEF"));//按钮文字选中为白色
                rb.setBackgroundColor(Color.parseColor("#4987B6"));//按钮背景为蓝色
            }
            else{
                rb.setTextColor(Color.parseColor("#4987B6"));
                rb.setBackgroundColor(Color.parseColor("#EFEFEF"));
            }
        }
    }
    //管理fragment，根据索引position，它的值展示相应的fragment
    class MyPagerAdapter extends FragmentPagerAdapter{


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        //根据position的值返回fragment对象
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        //返回fragment个数，滑动切换一共有几个界面
        public int getCount() {
            return fragments.size();
        }
    }
}
