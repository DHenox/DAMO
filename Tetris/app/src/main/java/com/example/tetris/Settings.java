package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    int gameManager;
    Spinner dropdown;
    Switch saveSwitch;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        boolean saveGame = saveSwitch.isChecked();
        ((DataManager) getApplication()).setUserWantsToSave(saveGame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        saveSwitch = ((Switch) findViewById(R.id._saveSwitch));

        String[] items = new String[]{"Preferences", "Files", "SQL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown = ((Spinner) findViewById(R.id._managerSpinner));
        dropdown.setAdapter(adapter);

        gameManager = dropdown.getSelectedItemPosition();
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                boolean saveGame = saveSwitch.isChecked();
                if(saveGame) {
                    ((DataManager) getApplication()).setTetrisManager((int) l);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}