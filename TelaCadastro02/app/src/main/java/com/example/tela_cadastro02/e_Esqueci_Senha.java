package com.example.tela_cadastro02;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class e_Esqueci_Senha extends AppCompatActivity {
    EditText email;
    private static FirebaseAuth fireAuth = FirebaseAuth.getInstance();      //Firebase - Autenticação

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_esqueci_senha);

        email = (EditText) findViewById(R.id.esqueci_Senha_Email_Xml);
    }

    public void bt_redefinirSenha_Xml (View view) {
        fireAuth.sendPasswordResetEmail(email.getText().toString())
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(e_Esqueci_Senha.this, "Email Enviado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

