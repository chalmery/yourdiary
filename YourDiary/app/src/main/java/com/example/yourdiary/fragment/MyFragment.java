package com.example.yourdiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yourdiary.R;
import com.example.yourdiary.dao.UserDao;

public class MyFragment extends Fragment{

    private ImageView ivHead;
    private TextView tvUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        init();
    }

    //初始化控件
    private void init() {

        ivHead=getActivity().findViewById(R.id.iv_head);
        //指定默认头像文件
        ivHead.setImageResource(R.drawable.head1);

        //显示用户名
        tvUsername=getActivity().findViewById(R.id.tv_username);
        tvUsername.setText("你好： "+UserDao.name);
    }
}



