package com.example.sqlite_login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<StudentDetails> {

    public StudentAdapter(Context context, List<StudentDetails> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final StudentDetails studentDetails = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_details_layout, parent, false);

        TextView rowid = convertView.findViewById(R.id.rowid);
        TextView id = convertView.findViewById(R.id.ID);
        TextView name = convertView.findViewById(R.id.name);
        TextView programme = convertView.findViewById(R.id.Programme);
        TextView level = convertView.findViewById(R.id.level);
        Button edit = convertView.findViewById(R.id.editBtn);
        Button delete = convertView.findViewById(R.id.deleteBtn);

        final DatabaseManager databaseManager = new DatabaseManager(getContext());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), SignUpActivity.class).putExtra("edit", true)
                .putExtra("rowid", studentDetails.getRow_id()));
                //Toast.makeText(getContext(), "Edit at position "+getPosition(studentDetails), Toast.LENGTH_LONG).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                databaseManager.deleteStudent(studentDetails.getRow_id());
                Toast.makeText(getContext(), "Student Deleted!", Toast.LENGTH_LONG).show();
            }
        });

        rowid.setText(studentDetails.getRow_id());
        id.setText(String.valueOf(studentDetails.getId_number()));
        name.setText(studentDetails.getName());
        programme.setText(studentDetails.getProgramme());
        level.setText(String.valueOf(studentDetails.getLevel()));

        return convertView;
    }
}
