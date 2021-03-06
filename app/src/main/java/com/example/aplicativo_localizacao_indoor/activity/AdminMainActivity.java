package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btCadastrarPonto, btCadastrarLocal, btCadastrarSalas, btCadUsuario;
    private TextView tvUsuarioEmail, tvUsuarioNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerMain = navigationView.getHeaderView(0);
        tvUsuarioEmail = headerMain.findViewById(R.id.tvEmailUsuario);
        tvUsuarioNome = headerMain.findViewById(R.id.tvUsuarioNome);

//        tvUsuarioEmail.setText(AppSetup.usuario.getNome().concat(" ").concat(AppSetup.usuario.getSobrenome()));
//        tvUsuarioNome.setText(AppSetup.usuario.getFuncao());
        tvUsuarioEmail.setText("Área Administrativa");
        tvUsuarioNome.setText("IFlocaliza");

        btCadastrarPonto = findViewById(R.id.btCadastrarPonto);
        btCadastrarLocal = findViewById(R.id.btCadastrarLocal);
        btCadastrarSalas = findViewById(R.id.btCadastrarSala);
        btCadUsuario = findViewById(R.id.btCadUsuario);

        btCadastrarPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraPontoActivity.class));
            }
        });
        btCadastrarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraLocalActivity.class));
            }
        });
        btCadastrarSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraSalaActivity.class));
            }
        });
        btCadUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraUsuarioActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_cad_access_point: {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraPontoActivity.class));
                Log.d("click", "AdminCadastraPontoActivity");
                break;
            }
            case R.id.nav_cad_local: {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraLocalActivity.class));
                Log.d("click", "AdminCadastraLocalActivity");
                break;
            }

            case R.id.nav_cad_sala: {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraSalaActivity.class));
                Log.d("click", "AdminCadastraSalaActivity");
                break;
            }
            case R.id.nav_list_access_point: {
                startActivity(new Intent(AdminMainActivity.this, AdminListarPontoActivity.class));
                Log.d("click", "AdminListarPontoActivity");
                break;
            }
            case R.id.nav_list_local: {
                startActivity(new Intent(AdminMainActivity.this, AdminListarLocaisActivity.class));
                Log.d("click", "AdminListarLocaisActivity");
                break;
            }
            case R.id.nav_list_sala: {
                startActivity(new Intent(AdminMainActivity.this, AdminListarSalasActivity.class));
                Log.d("click", "AdminListarSalasActivity");
                break;
            }
            case R.id.nav_add_usuario: {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastraUsuarioActivity.class));
                Log.d("click", "AdminCadastraUsuarioActivity");
                break;
            }
            case R.id.nav_list_usuario: {
                startActivity(new Intent(AdminMainActivity.this, AdminListarUsuariosActivity.class));
                Log.d("click", "AdminListarUsuariosActivity");
                break;
            }
            case R.id.nav_sair: {
//                AppSetup.usuario.getFirebaseUser(). deslogar
                finish();
                break;
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
