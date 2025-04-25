package com.example.essatconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private final List<Student> studentsList;

    public StudentsAdapter(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentsList.get(position);
        holder.fullnameTextView.setText(student.getFullname());
        holder.emailTextView.setText(student.getEmail());
        holder.studentClassTextView.setText(student.getStudentClass());
        holder.cinTextView.setText(student.getCin());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView fullnameTextView, emailTextView, studentClassTextView, cinTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            fullnameTextView = itemView.findViewById(R.id.studentFullname);
            emailTextView = itemView.findViewById(R.id.studentEmail);
            studentClassTextView = itemView.findViewById(R.id.studentClass);
            cinTextView = itemView.findViewById(R.id.studentCin);
        }
    }
}
