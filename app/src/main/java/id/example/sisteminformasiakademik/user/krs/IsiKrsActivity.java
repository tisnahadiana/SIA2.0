package id.example.sisteminformasiakademik.user.krs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.DashboardActivity;

public class IsiKrsActivity extends AppCompatActivity {
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("record");

    Spinner spinnerkrs, spinnerprodi;
    EditText edmatkul1, edjumlahsks1, edmatkul2, edjumlahsks2, edmatkul3, edjumlahsks3,
            edmatkul4, edjumlahsks4, edmatkul5, edjumlahsks5, edmatkul6, edjumlahsks6, edmatkul7, edjumlahsks7,
            edmatkul8, edjumlahsks8, edbatal1, edsksbatal1, edbatal2, edsksbatal2, edbatal3, edsksbatal3,
            edjumlahsks;
    TextView txtNimKrs, txtNamaKrs;
    Button btnRefresh, btnKirim, btnSimpan, clear,save;
    String str_nim, str_nama,str_prodi, str_semester, str_matkul1, str_jumlah_sks1, str_matkul2, str_jumlah_sks2, str_matkul3, str_jumlah_sks3, str_matkul4, str_jumlah_sks4, str_matkul5, str_jumlah_sks5, str_matkul6, str_jumlah_sks6, str_matkul7, str_jumlah_sks7, str_matkul8, str_jumlah_sks8, str_batal1, str_batal_sks1, str_batal2, str_batal_sks2, str_batal3, str_batal_sks3, str_jumlahsks;
    String url = "https://droomp.tech/krs.php";

//    ArrayAdapter<String> adapter;
//    DataObject dataObject = new DataObject();
//    long NoPengiriman = 0;
//    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    SimpleDateFormat datePatternformat = new SimpleDateFormat("dd-MM-yyy hh:mm a");

    public final int REQUEST_CODE = 100;

    Bitmap bitmap,scaleBitmap;
    SignatureView signatureView;
    int path;
    private static final String Image_DIRECTORY="/signdemo";
    ScrollView scroolViewkrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_krs);

        getSupportActionBar().hide();

        spinnerkrs = findViewById(R.id.spinnerKrs);
        spinnerprodi = findViewById(R.id.spProdiKrsmahasiswa);
        txtNimKrs = findViewById(R.id.txtNimKrs);
        txtNamaKrs = findViewById(R.id.txtNamaKrs);
        btnRefresh = findViewById(R.id.btnRefreshsks);
        btnKirim = findViewById(R.id.btnKirimKrs);
        btnSimpan = findViewById(R.id.btnSimpanKrs);

        edmatkul1 = findViewById(R.id.ed_matkul1);
        edjumlahsks1 = findViewById(R.id.ed_sks_matkul1);
        edmatkul2 = findViewById(R.id.ed_matkul2);
        edjumlahsks2 = findViewById(R.id.ed_sks_matkul2);
        edmatkul3 = findViewById(R.id.ed_matkul3);
        edjumlahsks3 = findViewById(R.id.ed_sks_matkul3);
        edmatkul4 = findViewById(R.id.ed_matkul4);
        edjumlahsks4 = findViewById(R.id.ed_sks_matkul4);
        edmatkul5 = findViewById(R.id.ed_matkul5);
        edjumlahsks5 = findViewById(R.id.ed_sks_matkul5);
        edmatkul6 = findViewById(R.id.ed_matkul6);
        edjumlahsks6 = findViewById(R.id.ed_sks_matkul6);
        edmatkul7 = findViewById(R.id.ed_matkul7);
        edjumlahsks7 = findViewById(R.id.ed_sks_matkul7);
        edmatkul8 = findViewById(R.id.ed_matkul8);
        edjumlahsks8 = findViewById(R.id.ed_sks_matkul8);
        edbatal1 = findViewById(R.id.ed_batal_matkul1);
        edsksbatal1 = findViewById(R.id.ed_batalsks_matkul1);
        edbatal2 = findViewById(R.id.ed_batal_matkul2);
        edsksbatal2 = findViewById(R.id.ed_batalsks_matkul2);
        edbatal3 = findViewById(R.id.ed_batal_matkul3);
        edsksbatal3 = findViewById(R.id.ed_batalsks_matkul3);
        edjumlahsks = findViewById(R.id.ed_jumlah_sks);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimKrs.setText(tNim);
        txtNamaKrs.setText(tName);

