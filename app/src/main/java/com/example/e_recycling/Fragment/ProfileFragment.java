// A fragment to handle user profile
package com.example.e_recycling.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.e_recycling.R;
import com.example.e_recycling.SlideMenuActivity;
import com.example.e_recycling.UserPostDetails;
import com.example.e_recycling.utils.BitmapProcess;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

public class ProfileFragment extends FragmentMaster {

    View view;
    ImageView imageProfile;
    public static final int ATTACHMENT_CHOICE_CAMERA = 1;
    String folder_main = "E-Recycling";
    int imageHeight = 600;
    int imageWidth = 800;
    EditText edit_phone;
    private boolean isFormatting;
    private boolean deletingHyphen;
    private int hyphenStart;
    private boolean deletingBackward;
    Button add_picture, button_update_profile;
    Integer REQUEST_CAMERA = 3, RESULT_LOAD_IMAGE = 1;
    SharedPreferences sharedPreferences;
    private String userEmail, userMode;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fetch data from SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "NOT_FOUND");
        userMode = sharedPreferences.getString("userType", "NOT_FOUND");
    }

    @Override
    public void prepareViews(View view) {
        imageProfile = view.findViewById(R.id.image_profile_user);
        edit_phone = view.findViewById(R.id.edit_profile_phone);
        add_picture = view.findViewById(R.id.add_picture);
        button_update_profile = view.findViewById(R.id.buttonRegister);

        add_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // OnClick button for update event
        button_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlideMenuActivity.class);
                intent.putExtra("email",userEmail);
                intent.putExtra("userType",userMode);
                startActivity(intent);
            }
        });

        // The below function will help improving UX.
        // For instance, when a user starts typing mobile number and enters three digits,
        // the below function will automatically add hyphen (-) after the third digit.
        // Similarly, a hyphen (-) will be added after the sixth digit.
        // https://stackoverflow.com/a/16976972/8243992
        edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFormatting)
                    return;

                // Make sure user is deleting one char, without a selection
                final int selStart = Selection.getSelectionStart(s);
                final int selEnd = Selection.getSelectionEnd(s);
                if (s.length() > 1 // Can delete another character
                        && count == 1 // Deleting only one character
                        && after == 0 // Deleting
                        && s.charAt(start) == '-' // a hyphen
                        && selStart == selEnd) { // no selection
                    deletingHyphen = true;
                    hyphenStart = start;
                    // Check if the user is deleting forward or backward
                    if (selStart == start + 1) {
                        deletingBackward = true;
                    } else {
                        deletingBackward = false;
                    }
                } else {
                    deletingHyphen = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (isFormatting)
                    return;

                isFormatting = true;

                // If deleting hyphen, also delete character before or after it
                if (deletingHyphen && hyphenStart > 0) {
                    if (deletingBackward) {
                        if (hyphenStart - 1 < text.length()) {
                            text.delete(hyphenStart - 1, hyphenStart);
                        }
                    } else if (hyphenStart < text.length()) {
                        text.delete(hyphenStart, hyphenStart + 1);
                    }
                }
                if (text.length() == 3 || text.length() == 7) {
                    text.append('-');
                    edit_phone.setText(text);
                    edit_phone.setSelection(text.length());
                }

                isFormatting = false;
            }
        });
    }

    @Override
    public void initializeViews() {
    }

    // The below function will help uploading an image.
    // The user will have an option to upload an image either by capturing from camera
    // or selecting from gallery.
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
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);

                }
                else if (items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    // To handle click requests
    // https://www.youtube.com/watch?v=8nDKwtTcOUg
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageProfile.setImageURI(selectedImage);
        }
        else if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null){
            Bundle bundle = data.getExtras();
            Bitmap bmp = (Bitmap) bundle.get("data");
            imageProfile.setImageBitmap(bmp);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }
}
