package com.example.a20200425project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.CopyOnWriteArraySet;

public class menu extends AppCompatActivity {
    Button picture, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        picture=findViewById(R.id.picture);
        search=findViewById(R.id.search);
    }
    public void click(View v){
        Intent pic,sea;
        pic=new Intent(getApplicationContext(),Cam.class);
        sea=new Intent(getApplicationContext(),Search.class);
        switch (v.getId()){
            case R.id.picture:
                startActivity(pic);
                break;
            case R.id.search:
                startActivity(sea);
                break;
        }
    }
}
