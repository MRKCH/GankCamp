package com.cooke.gankcamp.util;

import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kch on 2017/12/6.
 */

public class FileUtil {

    /**
     * 判断是否有内存卡
     * @return
     */
    public static boolean existSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 获取内置SD卡路径
     * @return
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取外置内存卡根目录
     * @return
     */
    public static String getExternalSDCardPath(){
        if (existSDCard()){
            return  Environment.getExternalStorageDirectory().toString();
        }else
            return  null;
    }

    public static boolean saveImage(String path, InputStream inputStream){
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSizeDownloaded = 0;
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
