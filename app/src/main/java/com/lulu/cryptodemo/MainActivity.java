package com.lulu.cryptodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText mTextContent;
    private TextView mTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextContent = (EditText) findViewById(R.id.main_et_content);
        mTextResult = (TextView) findViewById(R.id.main_tv_result);

    }

    public void btnBase64Encode(View view) {
        String str = mTextContent.getText().toString().trim();
        // Base64 编码, NO_WRAP 代表编码的结果没有任何换行
        String encoded = Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        mTextResult.setText(encoded);


    }


    public void btnBase64Decode(View view) {
        String str = mTextResult.getText().toString().trim();
        byte[] decode = Base64.decode(str, Base64.NO_WRAP);
        mTextResult.setText(new String(decode));
    }

    public void btnURLEncoding(View view) {

        // URLEncoder 编码内容为可以在网址上拼接的字符串
        try {
            String str = URLEncoder.encode(mTextContent.getText().toString().trim(), "UTF-8");
            mTextResult.setText(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void btnURLDecoding(View view) {
       String url = mTextResult.getText().toString().trim();
        try {
            String decode = URLDecoder.decode(url, "UTF-8");
            mTextResult.setText(decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * GZIP压缩 解压缩
     *
     * @param view
     */
    public void btnGzipTest(View view) {
        String str = mTextContent.getText().toString().trim();


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 1. 压缩GZIP, GZIPOutputStream

            GZIPOutputStream gzOut = new GZIPOutputStream(bos);
            gzOut.write(str.getBytes());//利用GZIPOutputStream压缩并且输出结果
            gzOut.finish();// 在接结束时必须调用, 生成实际的压缩数据
            gzOut.close();

            byte[] data = bos.toByteArray();
            bos.close();
            mTextResult.setText("内容长度: " + str.getBytes().length+"\n压缩大小 : " + data.length);

            Log.d("GZIP压缩", "btnGzipTest: " + Arrays.toString(data));


            // 2. 解压缩 GZIPInputStream
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzIn = new GZIPInputStream(bis);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = gzIn.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            Log.d("GZIP解压缩", "btnGzipTest: " + bos.toString("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
