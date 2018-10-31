package com.example.tela_cadastro02;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class b_Login_Email_Rodar extends AppCompatActivity {                                        //Classe de Tela
    private EditText                email,senha;                                                    //Objetos Tela - Declarar
    private TextView                cadastrar;
    private b_Login_Email_Campos    LoginCampos;                                                    //Classe Campos
    private static FirebaseAuth     fireAuth = FirebaseAuth.getInstance();                          //Firebase - Autenticação
    CallbackManager                 callbackManager;                                                //Facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                            //Método Inicializador da Classe
        super.onCreate(savedInstanceState);                                                         //Tela Xml Carregada
        setContentView(R.layout.b_login);

        email       = (EditText) findViewById(R.id.a_login_Email_Xml);                              //Objetos Tela - Identificar
        senha       = (EditText) findViewById(R.id.a_login_Senha_Xml);
        cadastrar   = (TextView) findViewById(R.id.cadastroCliqueAquiXml);

        TextView cliqueAqui = (TextView) findViewById(R.id.cadastroCliqueAquiXml);                  //Texto Sublinhado
        cliqueAqui.setPaintFlags(cliqueAqui.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setTitle("Tela de Login");                                                                  //Título da Tela


        //Facebook
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile, email, user_friends,user_birthday");        // Ver no face para pegar mais dados;
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());                              //chama a função abaixo ...

                Intent intentX = new Intent(b_Login_Email_Rodar.this, f_TelaDoSistema.class);    //Ir para a Tela .....
                startActivity(intentX);
                finish();

            }

            @Override
            public void onCancel() {
                Toast.makeText(b_Login_Email_Rodar.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(b_Login_Email_Rodar.this, "Erro ao Logar", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Facebook
    @Override   //Copiar do Face
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken (AccessToken token) {

    }



    //Ir para a Tela de Cadastro (Se não tiver cadastro)
    public void a_login_Cadastrar_Xml (View view) {

        //Falta Formatar Este Testo (Colocar como Link) ????????????????????????????????????????????????????????????????????????

        Intent intent = new Intent(b_Login_Email_Rodar.this, c_Cadastro_Email_Rodar.class);
        startActivity(intent);
        //finish();                                                                                 //Fechar a Tela Atual
    }


    public void a_login_Entrar_Xml (View view) {                                                    //Botão de Comando para Logar
        //Com a Classe Campos
        String emailx = email.getText().toString();
        String senhax = senha.getText().toString();
        LoginCampos = new b_Login_Email_Campos(emailx,senhax);

        if(emailx.equals("")) {                                                                     //Tratamento de Campo em Branco
            Toast.makeText(b_Login_Email_Rodar.this, "Digite seu e-mail", Toast.LENGTH_LONG).show();
        } else if (senhax.equals("")) {
            Toast.makeText(b_Login_Email_Rodar.this, "Digite sua senha", Toast.LENGTH_LONG).show();
        } else {
            LogarNoSistema();                                                                       //Método para Logar No Sistema
        }

        //Sem a Classe Campos
//        if(email.getText().toString().equals("")) {                                               //Tratamento de Campo em Branco
//            Toast.makeText(b_Login_Email_Rodar.this, "Digite seu e-mail", Toast.LENGTH_LONG).show();
//        } else if (senha.getText().toString().equals("")){
//            Toast.makeText(b_Login_Email_Rodar.this, "Digite sua senha", Toast.LENGTH_LONG).show();
//        } else {
//            LogarNoSistema();
//        }
    }


    public void senhaCliqueAquiXml (View view) {
        Intent intent = new Intent (b_Login_Email_Rodar.this, e_Esqueci_Senha.class);
        startActivity(intent);
    }





    public void LogarNoSistema(){
        //Sem a Classe Campos
        //fireAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())

        //Com a Classe Campos
        fireAuth.signInWithEmailAndPassword(LoginCampos.getEmail(), LoginCampos.getSenha())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()) {
                    Toast.makeText(b_Login_Email_Rodar.this, "Login Ok!", Toast.LENGTH_LONG).show();
                    Intent intentX = new Intent(b_Login_Email_Rodar.this, f_TelaDoSistema.class);    //Ir para a Tela Main
                    startActivity(intentX);
                    finish();                                                                       //Fechar Tela Atual
                 } else {
                    Toast.makeText(b_Login_Email_Rodar.this, "Dados Inválidos", Toast.LENGTH_LONG).show();
                 }
              }
        });
    }

}

