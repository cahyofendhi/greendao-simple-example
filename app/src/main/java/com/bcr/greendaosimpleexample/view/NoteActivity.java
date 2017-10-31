package com.bcr.greendaosimpleexample.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcr.greendaosimpleexample.App;
import com.bcr.greendaosimpleexample.R;
import com.bcr.greendaosimpleexample.data.DaoSession;
import com.bcr.greendaosimpleexample.data.Note;
import com.bcr.greendaosimpleexample.data.NoteDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by bcr on 10/31/17.
 */

public class NoteActivity extends AppCompatActivity {

    private EditText text;
    private Button save, display, search;
    private TextView result;

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
        result  = findViewById(R.id.txv_result);
        display = findViewById(R.id.btn_display);
        search  = findViewById(R.id.btn_search);
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
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("");
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(text.getText().toString());
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

    /**
     * get data from database Dao
     */
    private void getData(String search){
        QueryBuilder<Note> qb   = null;
        if (search.length() > 0) {
            qb = noteDao.queryBuilder().where(NoteDao.Properties.Text.eq(search));
        } else {
            qb = noteDao.queryBuilder();
        }
        List<Note> noteData = qb.list();
        StringBuilder stringResult  = new StringBuilder();
        for (Note dt: noteData){
            stringResult.append(dt.getText()+" \n");
        }
        result.setText(stringResult.toString());

        if (noteData.size() == 0)Toast.makeText(this, "no result", Toast.LENGTH_LONG).show();
    }

}
