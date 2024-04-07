package com.example.oblig1quiz.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oblig1quiz.Util.PhotoViewModel;
import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.PhotoInfo;

public class AddImageActivity extends AppCompatActivity {

    private String imageUri;

    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        Button selectPhoto = findViewById(R.id.selectPhoto);
        Button save = findViewById(R.id.saveButton);
        Button back = findViewById(R.id.backButton);
        description = findViewById(R.id.textInputEditText);

        // Onclick for opening cameraroll
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

        // Onclick for saving an image
        save.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String descString = description.getText().toString();

                    // Check for empty name / description and picture
                   if (descString.isEmpty()) {
                       Toast.makeText(getApplicationContext(), "Fill out description", Toast.LENGTH_LONG).show();
                   }
                   if (imageUri == null) {
                       Toast.makeText(getApplicationContext(), "Please select a photo", Toast.LENGTH_LONG).show();
                   }

                   // Granting permission to use the picture from cameraroll
                   if (!imageUri.startsWith("android.resource://com.example.oblig1quiz/drawable/")) {
                       getContentResolver().takePersistableUriPermission(Uri.parse(imageUri), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                   }

                   // Add photo
                   PhotoViewModel photoViewModel = new ViewModelProvider(AddImageActivity.this).get(PhotoViewModel.class);
                   photoViewModel.insert(new PhotoInfo(descString, imageUri));

                   Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_LONG).show();

                   // Wipe the screen
                   reset();
               }
           }

        );

        // Onclick for back button
        back.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   finish();
               }
           }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if(requestCode==0){
            if(resultCode==RESULT_OK && result != null){
                imageUri = result.getData().toString();
                ImageView view = findViewById(R.id.imageView);
                view.setImageURI(Uri.parse(imageUri));
            }
        }
    }

    // Function to empty the screen and make ready for a new entry
    private void reset() {
        ImageView view = findViewById(R.id.imageView);
        view.setImageResource(0);
        EditText input = findViewById(R.id.textInputEditText);
        input.setText("");
        this.imageUri = null;
    }
}