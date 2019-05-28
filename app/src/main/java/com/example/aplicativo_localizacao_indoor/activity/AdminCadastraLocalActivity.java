package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aplicativo_localizacao_indoor.R;

public class AdminCadastraLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_local);

        RadioGroup radioGroupAndar = findViewById(R.id.radioGroupAndar);
        Button btCadLocal= findViewById(R.id.btCadLocal);

        String[] SETORES = new String[] {"Principal", "Tecnologia em sistemas para Internet", "Design", "Caldelas"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SETORES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteSetor);
        textView.setAdapter(adapter);


        radioGroupAndar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonAndar1:{
                        Log.d("checked", "1 andar");
                        break;
                    }
                    case R.id.radioButtonAndar2:{
                        Log.d("checked", "2 andar");
                        break;
                    }
                    case R.id.radioButtonAndar3:{
                        Log.d("checked", "3 andar");
                        break;
                    }
                }
            }
        });
        btCadLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
