package com.example.aplicativo_localizacao_indoor.activity;


import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Usuario;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
                senha = ((EditText) findViewById(R.id.etPassword)).getText().toString();
                ProgressBar pbLogin = findViewById(R.id.login_progress);
                if (!email.isEmpty() && !senha.isEmpty()) {
                    pbLogin.setVisibility(View.VISIBLE);
                    realizaLogin(email, senha);
                } else {
                    Snackbar.make(findViewById(R.id.container_login_activity), "Preencha todos os campos.", Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(LoginActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void realizaLogin(String etEmail, String etPassword) {
        mAuth.signInWithEmailAndPassword(etEmail, etPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login", "signInWithEmail:success");
                            mAuth = FirebaseAuth.getInstance();
                            final FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("retorno user", user.getEmail());
//                            updateUI(user);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(user.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            AppSetup.usuario = dataSnapshot.getValue(Usuario.class);
//                                            Log.d("usuario", AppSetup.usuario.toString());
                                            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(LoginActivity.this, "Falha na requisição", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            if (Objects.requireNonNull(task.getException()).getMessage().contains("password")) {
                                Toast.makeText(LoginActivity.this, "Senha incorreta.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "E-mail incorreto.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}