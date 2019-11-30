package com.example.e_recycling.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_recycling.R;
import com.example.e_recycling.adapter.PlaceAutoSuggestAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

public class AddPostFragment extends FragmentMaster {

    View view;
    AutoCompleteTextView edit_location;
    ImageView image_add_post_1, image_add_post_2;
    Button add_picture_1, add_picture_2;
    private static final int RESULT_LOAD_IMAGE_1 = 1, RESULT_LOAD_IMAGE_2 = 2;
    Integer REQUEST_CAMERA = 3, SELECT_FILE = 0;
    int button_chose = 0;

    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }

    @Override
    public void prepareViews(View view) {
        Log.i("Main", "Message");
        edit_location = view.findViewById(R.id.edit_location);
        image_add_post_1 = view.findViewById(R.id.image_add_post_1);
        image_add_post_2 = view.findViewById(R.id.image_add_post_2);
        add_picture_1 = view.findViewById(R.id.add_picture_1);
        add_picture_2 = view.findViewById(R.id.add_picture_2);

        edit_location.setAdapter(new PlaceAutoSuggestAdapter(getContext(),android.R.layout.simple_list_item_1));

        add_picture_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  button_chose = 1;
                  selectImage();
            }
        });

        add_picture_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_chose = 2;
                selectImage();
            }
        });
    }

    @Override
    public void initializeViews() {

    }

    // https://www.youtube.com/watch?v=i5UcFAdKe5M
    public void selectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        Log.i("IMAGE", "selectImage");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[which].equals("Gallery")){
                    if (button_chose == 1){
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, RESULT_LOAD_IMAGE_1);
                    }
                    else if (button_chose == 2){
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, RESULT_LOAD_IMAGE_2);
                    }
                }
                else if (items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    // https://www.youtube.com/watch?v=8nDKwtTcOUg
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE_1 && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            image_add_post_1.setImageURI(selectedImage);
        }
        else if (requestCode == RESULT_LOAD_IMAGE_2 && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            image_add_post_2.setImageURI(selectedImage);
        }
        else if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null){
            Bundle bundle = data.getExtras();
            Bitmap bmp = (Bitmap) bundle.get("data");
            if (button_chose == 1){
                image_add_post_1.setImageBitmap(bmp);
            }
            else if (button_chose == 2){
                image_add_post_2.setImageBitmap(bmp);
            }
        }
    }
}
