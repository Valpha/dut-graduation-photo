package com.dlut.picturemaker.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dlut.picturemaker.R;
import com.dlut.picturemaker.data.DataBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterHead extends RecyclerView.Adapter<AdapterHead.VH> {

    private static final String TAG = "head";
    List<DataBean> personList = new ArrayList<>();

        public void setPersonList(List<DataBean> personList) {
            Log.d(TAG, "setPersonList: HeadAdapter数据更新了！！当前数据有"+personList.size()+"组。");
            this.personList = personList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_body, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            ((ImageView) holder.itemView).setImageBitmap(personList.get(position).headBitmap);
            holder.itemView.setOnClickListener(v -> Log.d(TAG, "onBindViewHolder: 点击了第" + position + "个head。"));
        }

        @Override
        public int getItemCount() {
            return personList.size();
        }

    public void setChosedPerson(DataBean dataBean) {
        personList = Collections.singletonList(dataBean);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
            public VH(@NonNull View itemView) {
                super(itemView);
            }
        }
    }