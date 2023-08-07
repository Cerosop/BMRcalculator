package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class ModifyPage extends AppCompatActivity {
    Button btnCancelM, btnSendM;
    EditText nameInputM, ageInputM, heightInputM, weightInputM;
    RadioButton maleInputM, femaleInputM;
    String name, age, height, weight, gender, isM, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_page);

        initViewElement();

        Intent it = this.getIntent();
        if(it != null){
            Bundle bundle = it.getExtras();
            if(bundle != null){
                id = bundle.getString("id");
                name = bundle.getString("name");
                if(name != null && !name.equals("")){
                    nameInputM.setText(name);
                }
                age = bundle.getString("age");
                if(age != null && !age.equals("")){
                    ageInputM.setText(age);
                }
                height = bundle.getString("height");
                if(height != null && !height.equals("")){
                    heightInputM.setText(height);
                }
                weight = bundle.getString("weight");
                if(weight != null && !weight.equals("")){
                    weightInputM.setText(weight);
                }
                gender = bundle.getString("gender");
                if(gender != null && !gender.equals("")){
                    if(gender.equals("male")){
                        maleInputM.setChecked(true);
                        femaleInputM.setChecked(false);
                    }
                    else {
                        maleInputM.setChecked(false);
                        femaleInputM.setChecked(true);
                    }
                }
            }
        }

        btnSendM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("isM", "T");
                bundle.putString("name", nameInputM.getText().toString());
                bundle.putString("age", ageInputM.getText().toString());
                bundle.putString("height", heightInputM.getText().toString());
                bundle.putString("weight", weightInputM.getText().toString());
                if(maleInputM.isChecked())
                    bundle.putString("gender", "male");
                else if(femaleInputM.isChecked())
                    bundle.putString("gender", "female");
                else
                    bundle.putString("gender", "no");

                Intent it = new Intent();
                it.putExtras(bundle);
                it.setClass(ModifyPage.this, ResultPage.class);
                startActivity(it);
            }
        });

        btnCancelM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViewElement(){
        btnCancelM = (Button) findViewById(R.id.btn_cancel_c3);
        btnSendM = (Button) findViewById(R.id.btn_send_c3);
        nameInputM = (EditText) findViewById(R.id.nameInput3);
        ageInputM = (EditText) findViewById(R.id.ageInput3);
        heightInputM = (EditText) findViewById(R.id.heightInput3);
        weightInputM = (EditText) findViewById(R.id.weightInput3);
        maleInputM = (RadioButton) findViewById(R.id.maleRatio3);
        femaleInputM = (RadioButton) findViewById(R.id.femaleRatio3);
    }
}