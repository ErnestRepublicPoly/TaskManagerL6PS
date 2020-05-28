package sg.edu.rp.c346.taskmanagerl6ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> alTasks;
    CustomTaskList caTask;
    ListView lvTasks;
    Button btnAdd;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(MainActivity.this);

        lvTasks = findViewById(R.id.lvTask);
        btnAdd = findViewById(R.id.buttonAdd);
        alTasks = new ArrayList<>();
        alTasks.addAll(dbHelper.getAllTasks());

        caTask = new CustomTaskList(this, R.layout.row, alTasks);
        lvTasks.setAdapter(caTask);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                alTasks.clear();
                DBHelper dbh = new DBHelper(MainActivity.this);
                alTasks.addAll(dbh.getAllTasks());
                caTask.notifyDataSetChanged();
                dbh.close();
            }
        }
    }
}
