package sg.edu.rp.c346.taskmanagerl6ps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    int reqCode = 12345;
    EditText name, description, seconds;
    Button btnAdd, btnCancel;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        name = findViewById(R.id.editTextName);
        description = findViewById(R.id.editTextDescription);
        seconds = findViewById(R.id.editTextTime);
        btnAdd = findViewById(R.id.buttonPerfAdd);
        btnCancel = findViewById(R.id.buttonCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() == false || description.getText().toString().isEmpty() == false || seconds.getText().toString().isEmpty() == false) {
                    dbHelper = new DBHelper(AddTask.this);
                    dbHelper.insertTask(name.getText().toString(), description.getText().toString());
                    int a = Integer.parseInt(seconds.getText().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, a);
                    Intent intent = new Intent(AddTask.this, ScheduledNotificationReceiver.class);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("description", description.getText().toString());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTask.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
