package com.example.sqlite_login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sqlite_login.DatabaseManager.studentInfoEntry.*;

public final class DatabaseManager extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "studentsDetails.db";
    private static final int DATABASE_VERSION = 1;
    private int rowIdPos, idPos, namePos, levelPos, progPos, passwordPos;
    private SQLiteDatabase sqLiteDatabase;
    public static String[] columns;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(studentInfoEntry.TABLECREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public final class studentInfoEntry{
        public static final String TABLE_NAME = "student_details";
        public static final String STUDENT_ID = "studentID";
        public static final String ROW_ID = "_id";
        public static final String STUDENT_NAME = "student_name";
        public static final String STUDENT_PROGRAMME = "student_programme";
        public static final String STUDENT_LEVEL = "student_level";
        public static final String STUDENT_PASSWORD = "student_password";

        private static final String TABLECREATION = "CREATE TABLE "+TABLE_NAME+" (" +
                ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                STUDENT_ID + " INTEGER NOT NULL,"+
                STUDENT_NAME +" TEXT NOT NULL,"+
                STUDENT_PROGRAMME +" TEXT NOT NULL,"+
                STUDENT_LEVEL +" TEXT NOT NULL,"+
                STUDENT_PASSWORD +" TEXT NOT NULL);";
    }

    public void deleteStudent(String rowid){
        sqLiteDatabase = getWritableDatabase();
        String clause = ROW_ID + " = ?";
        String[] args = {rowid};
        sqLiteDatabase.delete(TABLE_NAME, clause, args);
    }

    public void updateStudentRecord(StudentDetails studentDetails){
        sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, studentDetails.getName());
        values.put(STUDENT_LEVEL, studentDetails.getLevel());
        values.put(STUDENT_PROGRAMME, studentDetails.getProgramme());
        values.put(STUDENT_PASSWORD, studentDetails.getPassword());
        sqLiteDatabase.update(TABLE_NAME, values, ROW_ID +" = ? ", new String[]{studentDetails.getRow_id()});
    }

    public ArrayList<Map<String, String>> getStudentById(String rowid){
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{ROW_ID, STUDENT_ID, STUDENT_NAME, STUDENT_LEVEL, STUDENT_PROGRAMME, STUDENT_PASSWORD},
                ROW_ID +" = ?", new String[]{rowid}, null, null, null);

        Map<String, String> studentRecord = new HashMap<>();
        ArrayList<Map<String, String>> studentDetail = new ArrayList<>();
        rowIdPos = cursor.getColumnIndex(ROW_ID);
        idPos = cursor.getColumnIndex(STUDENT_ID);
        namePos = cursor.getColumnIndex(STUDENT_NAME);
        levelPos = cursor.getColumnIndex(STUDENT_LEVEL);
        progPos = cursor.getColumnIndex(STUDENT_PROGRAMME);
        passwordPos = cursor.getColumnIndex(STUDENT_PASSWORD);

        if(cursor.moveToFirst()){
            studentRecord.put(ROW_ID, cursor.getString(rowIdPos));
            studentRecord.put(STUDENT_ID, cursor.getString(idPos));
            studentRecord.put(STUDENT_NAME, cursor.getString(namePos));
            studentRecord.put(STUDENT_PROGRAMME, cursor.getString(progPos));
            studentRecord.put(STUDENT_LEVEL, cursor.getString(levelPos));
            studentRecord.put(STUDENT_PASSWORD, cursor.getString(passwordPos));

            studentDetail.add(studentRecord);
        }
        cursor.close();
        return studentDetail;
    }

    public ArrayList<StudentDetails> getStudentsDetails(){
        sqLiteDatabase = getReadableDatabase(); //We are getting a readable reference to the database
        //Columns we want to get from the database shouls be in a String arary
        columns = new String[]{ROW_ID, STUDENT_ID, STUDENT_NAME, STUDENT_LEVEL, STUDENT_PASSWORD, STUDENT_PROGRAMME};

        //Cursor is used to know the current position of the row in the database...
        Cursor cursor = sqLiteDatabase.query(true, TABLE_NAME, columns, null, null, null, null, null, null);

        //Getting the postions of every column to be able to loop through
        rowIdPos = cursor.getColumnIndex(ROW_ID);
        idPos = cursor.getColumnIndex(STUDENT_ID);
        namePos = cursor.getColumnIndex(STUDENT_NAME);
        levelPos = cursor.getColumnIndex(STUDENT_LEVEL);
        progPos = cursor.getColumnIndex(STUDENT_PROGRAMME);
        passwordPos = cursor.getColumnIndex(STUDENT_PASSWORD);

        ArrayList<StudentDetails> studentDetails = new ArrayList<>();

        while(cursor.moveToNext()){
            StudentDetails details = new StudentDetails();
            details.setRow_id(cursor.getString(rowIdPos));
            details.setName(cursor.getString(namePos));
            details.setId_number(cursor.getInt(idPos));
            details.setLevel(cursor.getInt(levelPos));
            details.setProgramme(cursor.getString(progPos));
            details.setPassword(cursor.getString(passwordPos));

            studentDetails.add(details);
        }
        cursor.close();
        return studentDetails;
    }

    public void addStudents(StudentDetails studentDetails){
        ContentValues values = new ContentValues();
        values.put(STUDENT_ID, studentDetails.getId_number());
        values.put(STUDENT_LEVEL, studentDetails.getLevel());
        values.put(STUDENT_NAME, studentDetails.getName());
        values.put(STUDENT_PASSWORD, studentDetails.getPassword());
        values.put(STUDENT_PROGRAMME, studentDetails.getProgramme());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }
}
