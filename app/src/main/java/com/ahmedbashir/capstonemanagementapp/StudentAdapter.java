package com.ahmedbashir.capstonemanagementapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Model.Project;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed on 2/16/2018.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentVH>{


    private List<Project> projects;
    private Context context;

    @Override
    public StudentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_home_list,parent,false);
        return new StudentVH(view);
    }

    @Override
    public void onBindViewHolder(final StudentVH holder, int position) {
        final Project project = projects.get(position);
        holder.projectTitle.setText(project.getProjectTitle());
        holder.projectSupervisor.setText(project.getProjectSupervisor());
        holder.showProjectDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You Clicked " + project.getProjectTitle(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public StudentAdapter(List<Project> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }

    public class StudentVH extends RecyclerView.ViewHolder{

        public TextView projectTitle;
        public TextView projectSupervisor;
        public Button showProjectDetailButton;

        public StudentVH(View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.project_title);
            projectSupervisor = itemView.findViewById(R.id.supervisorName);
            showProjectDetailButton = itemView.findViewById(R.id.button_show_project_details);
        }
    }
}
