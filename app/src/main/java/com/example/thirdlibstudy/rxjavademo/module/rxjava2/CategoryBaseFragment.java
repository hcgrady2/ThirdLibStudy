package com.example.thirdlibstudy.rxjavademo.module.rxjava2;

import android.graphics.Color;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.thirdlibstudy.R;
import com.example.thirdlibstudy.rxjavademo.base.BaseFragment;
import com.example.thirdlibstudy.rxjavademo.model.OperatorModel;
import com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators.OperatorsAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-30  14:34
 */

public abstract class CategoryBaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected List<OperatorModel> data;

    @BindView(R.id.operators_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.operators_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_operators;
    }

    protected abstract void fillData();

    protected abstract void itemClick(int position);

    @Override
    protected void init() {
        fillData();
        OperatorsAdapter adapter = new OperatorsAdapter(data) {
            @Override
            public void onItemClick(int position) {
                itemClick(position);
            }
        };

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        mRefreshLayout.setOnRefreshListener(this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(adapter);

    }


    @Override
    public void onRefresh() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
}