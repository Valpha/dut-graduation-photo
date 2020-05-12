package com.dlut.picturemaker.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.dlut.picturemaker.R;

import java.io.IOException;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.VH> {

    List<Drawable> mDatasets;

    public PictureAdapter(List<Drawable> mDatasets)  {
        this.mDatasets = mDatasets;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        ((ImageView) holder.itemView).setImageDrawable(mDatasets.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatasets.size();
    }

    public void addCustomImage(Bitmap image) {
        Drawable drawable = ImageUtils.bitmap2Drawable(image);
        mDatasets.add(drawable);
    }

    public void addCustomImage(Drawable image) {
        mDatasets.add(image);
    }

    public void setDatas(List<Drawable> drawables) {
        this.mDatasets=(drawables);
        notifyDataSetChanged();
    }

    public class VH extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public VH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
