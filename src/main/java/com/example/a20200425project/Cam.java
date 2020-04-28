package com.example.a20200425project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Cam extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE=672;
    private String imageFilePath;
    private Uri photoUri;
    TextView view;
    ImageView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        view=findViewById(R.id.view);
        result=findViewById(R.id.result);
        //카메라 사용 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 열어라")
                .setDeniedMessage("안돼 돌아가")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA).check();

        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile=null;
                    try{
                        photoFile = createImageFile();
                    }catch(IOException e){

                    }

                    if(photoFile != null){
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT , photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });


    }

    public void click(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.SUGGEST_COLUMN_RESULT_CARD_IMAGE,R.drawable.a);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
                else{
                    String msg ="no available";
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                }


        }


    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyymmdd_HHmmss").format(new Date());
        String imageFileName = "TEST_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".png",
                storageDir
        );
        imageFilePath=image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exit = null;

            try {
                exit = new ExifInterface(imageFilePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
            int exitOrientation;
            int exitDegree;

            if (exit != null) {
                exitOrientation = exit.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                exitDegree = exitOrientationToDegress(exitOrientation);
            } else {
                exitDegree = 0;
            }
            ((ImageView) findViewById(R.id.result)).setImageBitmap(rotate(bitmap, exitDegree));
        }
    }
    private int exitOrientationToDegress(int exitOrientation){
        if (exitOrientation==ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exitOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exitOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }else {
            return 0;
        }
    }
    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,
                true);
    }
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한 ok", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한 x", Toast.LENGTH_SHORT).show();
        }
    };
}

