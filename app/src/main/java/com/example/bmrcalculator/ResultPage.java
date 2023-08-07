package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResultPage extends AppCompatActivity {
    Button btnCancel, btnSave;
    TextView nameT, BMIT, BMRT;
    String name, age, height, weight, gender, isM;
    boolean isMB;
    double BMI, BMR;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        initViewElement();

        Intent it = this.getIntent();
        if(it != null){
            Bundle bundle = it.getExtras();
            if(bundle != null){
                name = bundle.getString("name");
                if(name != null && !name.equals("")){
                    nameT.setText(name);
                }
                isM = bundle.getString("isM");
                if(isM != null && !isM.equals("")){
                    if(isM.equals("T")){
                        isMB = true;
                        id = Integer.parseInt(bundle.getString("id"));
                    }
                    else
                        isMB = false;
                }
                age = bundle.getString("age");
                height = bundle.getString("height");
                weight = bundle.getString("weight");
                gender = bundle.getString("gender");

                double h = Double.parseDouble(height), w = Double.parseDouble(weight), a = Double.parseDouble(age);
                BMI = w / (h / 100) / (h / 100);
                if(gender.equals("male"))
                    BMR = 66 + 13.7 * w + 5 * h - 6.8 * a;
                else
                    BMR = 655 + 9.6 * w + 1.8 * h - 4.7 * a;
                BMIT.setText(Double.toString(BMI));
                BMRT.setText(Double.toString(BMR));
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMB){
                    updateData();
                }
                else {
                    addData();
                }
                Intent it = new Intent();
                it.setClass(ResultPage.this, MainActivity.class);
                startActivity(it);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(ResultPage.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    private void initViewElement(){
        btnCancel = (Button) findViewById(R.id.btn_cancel_r);
        btnSave = (Button) findViewById(R.id.btn_save_r);
        nameT = (TextView) findViewById(R.id.nameText);
        BMIT = (TextView) findViewById(R.id.BMIText);
        BMRT = (TextView) findViewById(R.id.BMRText);
    }

    public void updateData(){
        Log.d("connection", "80");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.101/GetAllData.php");
                    // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 建立 Google 比較挺的 HttpURLConnection 物件
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept-Charset", "UTF-8");
                    // 設定連線方式為 POST
                    connection.setDoOutput(true); // 允許輸出
                    connection.setDoInput(true); // 允許讀入
                    connection.setUseCaches(false); // 不使用快取
                    String postData = "todo=update&id="+ id+"&name="+name+"&age="+age+"&gender="+gender+"&BMR="+ BMR +"&height="+height+"&weight="+weight;    // 构建要发送的数据
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    int responseCode = connection.getResponseCode();
                    // 建立取得回應的物件
                    if(responseCode != HttpURLConnection.HTTP_OK){
                        Log.d("connection", "error");
                    }
                    connection.disconnect();
                    // 讀取輸入串流並存到字串的部分
                    // 取得資料後想用不同的格式
                    // 例如 Json 等等，都是在這一段做處理

                } catch(Exception e) {
                    Log.d("connection", e.toString());
                }
            }
        });
        thread.start();
        while(thread.isAlive()){}
    }

    public void addData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.101/GetAllData.php");
                    // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 建立 Google 比較挺的 HttpURLConnection 物件
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept-Charset", "UTF-8");
                    // 設定連線方式為 POST
                    connection.setDoOutput(true); // 允許輸出
                    connection.setDoInput(true); // 允許讀入
                    connection.setUseCaches(false); // 不使用快取
                    String postData = "todo=add&name="+name+"&age="+age+"&gender="+gender+"&BMR="+ BMR +"&height="+height+"&weight="+weight;    // 构建要发送的数据
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    int responseCode = connection.getResponseCode();
                    // 建立取得回應的物件
                    if(responseCode != HttpURLConnection.HTTP_OK){
                        Log.d("connection", "error");
                    }
                    connection.disconnect();
                    // 讀取輸入串流並存到字串的部分
                    // 取得資料後想用不同的格式
                    // 例如 Json 等等，都是在這一段做處理

                } catch(Exception e) {
                    Log.d("connection", e.toString());
                }
            }
        });
        thread.start();
        while(thread.isAlive()){}
    }
}