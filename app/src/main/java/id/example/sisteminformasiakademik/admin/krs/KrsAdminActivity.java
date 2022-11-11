package id.example.sisteminformasiakademik.admin.krs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;

public class KrsAdminActivity extends AppCompatActivity {

    TextView txtIdAdminKRS, txtNamaAdminKRS;
    FloatingActionButton fab_addKRS;
    Spinner spinnerProdiAdminKRS, spinnerNoMatkulKRSAdmin;
    String url = "https://droomp.tech/admin/krs/showkrs_admin.php";

    ArrayList<KrsData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_admin);
        getSupportActionBar().hide();




    }

//    public void show_krsData() {
//
//        String prodi = spinnerProdiAdminKRS.getSelectedItem().toString();
//        String semester = spinnerNoMatkulKRSAdmin.getSelectedItem().toString();
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Mohon Menunggu.....");
//
//        progressDialog.show();
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    JSONArray j = new JSONArray(response);
//                    list.clear();
//                    for (int i=0; i<j.length(); i++){
//                        try {
////                            JSONObject getData = jsonArray.getJSONObject(i);
//                            JSONObject object = j.getJSONObject(i);
//                            String nim = object.getString("nim");
//                            String nama = object.getString("nama");
//                            String prodi = object.getString("prodi");
//                            String semester = object.getString("semester");
////                            String matkul1 = object.getString("matkul1");
////                            String jmlmatkul1 = object.getString("jmlmatkul1");
////                            String matkul2 = object.getString("matkul2");
////                            String jmlmatkul2 = object.getString("jmlmatkul2");
////                            String matkul3 = object.getString("matkul3");
////                            String jmlmatkul3 = object.getString("jmlmatkul3");
////                            String matkul4 = object.getString("matkul4");
////                            String jmlmatkul4 = object.getString("jmlmatkul4");
////                            String matkul5 = object.getString("matkul5");
////                            String jmlmatkul5 = object.getString("jmlmatkul5");
////                            String matkul6 = object.getString("matkul6");
////                            String jmlmatkul6 = object.getString("jmlmatkul6");
////                            String matkul7 = object.getString("matkul7");
////                            String jmlmatkul7 = object.getString("jmlmatkul7");
////                            String matkul8 = object.getString("matkul8");
////                            String jmlmatkul8 = object.getString("jmlmatkul8");
////
////                            String matkulbatal1 = object.getString("matkulbatal1");
////                            String jmlmatkulbatal1 = object.getString("jmlmatkulbatal1");
////
////                            String matkulbatal2 = object.getString("matkulbatal2");
////                            String jmlmatkulbatal2 = object.getString("jmlmatkulbatal2");
////
////                            String matkulbatal3 = object.getString("matkulbatal3");
////                            String jmlmatkulbatal3 = object.getString("jmlmatkulbatal3");
////                            String jmlsks = object.getString("jmlsks");
//
//                            KrsData krsData = new KrsData();
//                            krsData.setStr_nim(nim);
//                            krsData.setStr_nama(nama);
//                            krsData.setStr_prodi(prodi);
//                            krsData.setStr_semester(semester);
////                            krsData.setStr_matkul1(matkul1);
////                            krsData.setStr_jumlah_sks1(jmlmatkul1);
////                            krsData.setStr_matkul2(matkul2);
////                            krsData.setStr_jumlah_sks2(jmlmatkul2);
////                            krsData.setStr_matkul3(matkul3);
////                            krsData.setStr_jumlah_sks3(jmlmatkul3);
////                            krsData.setStr_matkul4(matkul4);
////                            krsData.setStr_jumlah_sks4(jmlmatkul4);
////                            krsData.setStr_matkul5(matkul5);
////                            krsData.setStr_jumlah_sks5(jmlmatkul5);
////                            krsData.setStr_matkul6(matkul6);
////                            krsData.setStr_jumlah_sks6(jmlmatkul6);
////                            krsData.setStr_matkul7(matkul7);
////                            krsData.setStr_jumlah_sks7(jmlmatkul7);
////                            krsData.setStr_matkul8(matkul8);
////                            krsData.setStr_jumlah_sks8(jmlmatkul8);
////
////                            krsData.setStr_batal1(matkulbatal1);
////                            krsData.setStr_batal_sks1(jmlmatkulbatal1);
////
////                            krsData.setStr_batal2(matkulbatal2);
////                            krsData.setStr_batal_sks2(jmlmatkulbatal2);
////
////                            krsData.setStr_batal3(matkulbatal3);
////                            krsData.setStr_batal_sks3(jmlmatkulbatal3);
////                            krsData.setStr_jumlahsks(jmlsks);
//
//                            list.add(krsData);
//                            progressDialog.dismiss();
////                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                        }
//                    }
//                    Adapter adapter = new Adapter(KrsAdminActivity.this, list);
//                    listView.setAdapter(adapter);
//
//
//                    progressDialog.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            //                @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("prodi", prodi);
//                params.put("semester", semester);
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(KrsAdminActivity.this);
//        queue.add(request);
//
//    }

    public void moveDashboardKrsData(View view){
        String idAdmin = txtIdAdminKRS.getText().toString().trim();
        String namaAdmin = txtNamaAdminKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminKRS.getText().toString().trim();
        String namaAdmin = txtNamaAdminKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}
