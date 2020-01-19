package lock.file.lockerinfo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;


import lock.file.lockerinfo.R;
import lock.file.lockerinfo.activity.Login;
import lock.file.lockerinfo.activity.MainActivity;

public class SettingDialog {

    CheckBox checkBox, checkBoxInactive;

    public void showSettingDialog(AppCompatActivity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("pass", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.settinglayout);

        checkBox = dialog.findViewById(R.id.passwordCheckID);
        Button saveBtn = dialog.findViewById(R.id.saveBtnDialogID);

        boolean ss = preferences.getBoolean("p", false);


        if (ss) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    editor.putBoolean("p", true);
                    editor.commit();
                    dialog.dismiss();
                } else
                    editor.clear();
                      editor.commit();
                      dialog.dismiss();
            }
        });


        dialog.show();
    }
}
