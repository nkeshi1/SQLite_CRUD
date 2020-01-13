package com.example.sqlite_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import static com.example.sqlite_login.DatabaseManager.studentInfoEntry.*;

public class SignUpActivity extends AppCompatActivity{
    private DatabaseManager databaseManager;

    private String name, programme, password;
    private int level, id_number;
    private EditText nameText, programmeText, passwordText, levelText, id_numberText;
    private Button signUp;
    private TextView loginPage;

    @Override
    protected void onResume() {
        super.onResume();
        boolean edit = getIntent().getBooleanExtra("edit", false);
        String rowid = getIntent().getStringExtra("rowid");
        if(edit){
            final ArrayList<Map<String, String>> studentRecord = databaseManager.getStudentById(rowid);
            if(studentRecord.size() != 0){
                nameText.setText(studentRecord.get(0).get(STUDENT_NAME));
                programmeText.setText(studentRecord.get(0).get(STUDENT_PROGRAMME));
                passwordText.setText(studentRecord.get(0).get(STUDENT_PASSWORD));
                levelText.setText(studentRecord.get(0).get(STUDENT_LEVEL));
                id_numberText.setText(studentRecord.get(0).get(STUDENT_ID));

                signUp.setText("update record");
                TextView signLabel = findViewById(R.id.signUpLbl);
                signLabel.setText("Update Student Record");
                loginPage.setVisibility(View.GONE);

                signUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name, programme, password, level;
                        name = nameText.getText().toString();
                        programme = programmeText.getText().toString();
                        password = passwordText.getText().toString();
                        level = levelText.getText().toString();
                        StudentDetails studentDetails = new StudentDetails(name, programme, password, Integer.parseInt(level),
                                Integer.parseInt(id_numberText.getText().toString()), studentRecord.get(0).get(ROW_ID));
                        databaseManager.updateStudentRecord(studentDetails);
                        Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp = findViewById(R.id.signUpBtn);
        databaseManager = new DatabaseManager(this);

        id_numberText = findViewById(R.id.indexText);
        programmeText = findViewById(R.id.programmeText);
        levelText = findViewById(R.id.levelText);
        passwordText = findViewById(R.id.passcodeText);
        nameText = findViewById(R.id.nameText);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                id_number = Integer.parseInt(id_numberText.getText().toString());
                programme = programmeText.getText().toString();
                level = Integer.parseInt(levelText.getText().toString());
                password = passwordText.getText().toString();
                StudentDetails studentDetails = new StudentDetails();
                studentDetails.setId_number(id_number);
                studentDetails.setLevel(level);
                studentDetails.setName(name);
                studentDetails.setPassword(password);
                studentDetails.setProgramme(programme);

                databaseManager.addStudents(studentDetails);
                Toast.makeText(getApplicationContext(), "Student Added Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        loginPage = findViewById(R.id.loginPageBtn);
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        databaseManager.close();
        super.onDestroy();
    }
}
