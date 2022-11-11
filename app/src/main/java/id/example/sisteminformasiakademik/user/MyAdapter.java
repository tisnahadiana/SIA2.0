package id.example.sisteminformasiakademik.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.khs.LihatKhsActivity;
import id.example.sisteminformasiakademik.user.menu.DownloadKHSActivity;
import id.example.sisteminformasiakademik.user.menu.KhsActivity;

public class MyAdapter extends RecyclerView.Adapter<ImageViewHOlder> {

    private Context context;
    private List<ModelImage> imageList;

    public MyAdapter(Context context, List<ModelImage> imageList) {
        this.context = context;
        this.imageList = imageList;
    }


    @NonNull
    @Override
    public ImageViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_item, parent,false);
        return new ImageViewHOlder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHOlder holder, int position) {
        Glide.with(context).load(imageList.get(position).getImageurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}

class ImageViewHOlder extends RecyclerView.ViewHolder{

    ImageView imageView;

    public ImageViewHOlder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageViewAdapter);


    }

}

