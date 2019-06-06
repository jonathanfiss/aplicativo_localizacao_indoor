package com.example.aplicativo_localizacao_indoor.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;

import java.util.ArrayList;

public class TesteActivity extends AppCompatActivity {
    private String [] nomes = {"teste1","teste2","teste3","teste4","teste5","teste6","teste7","teste8","teste9","teste10","teste11"};
    private ListView lvTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        lvTeste = findViewById(R.id.lv_teste);
        lvTeste.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<String>()));

        new MyTask().execute();
    }
    class MyTask extends AsyncTask<Void, String, String>{
        ArrayAdapter<String> adapter;
        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>)lvTeste.getAdapter();
        }

        @Override
        protected String doInBackground(Void... voids) {
            for (String nm : nomes){
                publishProgress(nm);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "todos os nomes";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }



}

