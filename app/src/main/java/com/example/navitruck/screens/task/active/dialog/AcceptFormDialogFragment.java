package com.example.navitruck.screens.task.active.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.callback.TaskUpdateCallBack;
import com.example.navitruck.dto.ImageRecyclerDTO;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.Utils.BasicShearedDataService;
import com.example.navitruck.dto.response.ResponseTaskDTO;
import com.example.navitruck.network.rest.TaskRestClient;
import com.example.navitruck.screens.dialog.CircularProgressBarFragment;
import com.example.navitruck.screens.dialog.LoadingDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AcceptFormDialogFragment extends DialogFragment implements OnAddImgClick, TaskUpdateCallBack {



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

    private LoadingDialogFragment progress;


    public AcceptFormDialogFragment(OnAcceptFormView onAcceptView, int position){
        this.onAcceptView = onAcceptView;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_accept_status_form, container, false);

        initViews(v);
        setLinteners();

        ArrayList<ImageRecyclerDTO> arrayList= new ArrayList<ImageRecyclerDTO>();
        Uri imageUri = (new Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.addimg))
                .appendPath(getResources().getResourceTypeName(R.drawable.addimg))
                .appendPath(getResources().getResourceEntryName(R.drawable.addimg))
                .build();
        arrayList.add(new ImageRecyclerDTO(imageUri,false));
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

        progress = new LoadingDialogFragment();
    }

    private void setLinteners(){
        cancelBtn.setOnClickListener(view -> {
            dismiss();
        });

        saveBtn.setOnClickListener(view -> {

            startDialog();
            TaskRestClient client = new TaskRestClient(getActivity());
            client.updateStatus(2, 2, adapter.getUploadImages(), this);

        });
    }

    private void startDialog(){
        if(progress!=null){
            FragmentManager fm = getFragmentManager();
            progress.setCancelable(false);

            progress.show(fm, "");
        }
    }

    private void endDialog(){
        if(progress!=null){
            progress.dismiss();
        }
    }

    @Override
    public void onResponse(Call<ResponseTaskDTO<Object>> call, Response<ResponseTaskDTO<Object>> response) {
        ArrayList<TruckStatus> array = getStatusList();
        array.get(position).setDone(true);

        BasicShearedDataService shearedDataService = new BasicShearedDataService(getContext(), array);
        shearedDataService.save(true);

        onAcceptView.acceptStatus(position);
        endDialog();
        dismiss();

    }

    @Override
    public void onFailure(Call<ResponseTaskDTO<Object>> call, Throwable t) {
        endDialog();

        Toast.makeText(getContext(), "FUuuuuuck", Toast.LENGTH_LONG).show();
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
    public void hideDeleteIcon(int position) {
        adapter.hideDeleteIcon(position);
    }

    @Override
    public void showDeleteIcon(int position) {
        adapter.showDeleteIcon(position);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSION_READ_EXTERNAL){
            if(grantResults.length > 0){
                int grantResult = grantResults[0];
                if(grantResult == PackageManager.PERMISSION_GRANTED){
                    // If user grant the permission then open choose image popup dialog.
                    openPictureGallery();
                }else{
                    Toast.makeText(getContext(), "You denied read external storage permission.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK && null != data) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                if (data.getData() != null) {         //on Single image selected
                    ImageRecyclerDTO dto = new ImageRecyclerDTO(data.getData(), false);

                    adapter.handleDataChange(dto);
                } else {                              //on multiple image selected
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<ImageRecyclerDTO> mArrayUri = new ArrayList<ImageRecyclerDTO>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            mArrayUri.add(new ImageRecyclerDTO(item.getUri(), false));
                        }
                        adapter.handleDataChange(mArrayUri);
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
