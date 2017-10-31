package com.bcr.greendaosimpleexample.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bcr.greendaosimpleexample.App;
import com.bcr.greendaosimpleexample.R;
import com.bcr.greendaosimpleexample.data.DaoSession;
import com.bcr.greendaosimpleexample.data.Note;
import com.bcr.greendaosimpleexample.data.NoteDao;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private EditText text;
    private Button save;

    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setupUI();
        setupGreenDao();
        viewActivity();
    }

    private void setupUI() {
        text    = findViewById(R.id.edt_text);
        save    = findViewById(R.id.btn_save);
    }

    private void setupGreenDao() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();
    }

    private void viewActivity() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    /**
     * save data to database Dao
     */
    private void saveData(){
        Note note = new Note();
        note.setText(text.getText().toString());
        note.setDate(new Date());
        noteDao.insert(note);

        text.setText("");
        Toast.makeText(this, "Inserted new note, ID: " + note.getId(), Toast.LENGTH_LONG).show();
    }
}
