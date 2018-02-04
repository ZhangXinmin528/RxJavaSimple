package com.example.rxjavasample;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.core.ui.BaseActivity;
import com.example.rxjava1.ui.CombiningOperatorActivity;
import com.example.rxjava1.ui.CreatingOperatorActivity;
import com.example.rxjava1.ui.ErrorHandlingOperatorActivity;
import com.example.rxjava1.ui.FilteringOperatorActivity;
import com.example.rxjava1.ui.RxJavaABCActivity;
import com.example.rxjava1.ui.TransferingOperatorActivity;
import com.example.rxjava1.ui.UtilityOperatorActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<String> mDataList;
    private ArrayAdapter<String> mAdapter;

    private List<Class> mClzList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        mDataList = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, mDataList);
        mClzList = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        ListView listView = findViewById(R.id.listview_main);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        //跳转
        final Class[] arr = new Class[]{RxJavaABCActivity.class, CreatingOperatorActivity.class,
                TransferingOperatorActivity.class, FilteringOperatorActivity.class, CombiningOperatorActivity.class,
                ErrorHandlingOperatorActivity.class, UtilityOperatorActivity.class};
        mClzList.addAll(Arrays.asList(arr));

        final String[] titles = new String[]{"初识RxJava Version 1.x", "RxJava Version 1.x的创建操作符",
                "RxJava Version 1.x的转换操作符", "RxJava Version 1.x的过滤操作符", "RxJava Version 1.x的组合操作符",
                "RxJava Version 1.x的错误操作符", "RxJava Version 1.x的辅助性操作符"};

        mDataList.addAll(Arrays.asList(titles));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mClzList.isEmpty())
            return;
        Intent intent = new Intent();
        intent.setClass(mContext, mClzList.get(position));
        startActivity(intent);
    }
}
