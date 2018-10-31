package com.example.tela_cadastro02;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class d_Cadastro_End_Rodar extends AppCompatActivity {
    private EditText                    cep, rua, numero, comple, bairro, cidade, estado;           //Objetos - Declarar;
    private c_Cadastro_Email_Campos     CadastroEmailCampos;                                        //Classe de Campos da Tela Anterior;
    private d_Cadastro_End_Campos       CadastroEndCampos;                                          //Classe de Campos Desta Tela;
    private static FirebaseDatabase     fireDatabase = FirebaseDatabase.getInstance();              //Firebase - Banco Dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_cadastro_end);

        cep     = (EditText)    findViewById(R.id.c_Cadastro_End_Cep_Xml);                          //Objetos - Identificar;
        rua     = (EditText)    findViewById(R.id.c_Cadastro_End_Rua_Xml);
        numero  = (EditText)    findViewById(R.id.c_Cadastro_End_Numero_Xml);
        comple  = (EditText)    findViewById(R.id.c_Cadastro_End_Complemento_Xml);
        bairro  = (EditText)    findViewById(R.id.c_Cadastro_End_Bairro_Xml);
        cidade  = (EditText)    findViewById(R.id.c_Cadastro_End_Cidade_Xml);
        estado  = (EditText)    findViewById(R.id.c_Cadastro_End_Estado_Xml);

        //Definir o Valor do Estado Na Caixa de Texto como São Paulo
        estado.setText("SP");

        //Receber Dados do Usuário (da tela anterior), E Colocar Na Classe EMail Campos (Que é da Tela Anterior)
        Intent intent = getIntent();
        CadastroEmailCampos = (c_Cadastro_Email_Campos) intent.getSerializableExtra("DadosUsuárioEmail");

        //Passar Propriedades Desta Classe, Como o Comando Toast, para a Classe Abaixo
        d_Cadastro_End_BuscarCep buscarCep;
        buscarCep = new d_Cadastro_End_BuscarCep(this);
    }

    public void c_Cadastro_End_Cadastro_Xml (View view) {
        String cepX     =   cep.getText().toString();                                               //Pegar Valores Digitados;
        String ruaX     =   rua.getText().toString();
        String numeroX  =   numero.getText().toString();
        String compleX  =   comple.getText().toString();
        String bairroX  =   bairro.getText().toString();
        String cidadeX  =   cidade.getText().toString();
        String estadoX  =   estado.getText().toString();

        CadastroEndCampos = new d_Cadastro_End_Campos(ruaX, numeroX);                               //Campos Obrigatórios Passados (Set)
        CadastroEndCampos.setCep(cepX);                                                             //Passar Outros Campos (Set)
        CadastroEndCampos.setComple(compleX);
        CadastroEndCampos.setBairro(bairroX);
        CadastroEndCampos.setCidade(cidadeX);
        CadastroEndCampos.setEstado(estadoX);

        if(CadastroEndCampos.getRua().equals("")) {                                                 //Tratamento de Campos em Branco (Get)
            Toast.makeText(this, "Digite sua Rua", Toast.LENGTH_LONG).show();
        } else if (CadastroEndCampos.getNumero().equals("")) {
            Toast.makeText(this, "Digite o Número", Toast.LENGTH_LONG).show();
        } else {
            CadastrarClienteEndereco();                                                             //Cadastrar Cliente;
        }
    }

    public void c_Cadastro_End_BuscarCep_XML (View view){                                           //Buscar Endereço Pelo CEP

            //Tratamento Para o Teclado Só Aceitar Números = No XML, Comando Input Type = Number;

            //Tratamento Para Só Aceitar O Campo CEP com 8 Caracteres
        if(cep.getText().length() != 8) {
            Toast.makeText(this, "O CEP São 8 Caracteres", Toast.LENGTH_LONG).show();
        } else {

            //Pegar CEP da Caixa de Entrada
            String cepx = cep.getText().toString();
            //Montar URL do Browser
            String link = "https://viacep.com.br/ws/" + cepx +"/json/";
            //Passar Campos Usados
            d_Cadastro_End_BuscarCep thread = new d_Cadastro_End_BuscarCep(rua, comple, bairro, cidade);
            //Passar URL do Browser
            thread.execute(link);
        }
    }


    public void CadastrarClienteEndereco () {
        try {
            //Ir no Nó do Usuário
            DatabaseReference usuario = fireDatabase.getReference("Clientes").child(CadastroEmailCampos.getId());   //O Nó do Clíente é o ID do Usuário do Firebase

            //Incluir o Resto do Cadastro do Usuário Em Seu Respectivo Nó
            usuario.child("CEP").setValue(CadastroEndCampos.getCep());
            usuario.child("Rua").setValue(CadastroEndCampos.getRua());
            usuario.child("Numero").setValue(CadastroEndCampos.getNumero());
            usuario.child("Complemento").setValue(CadastroEndCampos.getComple());
            usuario.child("Bairro").setValue(CadastroEndCampos.getBairro());
            usuario.child("Cidade").setValue(CadastroEndCampos.getCidade());
            usuario.child("Estado").setValue(CadastroEndCampos.getEstado());

            //Informar o Usuário que o cadastro foi com sucesso
            Toast.makeText(d_Cadastro_End_Rodar.this, "Usuário Cadastrado", Toast.LENGTH_LONG).show();

            //Ir Para a Tela do Sistema ...
            Intent intentX = new Intent(d_Cadastro_End_Rodar.this, f_TelaDoSistema.class);    //Ir para a Tela .....
            startActivity(intentX);
            finish();


        } catch (Exception e) {                                                                     //Erros Diversos
            Toast.makeText(d_Cadastro_End_Rodar.this, "Erro ao Cadastrar Usuário", Toast.LENGTH_LONG).show();
        }
    }
}

