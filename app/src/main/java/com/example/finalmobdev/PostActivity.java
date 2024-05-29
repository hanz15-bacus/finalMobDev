package com.example.finalmobdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText editText;
    Button postButton, chooseFileButton;
    ImageView selectedImageView;
    LinearLayout postLayout;
    Uri selectedImageUri;
    List<Uri> imageUris;
    List<String> captions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        editText = findViewById(R.id.edit_text);
        selectedImageView = findViewById(R.id.selected_image_view);
        postButton = findViewById(R.id.post_button);
        chooseFileButton = findViewById(R.id.choose_file_button);
        postLayout = findViewById(R.id.post_layout);

        imageUris = new ArrayList<>();
        captions = new ArrayList<>();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if (selectedImageUri != null && !text.isEmpty()) {
                    // Post the image and caption
                    imageUris.add(selectedImageUri);
                    captions.add(text);
                    addPostToLayout();
                    editText.setText("");
                    selectedImageView.setImageURI(null); // Clear the selected image
                } else {
                    Toast.makeText(PostActivity.this, "Please select an image and enter a caption", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            selectedImageView.setImageURI(selectedImageUri);
        }
    }

    private void addPostToLayout() {
        View postView = getLayoutInflater().inflate(R.layout.post_item, null);
        ImageView imageView = postView.findViewById(R.id.image_view);
        TextView captionTextView = postView.findViewById(R.id.caption_text_view);

        imageView.setImageURI(imageUris.get(imageUris.size() - 1));
        captionTextView.setText(captions.get(captions.size() - 1));

        postLayout.addView(postView);
    }
}