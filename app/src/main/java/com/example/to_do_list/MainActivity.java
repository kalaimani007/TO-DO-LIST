package com.example.to_do_list;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.Adapter.ToDoAdapter;
import com.example.to_do_list.Model.ToDoModel;
import com.example.to_do_list.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{
    RecyclerView rv;
    FloatingActionButton fa;
    DataBaseHelper mydb;
    private List<ToDoModel> list;
    private ToDoAdapter adap;
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rv = findViewById(R.id.recyclerView);
        fa = findViewById(R.id.addButton);
        mydb = new DataBaseHelper(MainActivity.this);
        list = new ArrayList<>();
        adap = new ToDoAdapter(mydb,MainActivity.this);


        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adap);

        list = mydb.getAllTasks();
        Collections.reverse(list);
        adap.setTasks(list);

        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.ad);
            }
        });
        ItemTouchHelper it = new ItemTouchHelper(new RecyclerViewTouchHelper(adap));
        it.attachToRecyclerView(rv);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialog) {
        list = mydb.getAllTasks();
        Collections.reverse(list);
        adap.setTasks(list);
        adap.notifyDataSetChanged();
    }
}