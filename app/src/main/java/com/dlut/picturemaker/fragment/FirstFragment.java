package com.dlut.picturemaker.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.blankj.utilcode.util.ImageUtils;
import com.dlut.picturemaker.utils.GlideEngine;
import com.dlut.picturemaker.R;
import com.dlut.picturemaker.ViewModel.ImageViewModel;
import com.dlut.picturemaker.adapter.PictureAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FirstFragment extends Fragment {


    private static final String TAG = "FirstFragment";
    private PictureAdapter adapter;
    private File cameraPhoto;
    private ImageViewModel model;
    private List<Drawable> pictures;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(ImageViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .isCamera(true)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            String path;
                            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                                path = result.get(0).getAndroidQToPath();
                            } else {
                                path = result.get(0).getPath();
                            }
                            Drawable image = Drawable.createFromPath(path);
                            model.addDrawableList(image);
                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "onCancel: 取消选择");
                        }
                    });
        });

        DiscreteScrollView scrollView = view.findViewById(R.id.picker);
        pictures = model.getDrawableList().getValue();
        if (pictures.isEmpty()) {

            try {
                String[] list = getActivity().getAssets().list("");
                for (String s : list) {
                    if (s.endsWith(".jpg") || s.endsWith(".png")) {
                        InputStream inputStream = getActivity().getAssets().open(s);
                        Drawable image = Drawable.createFromStream(inputStream, s);
                        pictures.add(image);
                        model.setDrawableList(pictures);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        adapter = new PictureAdapter(pictures);
        model.getDrawableList().observe(getActivity(), drawables -> adapter.setDatas(drawables));

        scrollView.setAdapter(adapter);
        scrollView.setSlideOnFling(true);
        scrollView.setSlideOnFlingThreshold(3600);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());

        scrollView.addOnItemChangedListener((viewHolder, i) -> {
            String tag = (String) viewHolder.itemView.getTag();
            Log.d("choose", "选中了某个图片，位置是" + i + "图片属性为：" + tag);
            model.setImage(ImageUtils.drawable2Bitmap(((PictureAdapter.VH) viewHolder).imageView.getDrawable()));
            model.setNumberSelected(i);
        });
        if (model.getSelectedNumber() != null) {
            scrollView.scrollToPosition(model.getSelectedNumber());
        }

        // if (MainActivity.INIT_GUIDE) {
        //     showGuideView();
        // }
    }

    // public void showGuideView() {
    //     GuideBuilder builder = new GuideBuilder();
    //     builder .setTargetViewId()
    //             .setAlpha(150)
    //             .setHighTargetCorner(20)
    //             .setAutoDismiss(true)
    //             .setHighTargetPadding(10);
    //     builder.addComponent(new FirstComponent());
    //     Guide guide = builder.createGuide();
    //     guide.show(getActivity());
    // }
}
