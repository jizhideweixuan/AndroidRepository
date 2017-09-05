package com.example.administrator.ziptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView hasSDTextView;
    private TextView SDPathTextView;
    private TextView FILESpathTextView;
    private TextView createFileTextView;
    private TextView readFileTextView;
    private TextView deleteFileTextView;
    private TextView doZipTextView;
    private TextView unZipTextView;
    private FileHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasSDTextView = (TextView) findViewById(R.id.hasSDTextView);
        SDPathTextView = (TextView) findViewById(R.id.SDPathTextView);
        FILESpathTextView = (TextView) findViewById(R.id.FILESpathTextView);
        createFileTextView = (TextView) findViewById(R.id.createFileTextView);
        readFileTextView = (TextView) findViewById(R.id.readFileTextView);
        doZipTextView = (TextView) findViewById(R.id.doZipTextView);
        unZipTextView = (TextView) findViewById(R.id.unZipTextView);
        deleteFileTextView = (TextView) findViewById(R.id.deleteFileTextView);
        helper = new FileHelper(getApplicationContext());
        hasSDTextView.setText("SD卡是否存在:" + helper.hasSD());
        SDPathTextView.setText("SD卡路径:" + helper.getSDPATH());
        FILESpathTextView.setText("包路径:" + helper.getFILESPATH());
        try {
            createFileTextView.setText("创建文件："
                    + helper.createSDFile("test.txt").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.writeSDFile("1213212", "test.txt");
        readFileTextView.setText("读取文件:" + helper.readSDFile("test.txt"));
        doZipTextView.setText("压缩文件:"+helper.encrypt(helper.getSDPATH()+"/test.txt","123456"));
        unZipTextView.setText("解压文件:"+helper.unencrypt(helper.getSDPATH()+"/test.zip",helper.getSDPATH()+"/output","123456"));
        deleteFileTextView.setText("删除文件是否成功:"
                + helper.deleteSDFile("test.txt"));
    }
}
