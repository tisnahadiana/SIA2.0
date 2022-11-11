package id.example.sisteminformasiakademik.admin.khs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
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

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import id.example.sisteminformasiakademik.R;
//import id.example.sisteminformasiakademik.admin.khs.MyAdapter;

public class LihatKhsActivity extends AppCompatActivity {

    TextView txtIdAdminLihatKhs, txtNamaAdminLihatKhs,nim, nama,prodi, semester, imgURLKhs;
    ImageView imgLihatKhs;
    String url;
    Context context;
    Button btnDownloadKhs;
    Bitmap bitmap;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_khs);
        getSupportActionBar().hide();

        txtIdAdminLihatKhs = findViewById(R.id.txtIdAdminLihatKhs);
        txtNamaAdminLihatKhs = findViewById(R.id.txtNamaAdminLihatKhs);
        btnDownloadKhs = findViewById(R.id.btnDownloadKhs);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminLihatKhs.setText(tNim);
        txtNamaAdminLihatKhs.setText(tName);

        nim = findViewById(R.id.nimLihatdataKhs);
        nama = findViewById(R.id.namaLihatdataKhs);
        prodi = findViewById(R.id.prodiLihatdataKhs);
        semester = findViewById(R.id.semesterLihatdataKhs);
        imgURLKhs = findViewById(R.id.imgURLKhs);
        imgLihatKhs = findViewById(R.id.imgLihatKhs);


        nim.setText(getIntent().getStringExtra("nimLKHS"));
        nama.setText(getIntent().getStringExtra("namaLKHS"));
        prodi.setText(getIntent().getStringExtra("prodiLKHS"));
        semester.setText(getIntent().getStringExtra("semesterLKHS"));
        imgURLKhs.setText(getIntent().getStringExtra("imgLKHS"));
        url = imgURLKhs.getText().toString().trim();

        Glide.with(this).load(url).into(imgLihatKhs);

        btnDownloadKhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
                    }
                    else {
                        saveImage();
                    }
                }
            }
        });


//        fetchImagesLKHSAdmin();
    }

    private void saveImage() {
        bitmap = ((BitmapDrawable) imgLihatKhs.getDrawable()).getBitmap();

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/DCIM");
        dir.mkdirs();
        String imagename = time+".PNG";
        File file = new File(dir, imagename);
        OutputStream out;

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            Toast.makeText(LihatKhsActivity.this, "File Saved In DCIM", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(LihatKhsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case WRITE_EXTERNAL_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission Enable", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    //    private void fetchImagesLKHSAdmin() {
//
//        String nim = txtNimLihatKhs.getText().toString();
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Mohon Menunggu.....");
//
//        progressDialog.show();
//        StringRequest request = new StringRequest(Request.Method.POST, "https://droomp.tech/admin/khs/lihat_data_khsadmin.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                            if (success.equals("1")){
//
//                                for (int i=0;i<jsonArray.length();i++) {
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    String nim = object.getString("nim");
//                                    String imageurl = object.getString("image");
//
//                                    String url = imageurl;
////                                    "https://droomp.tech/khs/"+
//                                    modelImage = new ModelImage(nim, url);
//                                    imageList.add(modelImage);
//                                    myAdapter.notifyDataSetChanged();
//
//                                    progressDialog.dismiss();
//
//                                }
//
//                            }
//
//                        } catch (JSONException e) {
//                            progressDialog.dismiss();
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(LihatKhsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            //@Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("nim", nim);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//
//    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminLihatKhs.getText().toString().trim();
        String namaAdmin = txtNamaAdminLihatKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), KhsAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}