package com.example.oblig1quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        Button selectPhoto = findViewById(R.id.selectPhoto);
        Button save = findViewById(R.id.saveButton);
        View description = findViewById(R.id.textInputEditText);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(i, 0);
              }
          }

        );

        save.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if (description == null) {
                        Toast.makeText(getApplicationContext(), "Fill out description", Toast.LENGTH_LONG).show();
                    }
               }
           }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if(requestCode==0){
            if(resultCode==RESULT_OK && null!=result){

            }
        }
    }
}