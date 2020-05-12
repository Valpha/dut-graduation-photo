package com.dlut.picturemaker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dlut.picturemaker.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTemplete extends RecyclerView.Adapter<AdapterTemplete.VH> {
    private static final String TAG = "templete";
    List<Drawable> listsDrawables = new ArrayList<>();

        public AdapterTemplete(Context context) {
            listsDrawables.add(context.getDrawable(R.drawable.muban1));
            listsDrawables.add(context.getDrawable(R.drawable.muban2));
            listsDrawables.add(context.getDrawable(R.drawable.muban3));
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_templete, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            ((ImageView) holder.itemView).setImageDrawable(listsDrawables.get(position));
            holder.itemView.setOnClickListener(v -> Log.d(TAG, "onBindViewHolder: 点击了第" + position + "个模板。"));
        }

        @Override
        public int getItemCount() {
            return listsDrawables.size();
        }

        class VH extends RecyclerView.ViewHolder {
            public VH(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
