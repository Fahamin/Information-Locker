package lock.file.lockerinfo.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lock.file.lockerinfo.R;
import lock.file.lockerinfo.model.DataModel;

public class DetailsDialogView {


    EditText titleET, descriptionET;
    String title, description, times, date;
    Calendar calendar;
    ImageView imageView;
    Button saveBTN;
    private View rootView;
    AppCompatActivity activity;
    Dialog dialog;



    public void showDialog(final AppCompatActivity activity, String tt, String dd) {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.update_layout);

        init();

        titleET.setText(tt);
        descriptionET.setText(dd);
        dialog.show();
    }

    private void init() {

        titleET = dialog.findViewById(R.id.title_ET);
        descriptionET = dialog.findViewById(R.id.description_ET);
        saveBTN = dialog.findViewById(R.id.save_BTN);
        rootView = dialog.findViewById(R.id.root_view);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}


