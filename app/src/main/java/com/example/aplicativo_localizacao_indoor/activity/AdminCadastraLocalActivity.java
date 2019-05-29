package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCadastraLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_local);

        Button btCadLocal= findViewById(R.id.btCadLocal);

        String[] SETORES = new String[] {"Principal", "Tecnologia em sistemas para Internet", "Design", "Caldelas"};

        final Local local = new Local();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SETORES);
        final AutoCompleteTextView autoCompleteSetor = (AutoCompleteTextView)findViewById(R.id.autoCompleteSetor);
        autoCompleteSetor.setAdapter(adapter);

        final EditText etDescricaoLocal = findViewById(R.id.etDescricaoLocal);
        RadioGroup radioGroupAndar = findViewById(R.id.radioGroupAndar);
        radioGroupAndar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonAndar1:{
                        local.setAndar(1);
                        Log.d("checked", "1 andar");
                        break;
                    }
                    case R.id.radioButtonAndar2:{
                        local.setAndar(2);
                        Log.d("checked", "2 andar");
                        break;
                    }
                    case R.id.radioButtonAndar3:{
                        local.setAndar(3);
                        Log.d("checked", "3 andar");
                        break;
                    }
                }
            }
        });
        btCadLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                local.setCorredor(autoCompleteSetor.getText().toString());
                local.setDescricao(etDescricaoLocal.getText().toString());
                local.setSituacao(true);
                // obtém a referência do database e do nó
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("dados/local");
                myRef.push().setValue(local);
            }
        });

    }
}
