package com.example.yourdiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.yourdiary.R;
import com.example.yourdiary.showActivity.ShowActivity;
import com.example.yourdiary.bean.Diary;
import com.example.yourdiary.dao.DiaryDao;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends ListFragment {
    //数据源
    private  List<Diary> diaries = new ArrayList<>();

    private ListView list;
    private  DiaryDao diaryDao;
    private MyAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary,container,false);


    }
    @Override
    public void onResume() {
        super.onResume();

        list=getActivity().findViewById(android.R.id.list);
        //列表视图的单击处理事件
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到日记详情界面
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                //获取这个item的日期
                intent.putExtra("date",diaries.get(position).getDate());
                startActivity(intent);
            }
        });

        //执行查询操作，获取全部的日记
        diaryDao = new DiaryDao(getActivity());
        diaries=diaryDao.selectAll();
        //绑定适配器
        adapter= new MyAdapter();
        list.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {
        // 得到item的总数
        @Override
        public int getCount() {
            return diaries.size();
        }

        // 得到每个position位置上的item代表的对象
        @Override
        public Object getItem(int position) {
            return diaries.get(position);
        }

        // 得到item的Id
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 得到item的View视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            //如果每个list都被占满了
            if (convertView == null) {
                // 利用布局填充器填充自定义的ListView item的布局
                convertView = LayoutInflater.from(getActivity()).
                        inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                // 利用得到的view来进行findViewById()
                holder.tvDay =  convertView.findViewById(R.id.tv_day);
                holder.tvMonth=convertView.findViewById(R.id.tv_month);
                holder.tvContent =  convertView.findViewById(R.id.tv_content);

                //将holder存入view的tag中
                convertView.setTag(holder);
            }
            //如果不为空，有可复用的convertView
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            //设置holder中每个控件的内容
            String date=diaries.get(position).getDate();
            holder.tvDay.setText(date.substring(5));
            holder.tvMonth.setText(date.substring(4,5)+"月");

            String content=diaries.get(position).getContent();
            holder.tvContent.setText(content);
            return convertView;
        }

        class ViewHolder{
            TextView tvDay;
            TextView tvMonth;
            TextView tvContent;
        }

    }

}
