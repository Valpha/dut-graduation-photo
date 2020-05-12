package com.dlut.picturemaker.ViewModel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dlut.picturemaker.ImageProcess;
import com.dlut.picturemaker.data.DataBean;
import com.xiaopo.flying.sticker.Sticker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageViewModel extends ViewModel {
    private static final String TAG = "ViewModel";
    private final MutableLiveData<Bitmap> image = new MutableLiveData<>();
    private final MutableLiveData<Uri> customImagePath = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedNumber = new MutableLiveData<>();
    private MutableLiveData<Integer> chosedNumber = new MutableLiveData<>();
    private MutableLiveData<File> finishedFile = new MutableLiveData<>();
    private MutableLiveData<List<DataBean>> personList = new MutableLiveData<>();
    private MutableLiveData<List<Drawable>> drawableList = new MutableLiveData<>();
    private MutableLiveData<DataBean> chosedDataBean = new MutableLiveData<>();


    public Bitmap getImage() {
        return image.getValue();
    }

    public void setImage(Bitmap image) {
        this.image.postValue(image);
    }

    public void setCustomImagePath(Uri uri) {
        customImagePath.postValue(uri);
    }

    public Uri getCustomImagePath() {
        return customImagePath.getValue();
    }

    public void setNumberSelected(int i) {
        selectedNumber.postValue(i);
    }

    public Integer getSelectedNumber() {
        return selectedNumber.getValue();
    }

    public void setFinishedImage(File file) {
        finishedFile.postValue(file);
    }

    public File getFinishedFile() {
        return finishedFile.getValue();
    }

    public void setChosedDataBean(Sticker sticker) {
        Log.d(TAG, "setChosedSticker: 改变当前选中的Sticker了");
        List<DataBean> value = personList.getValue();
        for (int i = 0; i < value.size(); i++) {
            DataBean dataBean = value.get(i);
            if (dataBean.sticker.equals(sticker)) {
                chosedDataBean.postValue(dataBean);
                setChosedNumber(i);
                Log.d(TAG, "setChosedSticker: 被选中的databean是第" + i + "个");
            }
        }
    }

    public LiveData<List<DataBean>> getPersonList() {
        if (personList.getValue() == null) {
            personList.postValue(new ArrayList<>());
        }
        return personList;
    }

    public LiveData<DataBean> getChosedDataBean() {
        return chosedDataBean;
    }

    public LiveData<Integer> getChosedNumber() {
        return chosedNumber;
    }

    public void setChosedNumber(int position) {
        chosedNumber.postValue(position);
    }

    public void removeSticker(Sticker currentSticker) {
        Iterator<DataBean> iter = personList.getValue().iterator();
        while (iter.hasNext()) {
            if (currentSticker.equals(iter.next().sticker)) {
                iter.remove();
            }
        }
        personList.postValue(personList.getValue());
    }

    public void addHead(DataBean head) {
        personList.getValue().add(head);
        chosedDataBean.postValue(head);
        personList.postValue(personList.getValue());
    }

    public void setChosedTemplete(int i) {
        DataBean head = chosedDataBean.getValue();
        head.chosen_templete = i;
        Drawable image = ImageProcess.makeSticker(head, head.chosen_templete);
        head.sticker.setDrawable(image);

        chosedDataBean.postValue(chosedDataBean.getValue());
    }

    public void clearPerson() {
        personList.getValue().clear();
        chosedDataBean.setValue(null);
        chosedNumber.setValue(null);
    }

    public LiveData<List<Drawable>> getDrawableList() {
        if (drawableList.getValue() == null) {
            drawableList.setValue(new ArrayList<>());
        }
        return drawableList;
    }

    public void addDrawableList(Drawable image) {
        drawableList.getValue().add(image);
        drawableList.setValue(drawableList.getValue());

    }

    public void setDrawableList(List<Drawable> pictures) {
        drawableList.setValue(pictures);
    }
}