//        signatureView = findViewById(R.id.signatureViewKrs);
//        save = findViewById(R.id.saveSignature);
//        clear = findViewById(R.id.clear);
//        scroolViewkrs = findViewById(R.id.scroolViewkrs);
//
//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signatureView.clearCanvas();
//            }
//        });
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bitmap = signatureView.getSignatureBitmap();
//                path = Integer.parseInt(saveImage(bitmap));
//            }
//        });



        spinnerkrs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LoadMatkul();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

//        CallOnClickListener();
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                NoPengiriman = dataSnapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    printPDF();
                } else {
                    requestAllPermission();
                }
            }
        });

//        signatureView.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int action = motionEvent.getAction();
//                switch (action){
//                    case MotionEvent.ACTION_DOWN:
//                        // Disable the scroll view to intercept the touch event
//                        scroolViewkrs.requestDisallowInterceptTouchEvent(true);
//                        return false;
//                    case MotionEvent.ACTION_UP:
//                        // Allow scroll View to interceot the touch event
//                        scroolViewkrs.requestDisallowInterceptTouchEvent(false);
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        scroolViewkrs.requestDisallowInterceptTouchEvent(true);
//                        return false;
//                    default:
//                        return true;
//                }
//            }
//        });


    }

//    private String saveImage(Bitmap bitmap) {
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90,bytes);
//        File walpaperDirectory = new File(Environment.getExternalStorageDirectory()+ Image_DIRECTORY);
//
//        if (!walpaperDirectory.exists()){
//            walpaperDirectory.mkdirs();
//            Log.d("hhhh",walpaperDirectory.toString());
//
//        }
//
//        File f=new File(walpaperDirectory, Calendar.getInstance().getTimeInMillis()+".jpg");
//        try {
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(IsiKrsActivity.this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
//            fo.close();
//            f.getAbsolutePath();
//            return  f.getAbsolutePath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

//    private void CallOnClickListener() {
//        btnKirim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dataObject.NoPengiriman = NoPengiriman+1;
//                dataObject.str_nim = String.valueOf(txtNimKrs.getText());
//                dataObject.str_nama = String.valueOf(txtNamaKrs.getText());
//                dataObject.str_matkul1 = String.valueOf(edmatkul1.getText());
//                dataObject.str_jumlah_sks1 = String.valueOf(edjumlahsks1.getText());
//                dataObject.str_matkul2 = String.valueOf(edmatkul2.getText());
//                dataObject.str_jumlah_sks2 = String.valueOf(edjumlahsks2.getText());
//                dataObject.str_matkul3 = String.valueOf(edmatkul3.getText());
//                dataObject.str_jumlah_sks3 = String.valueOf(edjumlahsks3.getText());
//                dataObject.str_matkul4 = String.valueOf(edmatkul4.getText());
//                dataObject.str_jumlah_sks4 = String.valueOf(edjumlahsks4.getText());
//                dataObject.str_matkul5 = String.valueOf(edmatkul5.getText());
//                dataObject.str_jumlah_sks5 = String.valueOf(edjumlahsks5.getText());
//                dataObject.str_matkul6 = String.valueOf(edmatkul6.getText());
//                dataObject.str_jumlah_sks6 = String.valueOf(edjumlahsks6.getText());
//                dataObject.str_matkul7 = String.valueOf(edmatkul7.getText());
//                dataObject.str_jumlah_sks7 = String.valueOf(edjumlahsks7.getText());
//                dataObject.str_matkul8 = String.valueOf(edmatkul8.getText());
//                dataObject.str_jumlah_sks8 = String.valueOf(edjumlahsks8.getText());
//                dataObject.date = new Date().getTime();
//
//                myRef.child(String.valueOf(NoPengiriman+1)).setValue(dataObject);
//
//                printPDF();
//            }
//        });
//    }

    private void printPDF() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,0,0));

        canvas.drawText("Universitas Putra Indonesia", 40,20, paint);
        paint.setTextSize(9.5f);
        canvas.drawText("Jln.Dr.Muwardi No.66 Telp. (0263) 262604", 50,40,paint);
        canvas.drawText("By Pass - Cianjur 43215", 80,50,paint);

        forLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLinePaint.setStrokeWidth(2);
        canvas.drawLine(20,60,240,60,forLinePaint);

        paint.setTextSize(10.5f);
        canvas.drawText("KARTU RENCANA STUDI", 80,75,paint);

        paint.setTextSize(8.5f);
        canvas.drawText("Nama          : "+txtNamaKrs.getText(),20,90,paint);
        canvas.drawText("NIM             : "+txtNimKrs.getText(),20,100,paint);
        canvas.drawText("Prodi            : "+spinnerkrs.getSelectedItem().toString(),20,110,paint);
        canvas.drawText("Semester   : "+spinnerprodi.getSelectedItem().toString(),20,120,paint);
        canvas.drawLine(20,140,240,140,forLinePaint);

        canvas.drawText("Nama Mata Kuliah", 40,155, paint);
        canvas.drawText("SKS", 200,155, paint);
        canvas.drawLine(20,165,240,165,forLinePaint);

        canvas.drawText(""+edmatkul1.getText(), 20,175, paint);
        canvas.drawText(""+edjumlahsks1.getText(), 205,175, paint);
        canvas.drawText(""+edmatkul2.getText(), 20,190, paint);
        canvas.drawText(""+edjumlahsks2.getText(), 205,190, paint);
        canvas.drawText(""+edmatkul3.getText(), 20,205, paint);
        canvas.drawText(""+edjumlahsks3.getText(), 205,205, paint);
        canvas.drawText(""+edmatkul4.getText(), 20,220, paint);
        canvas.drawText(""+edjumlahsks4.getText(), 205,220, paint);
        canvas.drawText(""+edmatkul5.getText(), 20,235, paint);
        canvas.drawText(""+edjumlahsks5.getText(), 205,235, paint);
        canvas.drawText(""+edmatkul6.getText(), 20,250, paint);
        canvas.drawText(""+edjumlahsks6.getText(), 205,250, paint);
        canvas.drawText(""+edmatkul7.getText(), 20,265, paint);
        canvas.drawText(""+edjumlahsks7.getText(), 205,265, paint);
        canvas.drawText(""+edmatkul8.getText(), 20,280, paint);
        canvas.drawText(""+edjumlahsks8.getText(), 205,280, paint);

        canvas.drawText("Jumlah SKS : ", 140,285, paint);
        canvas.drawText(""+edjumlahsks.getText(), 200,285, paint);
        canvas.drawLine(20,295,240,295,forLinePaint);

        canvas.drawText("Cianjur, "+datePatternformat.format(new Date().getTime()),120, 310,paint);
        canvas.drawText("Mengetahui : ", 115,325, paint);
        canvas.drawText("Bagian Akademik", 20,340, paint);
        canvas.drawText("Dosen Wali", 115,340, paint);
        canvas.drawText("Mahasiswa", 200,340, paint);

        canvas.drawText("................", 35,375, paint);
        canvas.drawText("................", 115,375, paint);
        canvas.drawText("................", 200,375, paint);

        myPdfDocument.finishPage(myPage);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/KRS-UNPI" + ".pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();

    }

    private void requestAllPermission() {
        ActivityCompat.requestPermissions(IsiKrsActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(IsiKrsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void RefreshSKS(View view) {
        if (spinnerkrs.getSelectedItem().toString().equals("2")) {
            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());
            int sks8 = Integer.parseInt(edjumlahsks8.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7 + sks8 ;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        } else if (spinnerkrs.getSelectedItem().toString().equals("4")) {
            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));
        } else if (spinnerkrs.getSelectedItem().toString().equals("6")) {
            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        }else if (spinnerkrs.getSelectedItem().toString().equals("8")) {
            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());

            int jumlahsks = sks1 + sks2;
            edjumlahsks.setText(String.valueOf(jumlahsks));
        }


    }

    public void isiKRS(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        if (edmatkul1.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show();
        } else if (edjumlahsks1.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Nim", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();
            str_nim = txtNimKrs.getText().toString().trim();
            str_nama = txtNamaKrs.getText().toString().trim();
            str_prodi = spinnerprodi.getSelectedItem().toString().trim();
            str_semester = spinnerkrs.getSelectedItem().toString().trim();
            str_matkul1 = edmatkul1.getText().toString().trim();
            str_jumlah_sks1 = edjumlahsks1.getText().toString().trim();
            str_matkul2 = edmatkul2.getText().toString().trim();
            str_jumlah_sks2 = edjumlahsks2.getText().toString().trim();
            str_matkul3 = edmatkul3.getText().toString().trim();
            str_jumlah_sks3 = edjumlahsks3.getText().toString().trim();
            str_matkul4 = edmatkul4.getText().toString().trim();
            str_jumlah_sks4 = edjumlahsks4.getText().toString().trim();
            str_matkul5 = edmatkul5.getText().toString().trim();
            str_jumlah_sks5 = edjumlahsks5.getText().toString().trim();
            str_matkul6 = edmatkul6.getText().toString().trim();
            str_jumlah_sks6 = edjumlahsks6.getText().toString().trim();
            str_matkul7 = edmatkul7.getText().toString().trim();
            str_jumlah_sks7 = edjumlahsks7.getText().toString().trim();
            str_matkul8 = edmatkul8.getText().toString().trim();
            str_jumlah_sks8 = edjumlahsks8.getText().toString().trim();
            str_batal1 = edbatal1.getText().toString().trim();
            str_batal_sks1 = edsksbatal1.getText().toString().trim();
            str_batal2 = edbatal2.getText().toString().trim();
            str_batal_sks2 = edsksbatal2.getText().toString().trim();
            str_batal3 = edbatal3.getText().toString().trim();
            str_batal_sks3 = edsksbatal3.getText().toString().trim();
            str_jumlahsks = edjumlahsks.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    printPDF();
                    edmatkul1.setText("");
                    edjumlahsks1.setText("");
                    edmatkul2.setText("");
                    edjumlahsks2.setText("");
                    edmatkul3.setText("");
                    edjumlahsks3.setText("");
                    edmatkul4.setText("");
                    edjumlahsks4.setText("");
                    edmatkul5.setText("");
                    edjumlahsks5.setText("");
                    edmatkul6.setText("");
                    edjumlahsks6.setText("");
                    edmatkul7.setText("");
                    edjumlahsks7.setText("");
                    edmatkul8.setText("");
                    edjumlahsks8.setText("");
                    edbatal1.setText("");
                    edsksbatal1.setText("");
                    edbatal2.setText("");
                    edsksbatal2.setText("");
                    edbatal3.setText("");
                    edsksbatal3.setText("");
                    edjumlahsks.setText("");
                    Toast.makeText(IsiKrsActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(IsiKrsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("nim", str_nim);
                    params.put("nama", str_nama);
                    params.put("prodi", str_prodi);
                    params.put("semester", str_semester);
                    params.put("matkul1", str_matkul1);
                    params.put("sksmatkul1", str_jumlah_sks1);
                    params.put("matkul2", str_matkul2);
                    params.put("sksmatkul2", str_jumlah_sks2);
                    params.put("matkul3", str_matkul3);
                    params.put("sksmatkul3", str_jumlah_sks3);
                    params.put("matkul4", str_matkul4);
                    params.put("sksmatkul4", str_jumlah_sks4);
                    params.put("matkul5", str_matkul5);
                    params.put("sksmatkul5", str_jumlah_sks5);
                    params.put("matkul6", str_matkul6);
                    params.put("sksmatkul6", str_jumlah_sks6);
                    params.put("matkul7", str_matkul7);
                    params.put("sksmatkul7", str_jumlah_sks7);
                    params.put("matkul8", str_matkul8);
                    params.put("sksmatkul8", str_jumlah_sks8);
                    params.put("matkulbatal1", str_batal1);
                    params.put("sksbatal1", str_batal_sks1);
                    params.put("matkulbatal2", str_batal2);
                    params.put("sksbatal2", str_batal_sks2);
                    params.put("matkulbatal3", str_batal3);
                    params.put("sksbatal3", str_batal_sks3);
                    params.put("jumlahsks", str_jumlahsks);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(IsiKrsActivity.this);

            requestQueue.add(request);


        }
    }

    private void LoadMatkul() {

        String semester = spinnerkrs.getSelectedItem().toString();

        if (spinnerkrs.getSelectedItem().toString().equals("2")) {
            edmatkul1.setText("Pengantar AI");
            edjumlahsks1.setText("3");
            edmatkul2.setText("Web Programming");
            edjumlahsks2.setText("3");
            edmatkul3.setText("Algoritma &Pemrograman II");
            edjumlahsks3.setText("3");
            edmatkul4.setText("Pendidikan Agama");
            edjumlahsks4.setText("2");
            edmatkul5.setText("Fisika Dasar Lanjut");
            edjumlahsks5.setText("3");
            edmatkul6.setText("Kalkulus Lanjut");
            edjumlahsks6.setText("3");
            edmatkul7.setText("Bahasa Indonesia");
            edjumlahsks7.setText("2");
            edmatkul8.setText("Organisasi & Arsitektur Komputer");
            edjumlahsks8.setText("3");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());
            int sks8 = Integer.parseInt(edjumlahsks8.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7 + sks8 ;
            edjumlahsks.setText(String.valueOf(jumlahsks));


        } else if (spinnerkrs.getSelectedItem().toString().equals("4")) {
            edmatkul1.setText("Game Komputer");
            edjumlahsks1.setText("3");
            edmatkul2.setText("Mobile Programming I");
            edjumlahsks2.setText("3");
            edmatkul3.setText("RPL II :Desain & Implementasi");
            edjumlahsks3.setText("3");
            edmatkul4.setText("Metode Numerik");
            edjumlahsks4.setText("3");
            edmatkul5.setText("Sistem Operasi");
            edjumlahsks5.setText("3");
            edmatkul6.setText("Sistem Data Base");
            edjumlahsks6.setText("3");
            edmatkul7.setText("Metode OOP");
            edjumlahsks7.setText("3");
            edmatkul8.setText("");
            edjumlahsks8.setText("");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        } else if (spinnerkrs.getSelectedItem().toString().equals("6")) {
            edmatkul1.setText("Artificial Intelligence & Machine Learning");
            edjumlahsks1.setText("3");
            edmatkul2.setText("Analisis Sistem Inforamasi");
            edjumlahsks2.setText("3");
            edmatkul3.setText("Interfersonal Skill");
            edjumlahsks3.setText("2");
            edmatkul4.setText("Multimedia");
            edjumlahsks4.setText("3");
            edmatkul5.setText("Model & Simulasi");
            edjumlahsks5.setText("3");
            edmatkul6.setText("MK Lintas Prodi");
            edjumlahsks6.setText("3");
            edmatkul7.setText("MK Lintas Prodi");
            edjumlahsks7.setText("3");
            edmatkul8.setText("");
            edjumlahsks8.setText("");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        } else if (spinnerkrs.getSelectedItem().toString().equals("8")) {
            edmatkul1.setText("Tugas Akhir/Skripsi");
            edjumlahsks1.setText("6");
            edmatkul2.setText("Mobile Programing");
            edjumlahsks2.setText("3");
            edmatkul3.setText("");
            edjumlahsks3.setText("");
            edmatkul4.setText("");
            edjumlahsks4.setText("");
            edmatkul5.setText("");
            edjumlahsks5.setText("");
            edmatkul6.setText("");
            edjumlahsks6.setText("");
            edmatkul7.setText("");
            edjumlahsks7.setText("");
            edmatkul8.setText("");
            edjumlahsks8.setText("");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());

            int jumlahsks = sks1 + sks2;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        }


    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Ingin Kembali Ke Halaman Dashboard?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nimData = txtNimKrs.getText().toString().trim();
                        String namaData = txtNamaKrs.getText().toString().trim();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("nim", nimData);
                        intent.putExtra("nama", namaData);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}