package com.example.e_recycling.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.e_recycling.R;
import com.example.e_recycling.utils.BitmapProcess;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.FileProvider;

public class ProfileFragment extends FragmentMaster {

    View view;
    ImageView imageProfile;
    public static final int ATTACHMENT_CHOICE_CAMERA = 1;
    private String imageFilePath = "";
    Uri IMAGE_CAPTURE_URI;
    Bitmap bmp;
    SimpleDateFormat sdf;
    String folder_main = "E-Recycling";
    int imageHeight = 600;
    int imageWidth = 800;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void prepareViews(View view) {
        imageProfile = view.findViewById(R.id.image_profile_user);
    }

    @Override
    public void initializeViews() {
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
            }
        });
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

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, ATTACHMENT_CHOICE_CAMERA);
        }
    }

    private File createImageFile() throws IOException {

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/" + folder_main);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        Log.d("Image", imageFilePath);
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == ATTACHMENT_CHOICE_CAMERA) {
                setImg();
            } else {
                imageFilePath = "";
            }
        } else {
            File imgFile = new File(imageFilePath);
            if (imgFile.exists()) {
                imgFile.delete();
            }
            Log.d("cancelled", imageFilePath);
        }
    }

    public void setImg() {
        bmp = BitmapProcess.decodeSampledBitmapFromPah(imageFilePath, imageHeight,
                imageWidth);

        ExifInterface ei;

        try {
            ei = new ExifInterface(imageFilePath);

            int orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bmp = BitmapProcess.RotateBitmap(bmp, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bmp = BitmapProcess.RotateBitmap(bmp, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bmp = BitmapProcess.RotateBitmap(bmp, 270);
                    break;
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (bmp != null) {
            imageProfile.setImageBitmap(bmp);
        } else {
        }
    }
}
