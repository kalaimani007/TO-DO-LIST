package com.example.to_do_list;

import static android.graphics.Color.GRAY;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_do_list.Model.ToDoModel;
import com.example.to_do_list.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String ad = "AddNewTask";
    private EditText et;
    private Button btn;
    private DataBaseHelper db;
    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_task , container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et = view.findViewById(R.id.editText);
        btn = view.findViewById(R.id.save);

        db = new DataBaseHelper((getActivity()));

        boolean isUpdate = false;
        Bundle b =getArguments();
        if(b!=null){
            isUpdate = true;
            String task = b.getString("task");
            et.setText(task);

            if(task.length()>0){
                btn.setEnabled(false);
            }
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    btn.setEnabled(false);
                    btn.setBackgroundColor(Color.GRAY);
                }else {
                    btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        boolean finalIsUpdate = isUpdate;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                if(finalIsUpdate){
                    db.UpdateTask(b.getInt("id"),text);
                }else {
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    db.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity act = getActivity();
        if(act instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)act).onDialogClose(dialog);
        }
    }
}
