package com.example.sqlite_login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sqlite_login.DatabaseManager.studentInfoEntry.*;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private SQLiteDatabase db;
    private String id_number;
    private String password;
    private ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseManager = new DatabaseManager(this);

        ListView students = findViewById(R.id.studentList);
        String[] from = new String[]{STUDENT_ID, STUDENT_NAME, STUDENT_LEVEL, STUDENT_PROGRAMME};
        int[] to = new int[]{R.id.ID, R.id.name, R.id.level, R.id.Programme};
        StudentAdapter adapter = new StudentAdapter(this, databaseManager.getStudentsDetails());
        //SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.student_details_layout, cursor, from, to);
        students.setAdapter(adapter);

        EditText indexNumberText = findViewById(R.id.indexNumberText);
        id_number = indexNumberText.getText().toString();

        TextView signUpLink = findViewById(R.id.signUpText);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        EditText passwordText = findViewById(R.id.passwordText);
        password = passwordText.getText().toString();

        Button loginBtn = findViewById(R.id.loginBtn);

    }

    @Override
    protected void onDestroy() {
        databaseManager.close();
        super.onDestroy();
    }


}