//Rodar No Celular
    //No Celular Ir em Configurações / Programador e Ativar. Ire em Depuração USB e Ativar também;
    //Se a Versão do Android do Celular for Diferente do Aplicativo(android), Pedirá para Baixar a Versão do Celular;

//Identificação do Aplicativo
    //Ícone:            Copiar Imagem para o RES/Mipmap, e Chamar Este em Manifest/Icon;
    //Nome:             Manifest Label ou RES/Values/Strings/App Name
    //Título Padrão:    Ao Alterar o Nome (acima), todos os Títulos da Toolbar Serão Iguais a este Nome;
    //Título Individ:   Tentar o SetTitle no Xml ... ????????????????????????????????????????????????????????????????????????????????????????
    //Título Imagem:    Como Colocar Imagem Com o Título na Faixa de Menu ???????????????????????????????????????????????????????????????????

//Criar a Tela de Inicialização
    // copiar a imagem que será inicializada na pasta drawable;
    // criar tela Xml como FrameLayout (toda pagina);
    // Criar Tela Java (handler)

//Criar a Tela de Login /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Conectar o Projeto Android ao Firebase
    //Tentar Pelo Assistente do Android, Senão Fazer Manualmente;
    //Verificar Se o Nome do Projeto Já Existe no Firebase (senão dará erro);

//Adicionar o Banco de Dados do Firebase(RealTime Database) ao Projeto Android;
    //Tentar Pelo Assistente do Android, Senão Fazer Manualmente;

//Adicionar a Autenticação de Login do Firebase (Authentication) ao Projeto Android;
    //Tentar Pelo Assistente do Android, Senão Fazer Manualmente;

//Definir a Autenticação de Login do Firebase Por E-mail e Senha;
    //Pelo Firebase / Console / Projeto / Authentication / E-Mail e Senha;

//Rodar o Projeto no Android
    //Se Der Erro, Trocar as Dependencias do Gradle Project e do Gradle Module (Por Este, Que Foi copiado do Firelogin0);

//Criar Tela de Login Xml (Campos Para Entrada de Dados)
    //Fazer Login Pelo E-Mail e Senha (Campo Email, Campo Senha, Botão Login, Texto Não Tem Conta - Cadastrar);

//Criar Classe Java dos Campos da Tela de Login
    //Variáveis Private, Getter e Setter, Construtor;

//Criar Classe Java Executadora dos Campos da Tela de Login;
    //Declarar Variáveis (Objetos da Tela, Firebase, Etc) Na Classe e Identificá-los no OnCreate;
    //Declarar Variáveis da Classe Campos (para qualquer método da classe enxergar o valor setado);
    //Botão de Comando Para Logar no Sistema (Com Tratamento de Campos em Branco);
    //Método de Logar no Sistema (adicionar manualmente um usuário teste no firebase para testar);


//Atenção
    //Equals é diferente de == " "
    //Adicionar Usuário no Próprio Firebase, Para Este Módulo Funcionar;
    //Declarar a Classe Campos no Início, para o Valor Setado (manualmente ou pelo construtor), ser enxergue (Get) em Qualquer Módulo;


//Criar a Tela de Cadastro///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Criar Tela de Cadastro Xml (Campos Para Entrada de Dados)


//Criar Classe Java dos Campos da Tela de Login
//Variáveis Private (Campos Usados + Campo ID), Getter e Setter, Construtor (Nome, Email, Senha, Rua, Numero);








//--------------------------------------------------------------------
//trat. campos em branco                                  login
//trat senha menor que 6 dígitos                          login e cadastro
//Palavra/Texto Estilo Link (azul e sublinhado)           cadastro
