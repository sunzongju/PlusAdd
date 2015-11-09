package com.wrmoney.administrator.plusadd.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/10/8.
 */
public class CutBitmap {

    public static Bitmap cutImage(Bitmap bitmap) {
        // 裁剪图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        BitmapFactory
//                .decodeResource(context.getResources(), bitmap, options);
        //Log.d(TAG, "original outwidth: " + options.outWidth);
        // �˿����Ŀ��ImageViewϣ��Ĵ�С��������Զ���ImageView��Ȼ����ImageView�Ŀ�ȡ�
        int dstWidth = 150;
//        // ������Ҫ���ص�ͼƬ���ܴܺ������ȶ�ԭ�е�ͼƬ���вü�
        //      int sampleSize = calculateInSampleSize(options, dstWidth, dstWidth);
//        options.inSampleSize = sampleSize;
//        options.inJustDecodeBounds = false;
//        //Log.d(TAG, "sample size: " + sampleSize);
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
//                R.drawable.avatar, options);

        // ����ͼƬ
        Bitmap resultBmp = Bitmap.createBitmap(dstWidth, dstWidth,
                Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(resultBmp);
        // ��Բ
        canvas.drawCircle(dstWidth / 2, dstWidth / 2, dstWidth / 2, paint);
        // ѡ�񽻼�ȥ�ϲ�ͼƬ
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth()),
                new Rect(0, 0, dstWidth, dstWidth), paint);
//        mImg.setImageBitmap(resultBmp);
//        bitmap.recycle();
        return resultBmp;
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
