package com.jml.example.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by jose on 18/01/2014.
 *
 * @author jose
 * @version 0.1.0
 * @since 1
 */
public class AndroidVolleySingleton {

    private static String TAG_DEBUG  ="AndroidVolleySingleton";


    public static final int IO_BUFFER_SIZE = Integer.MAX_VALUE;

    /**
     *  Application Version, you can define it by default otherwise you can
     * get it from Android Manifest
     */
    private static int APP_VERSION = 1;

    /**
     * There has to be only an instance of this class, that´s why we
     * use singleton pattern
     */
    private static AndroidVolleySingleton mInstance = null;


    private RequestQueue mRequestQueue;

    /** ImageLoader used to ask for pictures */
    private ImageLoader mImageLoader;

    /** SimpleDiskCache is an easy class to work with
     * JackeWharton DiskLruCache:
     * https://github.com/JakeWharton/DiskLruCache
     */
    private SimpleDiskCache simpleDiskCache;

    private AndroidVolleySingleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context);

        try{

            simpleDiskCache = SimpleDiskCache.open(context.getCacheDir(), APP_VERSION, IO_BUFFER_SIZE );

            mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {

            public void putBitmap(String url, Bitmap bitmap) {
                try{
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapData = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapData);
                    simpleDiskCache.put(url, bs);
                }catch (Exception e){
                    Log.d(TAG_DEBUG, e.getMessage());
                }
            }
            public Bitmap getBitmap(String url) {
                try{
                    SimpleDiskCache.BitmapEntry bitmapEntry;
                    if( ( bitmapEntry = simpleDiskCache.getBitmap(url) ) != null){
                        return bitmapEntry.getBitmap();
                    }else{
                        return null;
                    }
                }catch (Exception e){
                    Log.d(TAG_DEBUG, "Error" + e.getMessage());
                    return null;
                }
            }
        });

        }catch (Exception ex){
            Log.d(TAG_DEBUG, ex.getMessage());
            //DiskBitmapCache is a class that use only the DiskCache.
            // Just in case there were problems with
            // DiskLruCache we could use this simple cache disk as a backup.
            mImageLoader = new ImageLoader(this.mRequestQueue, new DiskBitmapCache(context.getCacheDir()) );
        }
    }

    public static AndroidVolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new AndroidVolleySingleton(context);
        }
        return mInstance;
    }

    /** Cancel all request. It´s bit extremist this call but really effective */
    public  void cancelAllRequest(){
        getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override public boolean apply(Request<?> request) {
                Log.d("DEBUG", "request running: " + request.getTag().toString());
                return true;
            }
        });
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }
}
