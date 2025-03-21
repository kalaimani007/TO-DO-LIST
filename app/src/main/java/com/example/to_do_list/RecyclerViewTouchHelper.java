package com.example.to_do_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.Adapter.ToDoAdapter;

public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {

    ToDoAdapter adap;

    public RecyclerViewTouchHelper(ToDoAdapter adap) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adap = adap;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adap.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Äre you Sure");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adap.deleteTask(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adap.notifyItemChanged(position);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            adap.editItem(position);
        }
    }
}
