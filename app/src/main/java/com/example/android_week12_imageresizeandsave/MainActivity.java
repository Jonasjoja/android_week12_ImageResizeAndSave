package com.example.android_week12_imageresizeandsave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.android_week12_imageresizeandsave.Controller.ImageController;

public class MainActivity extends AppCompatActivity {

    public ImageView imageView;
    private ImageController ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        ic = new ImageController(this);
    }

    public void photoRollBtnPressed(View view){
        Intent intent = new Intent(Intent.ACTION_PICK); // Creating an intent
        intent.setType("image/*"); //Looking for images only

        // startactivity will make make android launch an activtiy which can handle the request
        startActivityForResult(intent, 0); // 0 means photoroll, 1 means camera
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Checks if result is OK. If not, then return
        if(resultCode != -1) return; // If its not -1, return, otherwise call handleImageReturn from imgController
        ic.handleImageReturn(requestCode, intent);
    }



    public void cameraBtnPressed(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //Action to capture an image
        startActivityForResult(intent, 1); //Starts the activity but with 1, so it's from camera.
    }

    public void resizeImageBtnPressed (View view) {
        imageView.destroyDrawingCache(); //Have to destroy cache to avoid it loading previous picture again
        imageView.setDrawingCacheEnabled(true); //Depricated methods, but enables caching
        Bitmap bitmap = imageView.getDrawingCache(); //Turns the imageview into a bitmap
        ic.resizeImage(bitmap); //Calls method from imageController to resize
    }

    public void saveToCameraRollBtn(View view){
        imageView.destroyDrawingCache(); //Have to destroy cache to avoid it loading previous picture again
        imageView.setDrawingCacheEnabled(true); //Depricated methods, but enables caching
        Bitmap bitmap = imageView.getDrawingCache(); //Turns the imageview into a bitmap
        MediaStore.Images.Media.insertImage(getContentResolver(),bitmap, "img.png", "yourDescription");
    }

}

