package com.example.andrewliu.fatbaby.DataBase;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by liut1 on 6/16/16.
 */
public class FileService {
    private Context context;

    public FileService(Context context) {
        super();
        this.context = context;
    }

    /**
     * 保存文件
     * @param fileName 文件名
     * @param fileContent 文件内容
     * */
    public void save(String fileName, String fileContent)throws Exception {
        // IO将文件保存至手机自带空间
        //私有操作模式：创建出来的文件只能被本应用访问，其他应用无法访问该文件，另采用私有操作模式创建的文件，写入文件中的内容会覆盖原有内容
        FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        outStream.write(fileContent.getBytes());//将字符串转成二进制数据
        outStream.close();
    }

    public  String read(String fileName) throws Exception{
        FileInputStream inStream = context.openFileInput(fileName);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inStream.read(buffer)) != -1){
            outStream.write(buffer,0,len);  ///获取buffer数组中从0-len范围的数据，将数据读入内存
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }

}
