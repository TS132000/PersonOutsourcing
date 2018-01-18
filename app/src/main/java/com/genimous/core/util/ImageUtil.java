//package com.genimous.core.util;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.net.Uri;
//import android.widget.ImageView;
//
//import com.bumptech.glide.RequestManager;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
//import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
//import com.bumptech.glide.load.resource.bitmap.CenterCrop;
//import com.supermarketloan.R;
//
//
//public class ImageUtil {
//
//    public static void setBannerViewURI(RequestManager requestManager, ImageView imageView, String strImg) {
//        if (!TextUtil.isEmptyString(strImg)) {
//            requestManager.load(Uri.parse(strImg)).placeholder(R.color.c6).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//        } else {
//            requestManager.load("").placeholder(R.color.c6).into(imageView);
//        }
//    }
//
//    public static void setGalleryViewURI(RequestManager requestManager, ImageView imageView, String strImg) {
//        if (!TextUtil.isEmptyString(strImg)) {
//            requestManager.load(Uri.parse(strImg)).placeholder(R.drawable.home_assistant).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//        } else {
//            requestManager.load("").placeholder(R.drawable.home_assistant).into(imageView);
//        }
//    }
//
//    public static void setGlideDrawViewURI(RequestManager requestManager, Context context, final ImageView imageView, String strImg) {
//        if (!TextUtil.isEmptyString(strImg)) {
////            requestManager.load(Uri.parse(strImg)).placeholder(R.drawable.bg_loan_icon).transform(new GlideRoundTransform(context, 8)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//            requestManager.load(Uri.parse(strImg)).placeholder(R.drawable.bg_loan_icon).bitmapTransform(new CenterCrop(context), new GlideRoundTransform(context, 8)).into(imageView);
//        } else {
//            requestManager.load("").placeholder(R.drawable.bg_loan_icon).transform(new GlideRoundTransform(context, 8)).into(imageView);
//        }
//    }
//
//    public static void setGlideCircleViewURI(RequestManager requestManager, Context context, ImageView imageView, String strImg) {
//        if (!TextUtil.isEmptyString(strImg)) {
//            requestManager.load(Uri.parse(strImg)).placeholder(R.drawable.home_creditcard).transform(new GlideCircleTransform(context)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//        } else {
//            requestManager.load("").placeholder(R.drawable.home_creditcard).transform(new GlideCircleTransform(context)).into(imageView);
//        }
//    }
//
//    public static class GlideCircleTransform extends BitmapTransformation {
//
//        private Paint mBorderPaint;
//        private float mBorderWidth;
//
//        public GlideCircleTransform(Context context) {
//            super(context);
//        }
//
//        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
//            super(context);
//            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
//
//            mBorderPaint = new Paint();
//            mBorderPaint.setDither(true);
//            mBorderPaint.setAntiAlias(true);
//            mBorderPaint.setColor(borderColor);
//            mBorderPaint.setStyle(Paint.Style.STROKE);
//            mBorderPaint.setStrokeWidth(0.1f);
//        }
//
//
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return circleCrop(pool, toTransform);
//        }
//
//        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
//            if (source == null) {
//                return null;
//            }
//            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//            if (result == null) {
//                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//            }
//            Canvas canvas = new Canvas(result);
//            Paint paint = new Paint();
//            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//            paint.setAntiAlias(true);
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//            if (mBorderPaint != null) {
//                float borderRadius = r - mBorderWidth / 2;
//                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
//            }
//            return result;
//        }
//
//        @Override
//        public String getId() {
//            return getClass().getName();
//        }
//    }
//
//    public static class GlideRoundTransform extends BitmapTransformation {
//
//        private static float radius = 0f;
//        private int mBorderWidth;
//        private static Paint mBorderPaint;
//
//        public GlideRoundTransform(Context context) {
//            this(context, 4);
//        }
//
//        public GlideRoundTransform(Context context, int dp) {
//            super(context);
//            this.radius = DimenUtils.dp2px(context, dp);
//            mBorderWidth = (int) (Resources.getSystem().getDisplayMetrics().density * 0.5f);
//            mBorderPaint = new Paint();
//            mBorderPaint.setDither(true);
//            mBorderPaint.setAntiAlias(true);
//            mBorderPaint.setColor(context.getResources().getColor(R.color.c5));
//            mBorderPaint.setStyle(Paint.Style.STROKE);
//            mBorderPaint.setStrokeWidth(mBorderWidth);
//        }
//
//        @Override
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return roundCrop(pool, toTransform);
//        }
//
//        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
//            if (source == null) {
//                return null;
//            }
//
//            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//            if (result == null) {
//                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//            }
//
//            Canvas canvas = new Canvas(result);
//            // 底部
//            canvas.drawRoundRect(new RectF(0f, 0f, source.getWidth(), source.getHeight()), radius, radius, mBorderPaint);
//
//            Paint paint = new Paint();
//            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//            paint.setAntiAlias(true);
//            canvas.drawRoundRect(new RectF(mBorderWidth / 2, mBorderWidth / 2, source.getWidth() - mBorderWidth / 2, source.getHeight() - mBorderWidth / 2), radius, radius, paint);
//            return result;
//        }
//
//        @Override
//        public String getId() {
//            return getClass().getName() + Math.round(radius);
//        }
//    }
//}
