package com.example.a20200425project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.MathContext;
import java.security.PublicKey;

public class Search extends AppCompatActivity {
    Button search,connect;
    EditText insert;
    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search = findViewById(R.id.search);
        connect = findViewById(R.id.connect);
        insert = findViewById(R.id.insert);
        view = findViewById(R.id.view);



    }
    public void click(View v) {
        String str = insert.getText().toString();
        switch (v.getId()) {
            case R.id.search:
                view.setText(str);
                break;
            case R.id.connect:
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,str);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
                else{
                    String msg ="no available";
                    Toast.makeText(this,str,Toast.LENGTH_LONG).show();
                }
                break;

        }



    }

}
