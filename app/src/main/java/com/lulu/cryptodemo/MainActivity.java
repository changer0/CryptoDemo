package com.lulu.cryptodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
}
