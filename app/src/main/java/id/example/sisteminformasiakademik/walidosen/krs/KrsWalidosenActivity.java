package id.example.sisteminformasiakademik.walidosen.krs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.walidosen.DashboardWalidosenActivity;

public class KrsWalidosenActivity extends AppCompatActivity {

    TextView txtNidnKRS, txtNamaWaldosKRS, txtProdiKRS, txtSemesterKRS, namaProdi, noSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_walidosen);
        getSupportActionBar().hide();

        txtNidnKRS = findViewById(R.id.txtNIDNkrs);
        txtNamaWaldosKRS = findViewById(R.id.txtNamaWaldosKrs);
        txtProdiKRS = findViewById(R.id.txtProdiWaldosKRS);
        txtSemesterKRS = findViewById(R.id.txtSemesterWaldosKRS);
        namaProdi = findViewById(R.id.namaProdiKRS);
        noSemester = findViewById(R.id.noSemesterKRS);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String tProdi = i.getStringExtra("prodi");
        String tSemester = i.getStringExtra("semester");

        txtNidnKRS.setText(tNim);
        txtNamaWaldosKRS.setText(tName);
        txtProdiKRS.setText(tProdi);
        txtSemesterKRS.setText(tSemester);
        namaProdi.setText(tProdi);
        noSemester.setText(tSemester);
    }

    public void moveDashboardKRSWaldos(View view){
        String idAdmin = txtNidnKRS.getText().toString().trim();
        String namaAdmin = txtNamaWaldosKRS.getText().toString().trim();
        String prodiKHS = txtProdiKRS.getText().toString().trim();
        String semesterKHS = txtSemesterKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtNidnKRS.getText().toString().trim();
        String namaAdmin = txtNamaWaldosKRS.getText().toString().trim();
        String prodiKHS = txtProdiKRS.getText().toString().trim();
        String semesterKHS = txtSemesterKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}