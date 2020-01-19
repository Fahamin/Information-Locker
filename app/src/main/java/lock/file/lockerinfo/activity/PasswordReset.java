package lock.file.lockerinfo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lock.file.lockerinfo.R;

public class PasswordReset extends AppCompatActivity {
    EditText inputEmail;
    Button btnBack;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseUser user;
    String hello = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
           actionBar.setHomeButtonEnabled(true);
           actionBar.setDisplayShowHomeEnabled(true);
        }

        inputEmail = (EditText) findViewById(R.id.email);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user != null) {
            hello = hello.concat("").concat(user.getEmail());
        }

        inputEmail.setText(hello);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);

                if(check())
                {
                    String email = inputEmail.getText().toString().trim();
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordReset.this, "Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PasswordReset.this, "Faield to send", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public boolean check()
    {
        if (inputEmail.getText().toString().isEmpty()) {
            inputEmail.setError("Enter your Email");
            inputEmail.requestFocus();
            return false;
        }
        else
            return true;
    }
}
