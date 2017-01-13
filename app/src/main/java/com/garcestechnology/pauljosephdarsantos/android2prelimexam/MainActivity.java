package com.garcestechnology.pauljosephdarsantos.android2prelimexam;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.garcestechnology.pauljosephdarsantos.android2prelimexam.data.NoteItem;
import com.garcestechnology.pauljosephdarsantos.android2prelimexam.data.NotesDataSource;

import java.util.List;

public class MainActivity extends ListActivity {

    private NotesDataSource datasource;
    List<NoteItem> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new NotesDataSource(this);

        refreshDisplay();

        Button addTodo = (Button) findViewById(R.id.btnAddTodo);
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteItem note = NoteItem.getNew();
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                intent.putExtra("key", note.getKey());
                intent.putExtra("text", note.getText());
                startActivityForResult(intent, 1001);
            }
        });
    }

    private void refreshDisplay() {
        notesList = datasource.findAll();
        ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notesList);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            NoteItem note = new NoteItem();
            note.setKey(data.getStringExtra("key"));
            note.setText(data.getStringExtra("text"));
            datasource.update(note);
            refreshDisplay();

        }
    }
}
