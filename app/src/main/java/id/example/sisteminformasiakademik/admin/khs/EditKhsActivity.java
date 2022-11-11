package id.example.sisteminformasiakademik.admin.khs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class EditKhsActivity extends AppCompatActivity {

    TextView txtIdAdminEditKhs, txtNamaAdminEditKhs, txtDataNimEditKhs, txtImageUrlEditKhs;
    EditText ed_editNimKhs, ed_editNamaKHS;
    String encodeImageString,str_nim,str_nama, str_prodi, str_semester, str_nimDataEditKhs, urlimg;
    Spinner spEditProdiKhsAdmin, spEditSemesterKhsAdmin;
    Button upload, browse;
    Bitmap bitmap;
    ImageView img;
    String urlapi = "https://droomp.tech/admin/khs/edit_datakhs_admin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_khs);
        getSupportActionBar().hide();

        txtIdAdminEditKhs = findViewById(R.id.txtIdAdminEditKhs);
        txtNamaAdminEditKhs = findViewById(R.id.txtNamaAdminEditKhs);
        txtDataNimEditKhs = findViewById(R.id.txtDataNimEditKhs);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminEditKhs.setText(tNim);
        txtNamaAdminEditKhs.setText(tName);

        ed_editNimKhs = findViewById(R.id.ed_editNimKhs);
        ed_editNamaKHS = findViewById(R.id.ed_editNamaKHS);
        spEditProdiKhsAdmin = findViewById(R.id.spEditProdiKhsAdmin);
        spEditSemesterKhsAdmin = findViewById(R.id.spEditSemesterKhsAdmin);
        txtImageUrlEditKhs = findViewById(R.id.txtImageUrlEditKhs);
        img = findViewById(R.id.imgUploadEditKHSAdmin);

        ed_editNimKhs.setText(getIntent().getStringExtra("nimEDKHS"));
        ed_editNamaKHS.setText(getIntent().getStringExtra("namaEDKHS"));
        txtImageUrlEditKhs.setText(getIntent().getStringExtra("imgLKHS"));
        txtDataNimEditKhs.setText(getIntent().getStringExtra("nimEDKHS"));
        urlimg = txtImageUrlEditKhs.getText().toString().trim();



        upload = findViewById(R.id.btn_EditKhsAdmin);
        browse = findViewById(R.id.browseImgEditKHSAdmin);

        Glide.with(this).load(urlimg).into(img);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(EditKhsActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDatakhs();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            Uri filepath = data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void uploadDatakhs() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");
        progressDialog.show();
        str_nim = ed_editNimKhs.getText().toString().trim();
        str_nama = ed_editNamaKHS.getText().toString().trim();
        str_prodi = spEditProdiKhsAdmin.getSelectedItem().toString();
        str_semester = spEditSemesterKhsAdmin.getSelectedItem().toString();
        str_nimDataEditKhs = txtDataNimEditKhs.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, urlapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ed_editNimKhs.setText("");
                ed_editNamaKHS.setText("");
                img.setImageResource(R.drawable.ic_launcher_foreground);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String,String>();
                map.put("nimData", str_nimDataEditKhs);
                map.put("nim", str_nim);
                map.put("nama", str_nama);
                map.put("prodi", str_prodi);
                map.put("semester", str_semester);
                map.put("upload", encodeImageString);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    public void onBackPressed() {
        String idAdmin = txtIdAdminEditKhs.getText().toString().trim();
        String namaAdmin = txtNamaAdminEditKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), KhsAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}