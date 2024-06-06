package com.example.tuantestbailuutrutt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText titleEditText;
    private EditText contentEditText;
    private long noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        dbHelper = new DatabaseHelper(this);

        titleEditText = findViewById(R.id.edit_text_title);
        contentEditText = findViewById(R.id.edit_text_content);
        Button saveButton = findViewById(R.id.button_save);

        Intent intent = getIntent();
        noteId = intent.getLongExtra("id", -1);

        if (noteId != -1) {
            loadNoteData();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                finish();
            }
        });
    }

    private void loadNoteData() {
        Cursor cursor = dbHelper.getAllNotes();
        if (cursor.moveToFirst()) {
            titleEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_TITLE)));
            contentEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_CONTENT)));
        }
        cursor.close();
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (noteId == -1) {
            dbHelper.addNote(title, content);
        } else {
            dbHelper.updateNote(noteId, title, content);
        }

    }
}