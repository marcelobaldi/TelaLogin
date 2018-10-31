package com.example.tela_cadastro02;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class c_Cadastro_Email_Rodar extends AppCompatActivity {                                     //Classe - Abrir Tela
    private        EditText                    nome, email, senha1, senha2;                         //Objetos Tela - Declarar
    private c_Cadastro_Email_Campos            CadastroEmailCampos;                                 //Classe Campos da Tela;
    private static FirebaseAuth                fireAuth     = FirebaseAuth.getInstance();           //Firebase - Autenticação;
    private static FirebaseDatabase            fireDatabase = FirebaseDatabase.getInstance();       //Firebase - Banco Dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                            //Método Carregado com a Classe;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_cadastro_email);                                                  //Tela Carregada

        nome    = (EditText) findViewById(R.id.b_Cadastro_Email_Nome_Xml);                          //Objetos Tela - Identificar;
        email   = (EditText) findViewById(R.id.b_Cadastro_Email_Email_Xml);
        senha1  = (EditText) findViewById(R.id.b_Cadastro_Email_Senha1_Xml);
        senha2  = (EditText) findViewById(R.id.b_Cadastro_Email_Senha2_Xml);
    }

    public void b_Cadastro_Email_Cadastrar_Xml (View view) {                                        //Botão de Comando Cadastrar
        String nomeX    = nome.getText().toString();                                                //Pegar Valores das Caixas;
        String emailX   = email.getText().toString();
        String senha1X  = senha1.getText().toString();
        String senha2X  = senha2.getText().toString();

        CadastroEmailCampos = new c_Cadastro_Email_Campos(nomeX, emailX, senha1X, senha2X);          //Colocar Valores na Classe Campos;

        if(CadastroEmailCampos.getNome().equals("")) {                                               //Tratamento de Campos em Branco;
            Toast.makeText(this, "Digite seu Nome", Toast.LENGTH_LONG).show();
        } else if (CadastroEmailCampos.getEmail().equals("")) {
            Toast.makeText(this, "Digite seu Email", Toast.LENGTH_LONG).show();
        } else if (CadastroEmailCampos.getSenha1().equals("")) {
            Toast.makeText(this, "Digite sua Senha", Toast.LENGTH_LONG).show();
        } else if (CadastroEmailCampos.getSenha2().equals("")) {
            Toast.makeText(this, "Digite Novamente a Senha", Toast.LENGTH_LONG).show();
        } else {
            ConferirTamanhoSenha();                                                                 //Conferir Senha (> 6 Caracteres);
        }
    }

    public void ConferirTamanhoSenha() {                                                            //Conferir Senha (> 6 Caracteres);
        if(CadastroEmailCampos.getSenha1().length() < 6) {
            Toast.makeText(this, "A Senha Deve Ter No Mínimo 6 Caracteres", Toast.LENGTH_LONG).show();
        } else {
            ConferirValorSenha();
        }
    }

    public void ConferirValorSenha() {                                                              //Conferir Senha (Senha1 e Senha2)
        if (CadastroEmailCampos.getSenha1().equals(CadastroEmailCampos.getSenha2())) {
            CadastrarClienteEmail();                                                                     //Método para Cadastrar Usuário
        } else {
            Toast.makeText(this, "A Senha Esta Errada", Toast.LENGTH_LONG).show();
        }
    }

    public void CadastrarClienteEmail() {                                                                //Método para Cadastrar Usuário
        fireAuth.createUserWithEmailAndPassword(CadastroEmailCampos.getEmail(), CadastroEmailCampos.getSenha1())      //Email e Senha;
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {                                //Se Cadastro OK
                if (task.isSuccessful()) {

                    //Informar o Usuário que o cadastro foi com sucesso
                    Toast.makeText(c_Cadastro_Email_Rodar.this, "Usuário Cadastrado", Toast.LENGTH_LONG).show();

                    //Pegar o ID do Usuário do Firebase (Gerado Pelo Próprio Firebase No Módulo Authentication)
                    FirebaseUser userFirebase = task.getResult().getUser();

                    //Colocar o Id do Usuário do Firebase na Classe Campos
                    CadastroEmailCampos.setId(userFirebase.getUid());

                    //Cadastrar o Usuário Também no Banco de Dados
                    DatabaseReference usuario = fireDatabase.getReference("Clientes").child(CadastroEmailCampos.getId());   //O Nó do Cliente é o ID do Usuário do Firebase
                        usuario.child("ID").setValue(CadastroEmailCampos.getId());
                        usuario.child("Nome").setValue(CadastroEmailCampos.getNome());
                        usuario.child("Email").setValue(CadastroEmailCampos.getEmail());
                        usuario.child("Senha").setValue(CadastroEmailCampos.getSenha1());

                    //Pegar os Dados do Usuário, e Levar Para A Tela de Cadastro de Endereço deste Usuário;
                    Intent intent = new Intent(c_Cadastro_Email_Rodar.this, d_Cadastro_End_Rodar.class);
                    intent.putExtra("DadosUsuárioEmail", CadastroEmailCampos);
                    startActivity(intent);

                } else {                                                                            //Se Erro no Cadastro

                    try {
                        throw  task.getException();                                                 //Trat. Erro
                    } catch (FirebaseAuthUserCollisionException e) {                                //Email Já Cadastrado no Firebase
                        Toast.makeText(c_Cadastro_Email_Rodar.this, "Este E-Mail Já Existe", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {                                                         //Erros Diversos
                        Toast.makeText(c_Cadastro_Email_Rodar.this, "Erro ao Cadastrar Usuário", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

}


//Tela Layout Xml;
    //Constrain + Relative Para Dimensionar Melhor (arrastar pelo mouse);
    //Botão de comando com OnClick = Método Próprio;

//Classe Campos Java;
    //Classe com Implements Serializable (para passar os dados para outra tela);
    //Campo ID (referencia do firebase);
    //GetSet e Construtor;

//Classe Rodar Java;
    //Declarar Objetos Desta Tela;
    //Declarar Classe Campos Desta Tela;
    //Declarar Firebase Autenticação;
    //Declarar Firebase Banco de dados;
    //Identificar Objetos da Tela;
    //Método Para Cadastrar Usuário - Validação;
        //Pegar Valores Digitados;
        //Colocar na Classe Campos (Com Construtor);
        //Tratamento de Campos em Branco;
        //Tratamento de Campos Com 6 ou > Caracteres (Menos o Firebase Não Aceita);
        //Tratamento de Senha (se digitou a senha correta);
    //Método Para Cadastrar Usuário - Inserção no Firebase
        //Pegar o ID Deste Usuário Criado Pelo Firebase (Quando o Usuário é Autenticado, o Firebase Gera um ID Deste Cliente);
        //Colocar Este ID na Classe Campos;
        //Criar No Banco De Dados Uma Tabela(Nó) Usuários Com Os Dados Deste Usuário;
        //Levar os Dados Deste usuário para a Próxima Tela;
        //Tratamento de Email Já Cadastrado;
        //Tratamento de Erros Diversos (não autenticou e nem cadastrou no Firebase);

//Observações
    // O fireAuth.createUserWithEmailAndPassword Já Cadastra o Usuário e Gera um ID para Este No Firebase (Módulo Autenticação);
    //Falta Validação Se E-Mail Existe de Verdade
