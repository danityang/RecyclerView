package com.cdemo.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // recyclerview
    RecyclerView recyclerView;
    List<String> list;
    // 适配器
    private  RecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        initListData(list);
        adapter = new RecycleAdapter(list, MainActivity.this);

        /*RecyclerView.LayoutManager是一个抽象类，系统为我们提供了三个实现类
        1.LinearLayoutManager即线性布局，这个是在上面的例子中我们用到的布局
        2.GridLayoutManager即表格布局
        3.StaggeredGridLayoutManager即流式布局，如瀑布流效果*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        // 添加分割线，后面的参数表示是横线还是竖线，0，横线 1，竖线
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,0));
        recyclerView.addItemDecoration(new ItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

        // iteM动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initListData(List<String> l){
        for (int i = 0; i < 100; i++) {
            l.add("item"+i);
        }

    }



}
