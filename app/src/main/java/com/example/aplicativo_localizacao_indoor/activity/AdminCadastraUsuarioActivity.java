package com.example.aplicativo_localizacao_indoor.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCadastraUsuarioActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etNomeUser, etSobrenomeUser, etEmailUser, etMatriculaUser, etPasswordUser;
    private Spinner spFuncaoUser;
    private Button btCadastrarUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_usuario);

        mAuth = FirebaseAuth.getInstance();

        String[] FUNCAO = new String[]{"Administrador", "Cadastrador"};

        spFuncaoUser =findViewById(R.id.spFuncaoUser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FUNCAO);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFuncaoUser.setAdapter(adapter);

        btCadastrarUser = findViewById(R.id.btCadastrarUser);
        btCadastrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmailUser = findViewById(R.id.etEmailUser);
                etPasswordUser = findViewById(R.id.etPasswordUser);
                singup(etEmailUser.getText().toString(), etPasswordUser.getText().toString());
            }
        });
    }
    private void singup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("user", "createUserWithEmail:success");
                            cadastraUser(mAuth.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("user", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AdminCadastraUsuarioActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void cadastraUser(FirebaseUser firebaseUser) {
        etNomeUser =findViewById(R.id.etNomeUser);
        etSobrenomeUser =findViewById(R.id.etSobrenomeUser);
        etMatriculaUser=findViewById(R.id.etMatriculaUser);
        spFuncaoUser=findViewById(R.id.spFuncaoUser);

        Usuario usuario = new Usuario();
        usuario.setFirebaseUser(firebaseUser);
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(etNomeUser.getText().toString());
        usuario.setSobrenome(etSobrenomeUser.getText().toString());
        usuario.setMatricula(etMatriculaUser.getText().toString());
        usuario.setFuncao(spFuncaoUser.toString());
        usuario.setSituacao(true);
        FirebaseDatabase.getInstance().getReference().child("administradores").child(usuario.getFirebaseUser().getUid()).setValue(usuario);
    }
}
