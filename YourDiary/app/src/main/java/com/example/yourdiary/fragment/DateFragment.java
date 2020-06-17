package com.example.yourdiary.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yourdiary.R;
import com.example.yourdiary.bean.Date;
import com.example.yourdiary.showActivity.InsertActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class DateFragment extends Fragment implements View.OnClickListener {
    private TextView tvYear;

    private GridView gvWeek;
    private ImageButton imageButton;

    private DayAdapter dayAdapter;
    //日期数据源
    private List<Date> dataList = new ArrayList<>();

    private TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化布局
        initView();
        //初始化数据
        initData();

    }

    private void initView() {
        tvYear=getActivity().findViewById(R.id.tv_year);
        gvWeek=getActivity().findViewById(R.id.gv_week);
        imageButton = getActivity().findViewById(R.id.ib_insert);
        imageButton.setOnClickListener(this);
    }

    private void initData() {
        //设置当前年月
        Calendar calendar = Calendar.getInstance();
        String year =String.valueOf(calendar.get(Calendar.YEAR));//年
        String month =String.valueOf(calendar.get(Calendar.MONTH)+1);//月
        tvYear.setText(year+ "年" + month + "月");

        //设置日期的内容
        setDate(dataList);
        //绑定适配器
        dayAdapter = new DayAdapter();
        gvWeek.setAdapter(dayAdapter);
    }

    private void setDate(List<Date> dataList) {
        //获取到当月的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //国际上周日是这周第一天，如果当天是星期一会返回2
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // 将日期设为上个月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        //调用getMonth方法获取到上个月的天数
        int preMonthDays = getMonth(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        // 拿到上一个月的最后几天的天数
        for (int i = 0; i < weekIndex; i++) {
            Date date = new Date();
            date.setYear(calendar.get(Calendar.YEAR));
            date.setMonth(calendar.get(Calendar.MONTH) + 1);
            //可以发现， preMonthDays+weekIndex+i = 需要的日期数-1
            date.setDay(preMonthDays - weekIndex + i + 1);
            date.setCurrentDay(false);
            date.setCurrentMonth(false);

            dataList.add(date);
        }

        // 将日期设为当月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        //调用getMonth获取到当月的天数
        int currentDays = getMonth(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        // 拿到当月的天数
        for (int i = 0; i < currentDays; i++) {
            Date date = new Date();
            date.setYear(calendar.get(Calendar.YEAR));
            date.setMonth(calendar.get(Calendar.MONTH) + 1);
            date.setDay(i + 1);
            // 当前日期
            String nowDate = getFormatTime("yyyy-M-d", Calendar.getInstance().getTime());
            // 选择的日期
            String selectDate = getFormatTime("yyyy-M-", calendar.getTime()) + (i + 1);
            // 如果相等，就是今天的日期
            if (nowDate.equals(selectDate)) {
                date.setCurrentDay(true);
            } else {
                date.setCurrentDay(false);
            }
            date.setCurrentMonth(true);

            dataList.add(date);
        }

        // 拿到下个月第一周的天数
        // 先拿到下个月第一天的星期索引
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < 7 - weekIndex; i++) {
            Date date = new Date();
            date.setYear(calendar.get(Calendar.YEAR));
            date.setMonth(calendar.get(Calendar.MONTH) + 1);
            date.setDay(i + 1);
            date.setCurrentDay(false);
            date.setCurrentMonth(false);

            dataList.add(date);
        }
    }
    /**
     * 判断是否为闰年
     * @param year
     * @return
     */
    private boolean isRunYear(int year) {
        if(year% 4 == 0 && year % 100 != 0){
            return true;
        }
        else if(year % 400 == 0){
            return true;
        }
        else{
            return false;
        }
    }
    // 设置日期格式
    private static String getFormatTime(String p, java.util.Date t) {
        return new SimpleDateFormat(p, Locale.CHINESE).format(t);
    }
    /**
     *获得出当月的天数
     * @param month
     * @param year
     * @return
     */
    private int getMonth(int month, int year) {
        switch (month) {
            case 2:
                return isRunYear(year) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    //写日记按钮的点击事件
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ib_insert:
                Intent intent =new Intent();
                intent.setClass(getActivity(), InsertActivity.class);
                startActivity(intent);
                break;
        }

    }
    //设置日期格式
    class DayAdapter extends BaseAdapter {
        // 得到需要显示日期的总数
        @Override
        public int getCount() {
            return dataList.size();
        }

        // 得到每个position位置上的item代表的对象
        @Override
        public Date getItem(int position) {
            return dataList.get(position);
        }

        // 得到item的Id
        @Override
        public long getItemId(int position) {
            return 0;
        }

        //得到view视图
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            textView = new TextView(getActivity());
            textView.setPadding(0, 30, 0, 30);

            Date date = getItem(position);
            //设置每个格子的内容
            textView.setText(date.getDay() + "");
            //居中显示
            textView.setGravity(Gravity.CENTER);
            //设置不同情况的textView的背景颜色，字体颜色
            //如果是当天
            if (date.isCurrentDay()) {
                textView.setBackgroundColor(Color.parseColor("#5999B6"));
                textView.setTextSize(COMPLEX_UNIT_SP, 16);
                textView.setTextColor(Color.WHITE);
            }
            //如果是当前月
            else if (date.isCurrentMonth()) {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextSize(COMPLEX_UNIT_SP, 16);
                textView.setTextColor(Color.parseColor("#5999B6"));
            }
            //如果不是当前月
            else {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextSize(COMPLEX_UNIT_SP, 16);
                textView.setTextColor(Color.parseColor("#D2D2D2"));
            }
            // 返回textView
            return textView;
        }
    }

}