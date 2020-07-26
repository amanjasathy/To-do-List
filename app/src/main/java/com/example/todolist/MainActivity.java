package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote=findViewById(R.id.add);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);//for getting databack
            }
        });

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter=new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(this.getApplication()))
                .get(NoteViewModel.class);
         noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
             @Override
             public void onChanged(List<Note> notes) {
                 //update Recycler View
                 adapter.setNotes(notes);
                 //Toast.makeText(MainActivity.this,"onChanged",Toast.LENGTH_SHORT).show();
             }
         });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK)
        {
            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description=data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note note =new Note(title,description,priority);
            noteViewModel.insert(note);

            Toast.makeText(this,"NOTE SAVED",Toast.LENGTH_SHORT).show();
        }
        else {
            //when we not save and move back
            Toast.makeText(this,"NOTE NOT SAVED",Toast.LENGTH_SHORT).show();
        }
    }
}