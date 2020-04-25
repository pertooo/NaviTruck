package com.example.navitruck.screens.task.active.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.Utils.BasicShearedDataService;
import com.example.navitruck.screens.task.active.StatusRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AcceptFormDialogFragment extends DialogFragment implements OnAddImgClick {



    private static final int PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int PICK_IMAGE_MULTIPLE = 200;

    String imageEncoded;
    List<String> imagesEncodedList;
    private final static int REQUEST_PERMISSION_READ_EXTERNAL = 2;

    RecyclerView imagesRecyclerView;
    ImagesRecyclerAdapter adapter;

    private Button cancelBtn;
    private Button saveBtn;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private OnAcceptFormView onAcceptView;
    private int position;

    public AcceptFormDialogFragment(OnAcceptFormView onAcceptView, int position){
        this.onAcceptView = onAcceptView;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_accept_status_form, container, false);

        initViews(v);
        setLinteners();

        ArrayList<Uri> arrayList= new ArrayList<Uri>();

        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new ImagesRecyclerAdapter(getContext(), arrayList, this);
        imagesRecyclerView.setAdapter(adapter);

        return v;
    }

    private void initViews(View v){
        sharedPref = getActivity().getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        imagesRecyclerView = v.findViewById(R.id.imagesRecyclerView);

        cancelBtn = v.findViewById(R.id.cancel);
        saveBtn = v.findViewById(R.id.save);
    }

    private void setLinteners(){
        cancelBtn.setOnClickListener(view -> {
            dismiss();
        });

        saveBtn.setOnClickListener(view -> {
            ArrayList<TruckStatus> array = getStatusList();
            array.get(position).setDone(true);

            BasicShearedDataService shearedDataService = new BasicShearedDataService(getContext(), array);
            shearedDataService.save(true);

            onAcceptView.acceptStatus(position);
            dismiss();
        });
    }

    private ArrayList<TruckStatus> getStatusList(){
        Gson gson = new Gson();
        String json = sharedPref.getString("statusList", "");
        Type type = new TypeToken<ArrayList<TruckStatus>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void addImage() {
// Because camera app returned uri value is something like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
        // So if show the camera image in image view, this app require below permission.
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExternalStoragePermission != PackageManager.PERMISSION_GRANTED){
            String requirePermission[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(getActivity(), requirePermission, REQUEST_PERMISSION_READ_EXTERNAL);
        }else {
            openPictureGallery();
        }
    }

    @Override
    public void deleteImage(int position) {
        adapter.showDeleteIcon(position);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSION_READ_EXTERNAL){
            if(grantResults.length > 0)
            {
                int grantResult = grantResults[0];
                if(grantResult == PackageManager.PERMISSION_GRANTED)
                {
                    // If user grant the permission then open choose image popup dialog.
                    openPictureGallery();
                }else
                {
                    Toast.makeText(getContext(), "You denied read external storage permission.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                if (data.getData() != null) {         //on Single image selected

                    Uri mImageUri = data.getData();
                    adapter.handleDataChange(mImageUri);

                } else {                              //on multiple image selected
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                        }
                        adapter.handleDataChange(mArrayUri);
                        Log.v("MainActivity", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void openPictureGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }
}
