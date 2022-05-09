package com.example.testrealtimeforvinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateTodo extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://signinup-6bab5-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("todo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);

        String txtTitle = getIntent().getExtras().getString("title");
        int id = getIntent().getExtras().getInt("id");

        EditText edtTitle = findViewById(R.id.edtTextChange);
        Button btnUpdate = findViewById(R.id.btnUpdate);

        edtTitle.setText(txtTitle);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(id + "").child("title").setValue(edtTitle.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateTodo.this, "Updated Completed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTodo.this, TodoMain.class);
                        startActivity(intent);
                    }
                });
            }
        });

        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateTodo.this, TodoMain.class);
                startActivity(intent);
            }
        });

    }
}