package id.example.sisteminformasiakademik.user.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.khs.LihatKhsActivity;
import id.example.sisteminformasiakademik.user.ModelImage;
import id.example.sisteminformasiakademik.user.MyAdapter;

public class KhsActivity extends AppCompatActivity {

    TextView txtNimKhs, txtNamaKhs, txtImageUrl,urlImageAdapterView;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;
    Bitmap bitmap;
    ImageView imageView;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
//    Button btnDownloadKHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs);

        getSupportActionBar().hide();
        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");
        String img = i.getStringExtra("img");
        txtNimKhs = findViewById(R.id.txtNimKhs);
        txtNamaKhs = findViewById(R.id.txtNamaKhs);
        txtImageUrl = findViewById(R.id.txtImageUrl);

        txtNimKhs.setText(tNim);
        txtNamaKhs.setText(tName);
        txtImageUrl.setText(img);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this, imageList);
        recyclerView.setAdapter(myAdapter);
        imageView = findViewById(R.id.imageViewAdapter);

        fetchImagesKHS();
//
//        PRDownloader.initialize(getApplicationContext());

//        btnDownloadKHS.setOnClickListener(v -> {
//            checkPermission();
//        });

    }

//    private void checkPermission() {
//        Dexter.withContext(this)
//                .withPermissions(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                ).withListener(new MultiplePermissionsListener() {
//            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
//                if (report.areAllPermissionsGranted()){
//                    downloadImage();
//                }else {
//                    Toast.makeText(KhsActivity.this, "Please allow all permission", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
//        }).check();
//    }
//
//    private void downloadImage() {
//
//        File file = Environment
//
//        PRDownloader.download(url, dirPath, fileName)
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
//
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//
//                    }
//                });
//    }


    public void fetchImagesKHS(){

        String nim = txtNimKhs.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://droomp.tech/fetchimages.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")){

                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nim = object.getString("nim");
                                    String imageurl = object.getString("image");

                                    String url = imageurl;
//                                    "https://droomp.tech/khs/"+
                                    modelImage = new ModelImage(nim, url);
                                    imageList.add(modelImage);
                                    myAdapter.notifyDataSetChanged();

                                    progressDialog.dismiss();

                                }

                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(KhsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void moveMenuKhs(View view){
        String nimData = txtNimKhs.getText().toString().trim();
        String namaData = txtNamaKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNimKhs.getText().toString().trim();
        String namaData = txtNamaKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}