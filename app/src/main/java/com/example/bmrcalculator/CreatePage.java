package com.example.bmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CreatePage extends AppCompatActivity {
    Button btnCancel, btnSend;
    EditText nameInput, ageInput, heightInput, weightInput;
    RadioButton maleInput, femaleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_page);

        initViewElement();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("isM", "F");
                bundle.putString("name", nameInput.getText().toString());
                bundle.putString("age", ageInput.getText().toString());
                bundle.putString("height", heightInput.getText().toString());
                bundle.putString("weight", weightInput.getText().toString());
                if(maleInput.isChecked())
                    bundle.putString("gender", "male");
                else if(femaleInput.isChecked())
                    bundle.putString("gender", "female");
                else
                    bundle.putString("gender", "no");

                Intent it = new Intent();
                it.putExtras(bundle);
                it.setClass(CreatePage.this, ResultPage.class);
                startActivity(it);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(CreatePage.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    private void initViewElement(){
        btnCancel = (Button) findViewById(R.id.btn_cancel_c);
        btnSend = (Button) findViewById(R.id.btn_send_c);
        nameInput = (EditText) findViewById(R.id.nameInput);
        ageInput = (EditText) findViewById(R.id.ageInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        weightInput = (EditText) findViewById(R.id.weightInput);
        maleInput = (RadioButton) findViewById(R.id.maleRatio);
        femaleInput = (RadioButton) findViewById(R.id.femaleRatio);
    }
}