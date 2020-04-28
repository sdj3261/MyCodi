package com.example.a20200425project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AppLaunchChecker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inspector.StaticInspectionCompanionProvider;
import android.widget.TextView;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView view,name;
    private Handler handler = new Handler();
    String[] str={"우아함은 눈에 띄지 않지만, 기억에는 남게 된다.",
            "여성은 가방을 들수 있지만, 여성을 들어올려주는 건 그녀가 신고 있는 신발이다.",
            "덜 사라. 그리고 잘 골라라",
            "패선은 변하지만 스타일은 그대로 남는다.",
            "패션은 변하지만 스타일은 영원하다.","우아함이란 제거하는 것이다",
            "패션은 구속이 아닌 현실도피의 형태이어야 한다."};
    String[] str_name={"-조르지오 아르마니-","-크리스찬 루부탱-","-비비안 웨스트우드-",
            "-코코샤넬-","이브 생 로랑","크리스토발 발렌시아가","알렉산더 맥퀸"};
    int count=0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(count==str.length){
                count=0;
            }
            view.setText(str[count]);
            name.setText(str_name[count]);
            handler.postDelayed(this, 3000);
            count++;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=findViewById(R.id.view);
        name=findViewById(R.id.name);
        Intent intent = new Intent(this, Spalsh.class);
        startActivity(intent);
        handler.postDelayed(runnable,1000);
        handler.post(runnable);

    }
    public void cam(View v){
        Intent intent = new Intent(getApplicationContext(), menu.class);
        startActivity(intent);
    }

}
