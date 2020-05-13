package com.dlut.picturemaker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.UriUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MyUtils {
    public static Uri saveToDcim(Bitmap bitmap, String filename, Context context) {
        Uri uri;
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, filename);
        //兼容Android Q和以下版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
            //RELATIVE_PATH是相对路径不是绝对路径
            //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/DUT云毕业照");

        } else {
            contentValues.put(MediaStore.Images.Media.DATA, context.getExternalFilesDir("Pictures").getAbsolutePath() +File.separator+ filename + ".JPEG");
        }
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream outputStream = null;
        try {
            outputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(context, "保存成功",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return uri;
    }

    public static String saveBitmap(Bitmap bitmap, String filename, Context context) {

        String extStorageDirectory = context.getExternalFilesDir("heads").getAbsolutePath();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, filename+".JPEG");//创建文件，第一个参数为路径，第二个参数为文件名
        Uri uri;
        try {
            outStream = new FileOutputStream(file);//创建输入流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();
            // 实现相册更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            if (Build.VERSION.SDK_INT >= 24) {
                uri = UriUtils.file2Uri(file);
                        // FileProvider.getUriForFile(context, "com.dlut.picturemaker.fileprovider", file);
            } else {
                //适配Android N版本之前
                uri = Uri.fromFile(file);
            }
            Toast.makeText(context,"保存成功",
                    Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(context, "保存失败 exception:" + e,
                    Toast.LENGTH_SHORT).show();
            Log.d("RecognizeUtil", e.toString());
            return null;
        }

        Log.d("Util", " "+file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
