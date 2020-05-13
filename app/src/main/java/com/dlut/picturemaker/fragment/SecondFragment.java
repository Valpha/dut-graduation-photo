package com.dlut.picturemaker.fragment;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dlut.picturemaker.utils.GlideEngine;
import com.dlut.picturemaker.utils.ImageProcess;
import com.dlut.picturemaker.R;
import com.dlut.picturemaker.ViewModel.ImageViewModel;
import com.dlut.picturemaker.adapter.AdapterHead;
import com.dlut.picturemaker.adapter.AdapterTemplete;
import com.dlut.picturemaker.data.DataBean;
import com.dlut.picturemaker.utils.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerIconEvent;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.ZoomIconEvent;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class SecondFragment extends Fragment {
    private static final String TAG = "second";
    private static final int RC_CHOOSE_PERSON = 10;
    private static final int RC_TAKE_PHOTO_PERSON = 11;
    private StickerView stickerView;
    private ImageViewModel model;
    private ImageView imageBackground;
    private File cameraPhoto;
    private Uri photoUri;
    private CardView bottomLayotu;
    private ZLoadingDialog dialog;
    private AdapterHead personAdapter = new AdapterHead();
    private DiscreteScrollView rvHead;
    private DiscreteScrollView rvTemplete;
    // private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(ImageViewModel.class);

    }






    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.clearPerson();

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigateUp();
            }
        });
        view.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String path = PathUtils.getExternalPicturesPath() + "/云合影/" + TimeUtils.getNowString() + ".jpg";
                // File file = new File(path);
                // FileUtils.createOrExistsFile(file);
                // stickerView.save(file);

                Uri uri = MyUtils.saveToDcim(stickerView.createBitmap(), "IMG_" + TimeUtils.getNowMills() + ".jpg", getActivity());
                // saveImage(stickerView.createBitmap())
                if (uri != null) {
                    model.setFinishedImageUri(uri);
                    ToastUtils.showShort("saved in " + uri.getPath());
                }
                // model.setFinishedImage(file);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_outputFragment);
            }
        });
        FloatingActionButton fab = view.findViewById(R.id.fab);
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
                                // path = result.get(0).getPath();
                            } else {
                                path = result.get(0).getPath();
                            }

                            dialog.show();
                            ImageProcess.getHeads(SecondFragment.this, path);
                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "onCancel: 取消选择");
                        }
                    });

        });
        // fab.setOnLongClickListener(v -> addPeopleByCamera());
        stickerView = view.findViewById(R.id.sticker_view);
        imageBackground = view.findViewById(R.id.image_background);
        imageBackground.setImageBitmap(model.getImage());
        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fold),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new StickerIconEvent() {
            @Override
            public void onActionDown(StickerView stickerView, MotionEvent event) {

            }

            @Override
            public void onActionMove(StickerView stickerView, MotionEvent event) {

            }

            @Override
            public void onActionUp(StickerView stickerView, MotionEvent event) {
                showOnOff(stickerView.getCurrentSticker());
            }
        });
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent() {
            @Override
            public void onActionUp(StickerView stickerView, MotionEvent event) {
                Sticker currentSticker = stickerView.getCurrentSticker();
                model.removeSticker(currentSticker);
                super.onActionUp(stickerView, event);

            }
        });

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());
        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, heartIcon, flipIcon));
        // stickerView.configDefaultIcons();
        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDeleted");
                showOff();
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerTouchedDown");
                model.setChosedDataBean(sticker);
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d(TAG, "onDoubleTapped: double tap will be with two click");
                showOnOff(sticker);

            }
        });
        stickerView.setOnClickListener(v -> showOff());
        stickerView.setConstrained(true);

        bottomLayotu = view.findViewById(R.id.bottom_layout);
        rvTemplete = view.findViewById(R.id.rv_templete);
        rvTemplete.setAdapter(new AdapterTemplete(getActivity()));
        rvTemplete.setSlideOnFling(false);
        rvTemplete.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());
        rvTemplete.addOnItemChangedListener((viewHolder, i) -> model.setChosedTemplete(i));

        rvHead = view.findViewById(R.id.rv_person);
        rvHead.setAdapter(personAdapter);
        rvHead.setSlideOnFling(false);
        rvHead.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());
        rvHead.addOnItemChangedListener((viewHolder, i) -> model.setChosedNumber(i));

        dialog = new ZLoadingDialog(getActivity());
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.LTGRAY)//颜色
                .setHintText("Loading...")
                .setHintTextColor(Color.LTGRAY)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false);
        model.getPersonList().observe(getActivity(), dataBeans -> personAdapter.setPersonList(dataBeans));
        model.getChosedNumber().observe(getActivity(), position -> {
            if (position != null) {

                rvHead.scrollToPosition(position);
                rvTemplete.scrollToPosition(model.getPersonList().getValue().get(position).chosen_templete);
            }
        });

        // if (MainActivity.INIT_GUIDE) {
        //     showGuideView();
        // }
    }

    // private void showGuideView() {
    //     GuideBuilder builder = new GuideBuilder();
    //     builder.setAlpha(150)
    //             .setHighTargetCorner(20)
    //             .setHighTargetPadding(10);
    //     builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
    //         @Override
    //         public void onShown() {
    //         }
    //
    //         @Override
    //         public void onDismiss() {
    //             // showGuideView2();
    //         }
    //     });
    //
    //     builder.addComponent(new SecondComponent());
    //     Guide guide = builder.createGuide();
    //     guide.show(getActivity());
    // }


    private void showOff() {
        bottomLayotu.setVisibility(View.GONE);
    }

    private void showOn() {
        bottomLayotu.setVisibility(View.VISIBLE);
    }

    private void showOnOff(Sticker sticker) {

        if (bottomLayotu.getVisibility() == View.GONE) {


            bottomLayotu.setVisibility(View.VISIBLE);
        } else {
            bottomLayotu.setVisibility(View.GONE);
        }
    }


    public void gotHeads(List<DataBean> result) {
        dialog.cancel();
        Log.d(TAG, "gotHeads: called!");
        if (result == null) {
            ToastUtils.showShort("加载失败！");
        } else if (!result.isEmpty()) {

            showOn();
            for (DataBean head : result) {
                head.chosen_templete = 0;
                Sticker sticker = new DrawableSticker(ImageProcess.makeSticker(head, head.chosen_templete));
                head.sticker = sticker;
                stickerView.addSticker(sticker);
                model.addHead(head);

            }
        }

    }

    public void showWhatWrong(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("错误原因")
                .setMessage(message)
                .setPositiveButton("复制", (dialog, which) -> {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", message);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    dialog.dismiss();
                });
        dialogBuilder.show();


    }

}
