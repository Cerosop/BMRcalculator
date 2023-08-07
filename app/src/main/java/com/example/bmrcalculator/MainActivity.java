package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnCreate;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> items1 = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();
    String name, age, gender, height, weight;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewElement();

        getData();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items1);
        listView.setAdapter(adapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(MainActivity.this, CreatePage.class);
                startActivity(it);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = ids.get(i);
                getDataFromID();
                Bundle bundle = new Bundle();
                bundle.putString("id", Integer.toString(id));
                bundle.putString("name", name);
                bundle.putString("age", age);
                bundle.putString("height", height);
                bundle.putString("weight", weight);
                bundle.putString("gender", gender);

                Intent it = new Intent();
                it.putExtras(bundle);
                it.setClass(MainActivity.this, ModifyPage.class);
                startActivity(it);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //創建訊息方塊

                builder.setMessage("確認要刪除此筆資料嗎？");

                builder.setTitle("刪除確認");

                builder.setPositiveButton("確認", new DialogInterface.OnClickListener()  {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //dismiss為關閉dialog,Activity還會保留dialog的狀態
                        deleteData(ids.get(i));
                        getData();
                        adapter.notifyDataSetChanged();
                    }

                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()  {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();

                return true;
            }
        });
    }

    private void initViewElement(){
        btnCreate = (Button) findViewById(R.id.btn_create);
        listView = (ListView) findViewById(R.id.list1);
    }

    public void getData(){
        ids.clear();
        items1.clear();
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
                    String postData = "todo=read"; // 构建要发送的数据
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    int responseCode = connection.getResponseCode();
                    // 建立取得回應的物件
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                        InputStream inputStream = connection.getInputStream();
                        // 取得輸入串流
                        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                        // 讀取輸入串流的資料
                        String line = null; // 宣告讀取用的字串
                        while((line = bufReader.readLine()) != null) {
                            Log.d("connection", "tt1");
                            JSONArray dataJson = new JSONArray(line);
                            int i = dataJson.length();
                            for(int j = 0; j < i; j++){
                                JSONObject info = dataJson.getJSONObject(j);
                                String idS = info.getString("id");
                                String names = info.getString("name");
                                String bmrs = info.getString("BMR");
                                items1.add(names + "    |    " + bmrs);
                                ids.add(Integer.parseInt(idS));
                            }
                        }
                        inputStream.close(); // 關閉輸入串流
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
        while (thread.isAlive()){}
    }

    public void getDataFromID(){
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
                    String postData = "todo=readid&id="+id; // 构建要发送的数据
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    int responseCode = connection.getResponseCode();
                    // 建立取得回應的物件
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                        InputStream inputStream = connection.getInputStream();
                        // 取得輸入串流
                        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                        // 讀取輸入串流的資料
                        String line = null; // 宣告讀取用的字串
                        if((line = bufReader.readLine()) != null) {
                            JSONArray dataJson = new JSONArray(line);
                            JSONObject info = dataJson.getJSONObject(0);
                            name = info.getString("name");
                            age = info.getString("age");
                            gender = info.getString("gender");
                            weight = info.getString("weight");
                            height = info.getString("height");
                        }
                        inputStream.close(); // 關閉輸入串流
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
        while (thread.isAlive()){}
    }

    public void deleteData(int id){
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
                    String postData = "todo=delete&id="+ id;    // 构建要发送的数据
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