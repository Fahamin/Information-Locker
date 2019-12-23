package lock.file.lockerinfo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lock.file.lockerinfo.R;
import lock.file.lockerinfo.model.DataModel;

public class DataADD extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser user;
    String key_id, key;

    EditText titleET, descriptionET;
    String title, description, times, date;
    Calendar calendar;
    ImageView imageView;
    Button saveBTN;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        key_id = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(key_id);

        key = databaseReference.push().getKey();


        init();


    }

    private void init() {
        titleET = findViewById(R.id.title_ET);
        descriptionET = findViewById(R.id.description_ET);
        saveBTN = findViewById(R.id.save_BTN);
        rootView = findViewById(R.id.root_view);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidity()) {
                    title = titleET.getText().toString();
                    description = descriptionET.getText().toString();

                    Calendar cal = Calendar.getInstance();
                    Date chosenDate = cal.getTime();

                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                    date = dateFormat.format(chosenDate);

                    calendar = Calendar.getInstance();

                    SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
                    String strTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

                    Date time = null;
                    try {
                        time = sdf24.parse(strTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    times = sdf12.format(time);



                    DataModel dataModel = new DataModel(title,description,date,times);
                    databaseReference.child(key).setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(DataADD.this, "Data Save", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void LoadData() {

    }

    private boolean checkValidity() {
        if (titleET.getText().toString().equals("")) {

            titleET.setError("This field is required !!!");
            return false;

        } else if (descriptionET.getText().toString().equals("")) {

            descriptionET.setError("This field is required !!!");
            return false;

        } else {

            return true;
        }
    }
}
