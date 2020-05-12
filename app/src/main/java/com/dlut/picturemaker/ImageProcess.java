package com.dlut.picturemaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.baidu.aip.util.Base64Util;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dlut.picturemaker.data.DataBean;
import com.dlut.picturemaker.entity.KeyPointEntity;
import com.dlut.picturemaker.entity.SegmentEntity;
import com.dlut.picturemaker.fragment.SecondFragment;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ImageProcess {

    //设置APPID/AK/SK
    public static final String APP_ID = "19731937";
    public static final String API_KEY = "B1eGXMLrihpbIpSpoOA2g1AG";
    public static final String SECRET_KEY = "EeKpFI417lEtgpi2gxb8Mcf0PMW15fY2";
    private static final String TAG = "Process";

    static AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;

    }

    public static void getHeads(SecondFragment secondFragment, String path) {
        Log.d(TAG, "getHeads: path is " + path);
        Bitmap image = ImageUtils.getBitmap(path, 1024, 1024);
        String newPath = PathUtils.getExternalAppDataPath() + File.separator + TimeUtils.getNowString() + ".jpg";
        ImageUtils.save(image, newPath, Bitmap.CompressFormat.JPEG);
        ThreadUtils.executeBySingle(new HeadTask(secondFragment, newPath));
    }

    public static Drawable makeSticker(DataBean headData, int drawable) {
        Mat head = new Mat();
        Utils.bitmapToMat(headData.headBitmap, head);
        Mat body = new Mat();
        int BODY_HEAD_X;
        int BODY_HEAD_Y;
        int BODY_NECK_X;
        int BODY_NECK_Y;
        switch (drawable) {
            case 0:
                try {
                    body = Utils.loadResource(mContext, R.drawable.muban1);
                    Imgproc.cvtColor(body, body, Imgproc.COLOR_BGRA2RGBA);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                BODY_HEAD_X = 160;
                BODY_HEAD_Y = 70;
                BODY_NECK_X = 155;
                BODY_NECK_Y = 245;
                break;
            case 1:
                try {
                    body = Utils.loadResource(mContext, R.drawable.muban2);
                    Imgproc.cvtColor(body, body, Imgproc.COLOR_BGRA2RGBA);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                BODY_HEAD_X = 155;
                BODY_HEAD_Y = 75;
                BODY_NECK_X = 155;
                BODY_NECK_Y = 250;
                break;
            case 2:
                try {
                    body = Utils.loadResource(mContext, R.drawable.muban3);
                    Imgproc.cvtColor(body, body, Imgproc.COLOR_BGRA2RGBA);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                BODY_HEAD_X = 150;
                BODY_HEAD_Y = 45;
                BODY_NECK_X = 150;
                BODY_NECK_Y = 220;
                break;
            default:
                throw new IllegalArgumentException("Wrong Body Templete!");
        }
        int headHeight = head.rows();
        int headWeight = head.cols();
        int bodyHeight = body.rows();
        int bodyWeight = body.cols();
        double HEIGHT = BODY_NECK_Y - BODY_HEAD_Y;
        double SCALE = HEIGHT / headHeight;
        double WIDTH = headWeight * SCALE;

        //  放缩头部
        Mat headResize = new Mat();
        Imgproc.resize(head, headResize, new Size(WIDTH, HEIGHT));

        //  平移
        double dx = (BODY_NECK_X - (headData.neckX - headData.xmin) * SCALE);
        double dy = (BODY_NECK_Y - (headData.ymax - headData.ymin) * SCALE);
        Mat headTrans = new Mat();

        Mat matTranslation = new Mat(2, 3, CvType.CV_32F);
        matTranslation.put(0, 0, 1);
        matTranslation.put(0, 1, 0);
        matTranslation.put(0, 2, dx);
        matTranslation.put(1, 0, 0);
        matTranslation.put(1, 1, 1);
        matTranslation.put(1, 2, dy);
        Imgproc.warpAffine(headResize, headTrans, matTranslation, new Size(bodyWeight, bodyHeight));

        //  两张图片融合

        List<Mat> bodyMatChannels = new ArrayList<Mat>();// 分离透明通道数据
        Core.split(body, bodyMatChannels);
        Mat bodyAlpha = new Mat();
        Core.divide(bodyMatChannels.get(3), new Scalar(255.0), bodyAlpha);

        List<Mat> headMatChannels = new ArrayList<Mat>();// 分离出来的彩色通道数据
        Core.split(headTrans, headMatChannels);
        for (int i = 0; i < headMatChannels.size(); i++) {
            Mat headChannel = headMatChannels.get(i);
            Mat tempMat = new Mat();
            Core.absdiff(bodyAlpha, new Scalar(1.0), tempMat);
            Mat temp2 = new Mat();
            Core.multiply(bodyAlpha, bodyMatChannels.get(i), temp2);
            Mat temp3 = new Mat();
            Core.multiply(headChannel, tempMat, temp3);
            Core.add(temp2, temp3, headChannel);
        }
        Mat outputMat = new Mat();
        Core.merge(headMatChannels, outputMat);

        Bitmap outputBitmap = Bitmap.createBitmap(outputMat.cols(), outputMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(outputMat, outputBitmap);
        Log.d(TAG, "makeSticker: 合并完成");


        return ImageUtils.bitmap2Drawable(outputBitmap);

    }

    static class HeadTask extends ThreadUtils.SimpleTask<List<DataBean>> {
        String path;
        SecondFragment secondFragment;

        @Override
        public void onFail(Throwable t) {
            secondFragment.gotHeads(null);
            CrashReport.postCatchedException(t);
            secondFragment.showWhatWrong(t.getMessage());
            super.onFail(t);

        }

        public HeadTask(SecondFragment secondFragment, String Path) {
            setTimeout(10000, new OnTimeoutListener() {
                @Override
                public void onTimeout() {
                    secondFragment.gotHeads(null);
                }
            });
            this.path = Path;
            this.secondFragment = secondFragment;
        }

        @Override
        public List<DataBean> doInBackground() throws Throwable {
            return segment(path);
        }

        @Override
        public void onSuccess(List<DataBean> result) {
            Log.d(TAG, "onSuccess: got " + result.size() + " heads.");
            secondFragment.gotHeads(result);
        }
    }


    public static List<DataBean> keyPoint(byte[] bytes) {
        JSONObject res = client.bodyAnalysis(bytes, null);
        KeyPointEntity result = JSON.parseObject(res.toString(), KeyPointEntity.class);

        Bitmap originImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String originPath = PathUtils.getExternalAppDataPath() + File.separator +
                TimeUtils.getNowString() + "_foreground.png";
        ImageUtils.save(originImage, originPath, Bitmap.CompressFormat.PNG);
        List<DataBean> heads = new ArrayList<>();
        LogUtils.dTag(TAG, res);
        int number = result.getPersonNum();
        Log.d(TAG, "keyPoint: 得到了" + number + "个人头");
        for (int i = 0; i < number; i++) {
            Log.d(TAG, "keyPoint: 正在处理第" + i + "个人头");
            KeyPointEntity.PersonInfoEntity info = result.getPersonInfo().get(i);
            long x1 = Math.round(info.getBodyParts().getRightShoulder().getX());
            long y1 = Math.round(info.getBodyParts().getRightMouthCorner().getY());
            long x2 = Math.round(info.getBodyParts().getNeck().getX());
            long y2 = Math.round(info.getBodyParts().getNeck().getY());
            long x3 = Math.round(info.getBodyParts().getLeftShoulder().getX());
            long y3 = Math.round(info.getBodyParts().getLeftMouthCorner().getY());
            long x4 = Math.round(info.getBodyParts().getLeftShoulder().getX());
            long y4 = Math.round(info.getBodyParts().getTopHead().getY());
            long x5 = Math.round(info.getBodyParts().getTopHead().getX());
            long y5 = Math.round(info.getLocation().getTop());
            long x6 = Math.round(info.getBodyParts().getRightShoulder().getX());
            long y6 = Math.round(info.getBodyParts().getTopHead().getY());

            long xmax = max(x1, x2, x3, x4, x5, x6);
            long xmin = min(x1, x2, x3, x4, x5, x6);
            long ymax = max(y1, y2, y3, y4, y5, y6);
            long ymin = min(y1, y2, y3, y4, y5, y6);

            Point pt1 = new Point(x1, y1);
            Point pt2 = new Point(x2, y2);
            Point pt3 = new Point(x3, y3);
            Point pt4 = new Point(x4, y4);
            Point pt5 = new Point(x5, y5);
            Point pt6 = new Point(x6, y6);
            Mat pts = new MatOfPoint(pt1, pt2, pt3, pt4, pt5, pt6);
            // Crop Image
            Mat originMat = new Mat();
            Utils.bitmapToMat(originImage, originMat);
            Rect rect = Imgproc.boundingRect(pts);
            if (rect.x + rect.width > originImage.getWidth()) {
                rect.width -= 1;
            }
            if (rect.y + rect.height > originImage.getHeight()) {
                rect.height -= 1;
            }
            Mat croppedMat = originMat.submat(rect);

            Point movedPt1 = new Point(x1 - xmin, y1 - ymin);
            Point movedPt2 = new Point(x2 - xmin, y2 - ymin);
            Point movedPt3 = new Point(x3 - xmin, y3 - ymin);
            Point movedPt4 = new Point(x4 - xmin, y4 - ymin);
            Point movedPt5 = new Point(x5 - xmin, y5 - ymin);
            Point movedPt6 = new Point(x6 - xmin, y6 - ymin);
            Mat movedPts = new MatOfPoint(movedPt1, movedPt2, movedPt3, movedPt4, movedPt5, movedPt6);

            Mat mask = Mat.zeros(croppedMat.rows(), croppedMat.cols(), CvType.CV_8U);
            List<MatOfPoint> contours = new ArrayList<>();
            contours.add(new MatOfPoint(movedPts));
            Imgproc.drawContours(mask, contours, -1, new Scalar(255, 255, 255), -1, Imgproc.LINE_AA);
            Mat afterPolyMat = new Mat();
            Core.bitwise_and(croppedMat, croppedMat, afterPolyMat, mask);
            String path = PathUtils.getExternalAppDataPath() + File.separator +
                    TimeUtils.getNowString() + "_head.png";
            Imgcodecs.imwrite(path, afterPolyMat);
            Bitmap outputBitmap = Bitmap.createBitmap(afterPolyMat.cols(), afterPolyMat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(afterPolyMat, outputBitmap, true);
            DataBean bean = new DataBean();
            bean.neckX = x2;
            bean.xmax = xmax;
            bean.xmin = xmin;
            bean.ymax = ymax;
            bean.ymin = ymin;
            bean.headPath = path;
            bean.headBitmap = outputBitmap;
            heads.add(bean);
            Log.d(TAG, "keyPoint: 处理完成！此时HeadList里有" + heads.size() + "组数据");
        }
        return heads;


    }

    private static long max(long... vars) {
        long max = vars[0];
        for (long var : vars) {
            if (max < var) {
                max = var;
            }
        }
        return max;
    }

    private static long min(long... vars) {
        long min = vars[0];
        for (long var : vars) {
            if (min > var) {
                min = var;
            }
        }
        return min;
    }

    public static List<DataBean> segment(String path) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", "foreground");


        // 参数为本地路径
        JSONObject res = client.bodySeg(path, options);
        SegmentEntity result = JSON.parseObject(res.toString(), SegmentEntity.class);
        byte[] foreground = Base64Util.decode(result.getForeground());

        return keyPoint(foreground);
    }
}
