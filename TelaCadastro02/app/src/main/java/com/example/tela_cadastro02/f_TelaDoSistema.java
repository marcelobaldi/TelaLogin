package com.example.tela_cadastro02;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.internal.kx;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class f_TelaDoSistema extends AppCompatActivity {
    private static FirebaseAuth      fireAuth     = FirebaseAuth.getInstance();                     //Firebase - Autenticação;
    private static FirebaseDatabase  fireDatabase = FirebaseDatabase.getInstance();                 //Firebase - Banco Dados;
    EditText                         logado, id, cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_tela_do_sistema);

        logado   = (EditText) findViewById(R.id.LogadoFirebase);
        id       = (EditText) findViewById(R.id.IdFirebase);
        cadastro = (EditText) findViewById(R.id.CadastroFirebase);

        //Verificar Se o Usuário Esta Logado no Firebase
        if (fireAuth.getCurrentUser() != null) {
            logado.setText("Usuário Logado no Firebase");
        } else {
            logado.setText("Usuário Não Está Logado no Firebase");
        }

        //Pegar o ID do Usuário No Firebase (Módulo Autenticação) / Converter Para Texto / Colocar Na Caixa de Entrada
        String usuarioX = String.valueOf(fireAuth.getCurrentUser().getUid());
        id.setText(usuarioX);

        //Verificar a Rua do Usuário No Firebase (Módulo Banco de Dados)
        DatabaseReference PegarDados = fireDatabase.getReference("Clientes").child(usuarioX);       //Ir Para o Nó do Usuário
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ruaX = dataSnapshot.child("Rua").getValue().toString();
                cadastro.setText(ruaX);                                                              //colocar na Caixa de Entrada
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        PegarDados.addValueEventListener(eventListener);


        //Deslogar Design
        Button botao = (Button) findViewById(R.id.btT_Logout_Xml);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireAuth.signOut();                                         //Deslogar Usuário
                if (fireAuth.getCurrentUser() != null) {                    //Verificar Se Deslogou
                    Toast.makeText(f_TelaDoSistema.this, "Ainda Logado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(f_TelaDoSistema.this, "Deslogado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(f_TelaDoSistema.this, b_Login_Email_Rodar.class);
                    startActivity(intent);
                    finish();   //fecha tela atual
                }
            }
        });

    }
}

