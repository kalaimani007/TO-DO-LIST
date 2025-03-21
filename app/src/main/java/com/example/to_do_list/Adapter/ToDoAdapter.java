package com.example.to_do_list.Adapter;
import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.AddNewTask;
import com.example.to_do_list.MainActivity;
import com.example.to_do_list.Model.ToDoModel;
import com.example.to_do_list.R;
import com.example.to_do_list.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> list;
    private MainActivity act;
    private DataBaseHelper db;



    public ToDoAdapter(DataBaseHelper db,MainActivity act){
        this.act = act;
        this.db = db;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = list.get(position);
        holder.cb.setText(item.getTask());
        holder.cb.setChecked(toBoolean(item.getStatus()));
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    db.updateStatus(item.getId(),1);
                }else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });
    }

    private boolean toBoolean(int num) {
        return num!=0;
    }
    public Context getContext(){
        return act;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setTasks(List<ToDoModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item = list.get(position);
        db.deleteTask(item.getId());

        list.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item = list.get(position);
        Bundle b = new Bundle();
        b.putInt("id",item.getId());
        b.putString("task",item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(b);
        task.show(act.getSupportFragmentManager(),task.getTag());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            cb = itemView.findViewById(R.id.checkbox);
        }
    }
}
