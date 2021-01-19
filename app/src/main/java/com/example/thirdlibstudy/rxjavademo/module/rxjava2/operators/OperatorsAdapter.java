package com.example.thirdlibstudy.rxjavademo.module.rxjava2.operators;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.thirdlibstudy.R;
import com.example.thirdlibstudy.rxjavademo.model.OperatorModel;

import java.util.List;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-20  15:03
 */

public abstract class OperatorsAdapter extends BaseQuickAdapter<OperatorModel, BaseViewHolder> {

    public OperatorsAdapter(@Nullable List<OperatorModel> data) {
        super(R.layout.layout_item_operator,data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, OperatorModel item) {
        if (item != null){
            holder.setText(R.id.item_title,item.title)
                    .setText(R.id.item_des,item.des)
                    .getConvertView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    public abstract void onItemClick(int position);
}
