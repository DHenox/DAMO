package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    int gameManagerDropDown;
    int gameManagerItem;
    Spinner dropdown;
    Switch saveSwitch;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        boolean saveGame = saveSwitch.isChecked();
        ((DataManager) getApplication()).setUserWantsToSave(saveGame);

        if(saveGame){
            ((DataManager) getApplication()).setTetrisManager(gameManagerItem);
        }
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

        gameManagerDropDown = dropdown.getSelectedItemPosition();
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gameManagerItem = (int)l;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}