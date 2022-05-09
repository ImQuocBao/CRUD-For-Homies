package com.example.testrealtimeforvinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TodoMain extends AppCompatActivity {

    ListView lvTodo;
    ArrayList<Todo> arrTodo;
    TodoAdapter adapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://signinup-6bab5-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("todo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_main);
        initUI();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edtTodo = findViewById(R.id.editTextTextPersonName);
                int length = arrTodo.size();

                Todo todo = new Todo(edtTodo.getText().toString(), 0);
                todo.setId(length);

                String pathOject = String.valueOf(todo.getId());

                myRef.child(pathOject).setValue(todo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(TodoMain.this, "Add success", Toast.LENGTH_SHORT).show();
                        loadDataFromFirebase();
                    }
                });
            }
        });
    }

    private void initUI() {
        lvTodo = findViewById(R.id.lvMain);
        arrTodo = new ArrayList<>();

        loadDataFromFirebase();

        adapter = new TodoAdapter(this, R.layout.item_todo, arrTodo);
        lvTodo.setAdapter(adapter);
    }

    private void loadDataFromFirebase () {
        // Load data to arrTodo/ Async
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Todo> listTodo = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Todo todo = data.getValue(Todo.class);
                    listTodo.add(todo);
                }
                arrTodo.clear();
                arrTodo.addAll(listTodo);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}