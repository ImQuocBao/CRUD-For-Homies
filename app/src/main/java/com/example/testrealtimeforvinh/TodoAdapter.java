package com.example.testrealtimeforvinh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

public class TodoAdapter extends BaseAdapter {
    private Context context;
    private int Layout;
    private List<Todo> todoList;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://signinup-6bab5-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("todo");

    public TodoAdapter(Context context, int layout, List<Todo> todoList) {
        this.context = context;
        Layout = layout;
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(Layout, null);

        TextView txtTilte = view.findViewById(R.id.textView);
        Todo item = todoList.get(i);
        txtTilte.setText(item.getTitle());

        // random color
        Random rnd = new Random();
        ConstraintLayout ctl = view.findViewById(R.id.layoutMain);
        // Add Switch Change
        Switch btnDone = view.findViewById(R.id.switch1);
        btnDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    todoList.get(i).setStatus(1);
                    myRef.child(todoList.get(i).getId() +"").child("status").setValue(1);
                    ctl.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                } else {
                    todoList.get(i).setStatus(0);
                    myRef.child(todoList.get(i).getId() +"").child("status").setValue(0);
                    ctl.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        if(item.getStatus() == 1) {
            ctl.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            btnDone.setChecked(true);
        } else {
            ctl.setBackgroundColor(Color.TRANSPARENT);
            btnDone.setChecked(false);
        }

        //Button remove
        ImageButton btnRemove = view.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(todoList.get(i).getId() + "").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Remove Success", "");
                            Toast.makeText(context, "Remove Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Error something wrong!!!", "");
                        }
                    }
                });
            }
        });


        // Button Update
        ImageView btnUpdate = view.findViewById(R.id.btnChange);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateTodo.class);

                Bundle b = new Bundle();
                b.putInt("id", todoList.get(i).getId());
                b.putString("title", todoList.get(i).getTitle());
                intent.putExtras(b);

                context.startActivity(intent);
            }
        });

        return view;
    }
}
