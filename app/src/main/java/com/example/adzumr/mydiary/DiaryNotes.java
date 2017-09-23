package com.example.adzumr.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DiaryNotes extends AppCompatActivity {

    public static final String DiaryFile = "Diary.txt";
    public static final String DebugTag = "DiaryDebug";
    public static final String FileSaved = "filesaved";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_notes);

        saveButtonListener();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean filesaved = sharedPreferences.getBoolean(FileSaved, false);

        if (filesaved) {
            readSavedFile();
        }

        readSavedFile();
    }

    //    This Method Read The File Save In Internal Storage By The User And Display The Content
    private void readSavedFile() {
        try {
            FileInputStream fileInputStream = openFileInput(DiaryFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));

            EditText diaryText = (EditText) findViewById(R.id.diary_editText);
            String line;

//            Read the saved file line by line
            while ((line = reader.readLine()) != null) {
                diaryText.append(line);
                diaryText.append("\n");

            }
            fileInputStream.close();
        } catch (Exception e) {
            Log.d(DebugTag, "Unable to read saved file!");
        }
    }

    //    This Method is calle when save_Button Is clicked By the User
    private void saveButtonListener() {
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.Save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                When Save_Button is clicked get the content of diaty_editText filed
                EditText diaryText = (EditText) findViewById(R.id.diary_editText);
                String editText = diaryText.getText().toString();

//                Save The Content Of diary_editText to internal storage
                try {
                    FileOutputStream fileOutputStream = openFileOutput(DiaryFile, Context.MODE_PRIVATE);
                    fileOutputStream.write(editText.getBytes());
                    fileOutputStream.close();

//                  Saved File To Preference
                    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(FileSaved, true);
                    editor.apply();

//                  Dialog To Notify The User That His Note Is Successfully Saved!
                    Toast.makeText(DiaryNotes.this, "Note Saved!", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Log.d(DebugTag, "Unable to Save!");
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_diary_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reset_password_ID:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
