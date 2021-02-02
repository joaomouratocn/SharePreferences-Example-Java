package br.com.programadorjm.sharepreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String preferences = "preferences"; //nome do aquivo sharePreferences
    TextView ed_msg, txt_duration;
    SeekBar seek_ms;
    SwitchCompat switch_enable;
    Button btn_save, btn_reset, btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pegando views da activity
        ed_msg = findViewById(R.id.edt_message);
        seek_ms = findViewById(R.id.seek_duration);
        txt_duration = findViewById(R.id.txt_duration);
        switch_enable = findViewById(R.id.switch_enable);

        //stanciando sharePreferences
        sharedPreferences = getSharedPreferences(preferences, MODE_PRIVATE);

        //Carregando prefencias, ao abrir activity
        ed_msg.setText(sharedPreferences.getString("MSG", ""));//no metodo get passa-se a chave do valor da preferencia e um valor default caso seje nulo
        seek_ms.setProgress(sharedPreferences.getInt("DURATION", 0)); //passando valor sharePreference dentro da String format
        txt_duration.setText(getString(R.string.strMs, sharedPreferences.getInt("DURATION", 0)*1000));
        switch_enable.setChecked(sharedPreferences.getBoolean("ENABLE", false));

        btn_save = findViewById(R.id.btn_save);

        //salva preferencias
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                preferencesEditor.putString("MSG", ed_msg.getText().toString());
                preferencesEditor.putInt("DURATION", seek_ms.getProgress());
                preferencesEditor.putBoolean("ENABLE", switch_enable.isChecked());
                preferencesEditor.apply();//salva os dados no arquivo

                //estancia manitpulação do teclado
                View view = MainActivity.this.getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                Toast.makeText(v.getContext(), getString(R.string.strPrefSaved), Toast.LENGTH_SHORT).show();
            }
        });

        btn_reset = findViewById(R.id.btn_reset);
        //limpando a activity
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_msg.setText("");
                seek_ms.setProgress(0);
                txt_duration.setText(getString(R.string.strMs, 0));
                switch_enable.setChecked(false);
            }
        });

        btn_clear = findViewById(R.id.btn_clear);
        //limpando as preferencias
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                preferencesEditor.clear();
                preferencesEditor.apply();
            }
        });

        //caso de restart activity, verifica se tem preferencias a salvas no OnPause da activity
        if (savedInstanceState != null){
            String msg = savedInstanceState.getString("MSG");
            if (!msg.isEmpty()){
                ed_msg.setText(savedInstanceState.getString("MSG"));
                seek_ms.setProgress(savedInstanceState.getInt("DURATION"));
                switch_enable.setChecked(savedInstanceState.getBoolean("ENABLE"));
            }
        }

        //seekbar listener
        seek_ms.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //evento chamdo a cada mudança de estado
                txt_duration.setText(getString(R.string.strMs, progress * 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //evento chamado ao tocar no seekbar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //evento chamado ao fim toque no seekbar
            }
        });
    }

    // No on pause da activity, implementa metodo de salvamento, para salvamento automatico
    @Override
    protected void onPause() {
        super.onPause();
        //SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        //preferencesEditor.putString("MSG", ed_msg.getText().toString());
        //preferencesEditor.putInt("DURATION", seek_ms.getProgress());
        //preferencesEditor.putBoolean("ENABLE", switch_enable.isChecked());
        //preferencesEditor.apply();//salva os dados no arquivo
    }
}