package com.jml.example.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * Created by jose on 18/01/2014.
 *
 * @author jose
 * @version 0.1.0
 * @since  1
 */
public class DiskBitmapCache extends DiskBasedCache implements ImageLoader.ImageCache {


    private File rootDirectory;

    public DiskBitmapCache(File rootDirectory, int maxCacheSizeInBytes) {
        super(rootDirectory, maxCacheSizeInBytes);
        this.rootDirectory = rootDirectory;
    }

    public DiskBitmapCache(File cacheDir) {
        super(cacheDir);
        this.rootDirectory = cacheDir;
    }

    public Bitmap getBitmap(String url) {

        final String volleyFileName = getFilenameForKey(url);

        if (getCacheDir().listFiles() != null)
            for (File file : getCacheDir().listFiles()) {
                if (file.getName().equals(volleyFileName))
                    return BitmapFactory.decodeFile(file.getName());
            }
        return null;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        final Entry entry = new Entry();

//        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount()); //Valid API 12+
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
        bitmap.copyPixelsToBuffer(buffer);
        entry.data = buffer.array();

        put(url, entry);
    }

    // Volley creates a filename for the url with the following function, so we'll use the same function
    // for translating the url back to said filename
    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        return localFilename;
    }

    private File getCacheDir() {
        return this.rootDirectory;
    }
}

