package lock.file.lockerinfo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import lock.file.lockerinfo.R;

public class SettingActivity extends AppCompatActivity {
Button seletBtn,upBtn;
ImageView imageView;
Uri filePath;
private  final int pick_ImageCode = 11;
FirebaseStorage storage;
StorageReference storageReference;

FirebaseUser user;
String key_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        seletBtn = findViewById(R.id.btnChoose);
        upBtn = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);


        storageReference = FirebaseStorage.getInstance().getReference().child(key_id);
        user = FirebaseAuth.getInstance().getCurrentUser();
        key_id = user.getUid();
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(key_id);
        String link = storageRef.getDownloadUrl().toString();

       /* if(link != null)
        {
            Glide.with(this)
                    .load(link)
                    .into(imageView);
        }*/


        seletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seletImage();
            }
        });
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Toast.makeText(SettingActivity.this, "upload succes", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(SettingActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    double progress
                            = (100.0
                            * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(
                            "Uploaded "
                                    + (int)progress + "%");
                }
            });
        }
    }

    private void seletImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image From Here!!"),pick_ImageCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == pick_ImageCode && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);

            }catch (IOException e)
            {

            }
        }

    }


}
