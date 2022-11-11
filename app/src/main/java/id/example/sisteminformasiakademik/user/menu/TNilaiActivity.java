package id.example.sisteminformasiakademik.user.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.ModelImage;
import id.example.sisteminformasiakademik.user.MyAdapter;

public class TNilaiActivity extends AppCompatActivity {

    TextView txtNimNilai, txtNamaNilai;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnilai);

        getSupportActionBar().hide();

        txtNimNilai = findViewById(R.id.txtNimNilai);
        txtNamaNilai = findViewById(R.id.txtNamaNilai);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimNilai.setText(tNim);
        txtNamaNilai.setText(tName);

        recyclerView = findViewById(R.id.recyclerViewNilai);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this, imageList);
        recyclerView.setAdapter(myAdapter);

        fetchImagesNilai();
    }

    public void fetchImagesNilai(){

        String nim = txtNimNilai.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://droomp.tech/fetchtnilai.php",
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
                Toast.makeText(TNilaiActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        String nimData = txtNimNilai.getText().toString().trim();
        String namaData = txtNamaNilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNimNilai.getText().toString().trim();
        String namaData = txtNamaNilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}