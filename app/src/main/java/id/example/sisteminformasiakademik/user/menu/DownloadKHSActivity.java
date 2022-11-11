package id.example.sisteminformasiakademik.user.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;

public class DownloadKHSActivity extends AppCompatActivity {

    TextView nimDownloadKhs, namaDownloadKhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_khsactivity);

        nimDownloadKhs = findViewById(R.id.nimDownloadKhs);
        namaDownloadKhs = findViewById(R.id.namaDownloadKhs);

        nimDownloadKhs.setText(getIntent().getStringExtra("nimLKHS"));
        namaDownloadKhs.setText(getIntent().getStringExtra("namaLKHS"));
    }
}