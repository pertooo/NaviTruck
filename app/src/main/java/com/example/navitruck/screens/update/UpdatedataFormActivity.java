package com.example.navitruck.screens.update;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.navitruck.R;
import com.example.navitruck.common.LocationHelper;
import com.example.navitruck.network.rest.RestClient;

import pub.devrel.easypermissions.EasyPermissions;

public class UpdatedataFormActivity extends Activity {

    Activity activity;
    private static int RESULT_LOAD_IMAGE = 1;
    private String[] galleryPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private String list[] = {"Active","Loading","In route", "waiting", "Other"};

    Spinner spinnerStatus;
    TextView zipText;
    EditText note;
    Button sendBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_zip_form);

        activity = this;

        initViews();
        setListeners();
        setAdapter();


    }

    private void initViews(){
        spinnerStatus = findViewById(R.id.spinner_status);
        zipText = findViewById(R.id.zipCode);
        note= findViewById(R.id.noteEditText);
        sendBtn = findViewById(R.id.send);
    }

    private void setListeners(){
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (EasyPermissions.hasPermissions(activity, galleryPermissions)) {
                    pickImageFromGallery();
                } else {
                    EasyPermissions.requestPermissions(activity, "Access for storage",101, galleryPermissions);
                }
            }
        });

        Button getZipBtn = (Button) findViewById(R.id.getZipBtn);
        getZipBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (EasyPermissions.hasPermissions(activity, permissions)) {
                    LocationHelper locationHelper = new LocationHelper(activity);
                    zipText.setText(locationHelper.getZip());
                } else {
                    EasyPermissions.requestPermissions(activity, "Access for storage",101, permissions);
                }
            }
        });

        sendBtn.setOnClickListener(arg -> {

            RestClient client = new RestClient(activity);
            client.sendData(note.getText().toString(), zipText.getText().toString(), spinnerStatus.getSelectedItem().toString());

        });
    }

    private void setAdapter(){


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        spinnerStatus.setAdapter(adapter);
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);;
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }


}
