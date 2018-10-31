package com.example.tela_cadastro02;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import static com.facebook.FacebookSdk.getApplicationContext;

public class d_Cadastro_End_BuscarCep extends AsyncTask<String,Void,String> {

    private EditText end, comp, bai, cid;

    //3Campos Retornados (Cep não Precisa)
    public d_Cadastro_End_BuscarCep(EditText end, EditText comp, EditText bai, EditText cid) {      //Constutor Destes Campos
        this.end = end;
        this.comp = comp;
        this.bai = bai;
        this.cid = cid;
    }

    //Receber Comando Toast da Classe d_Cadastro_End_Rodar
    private Context context;
    public d_Cadastro_End_BuscarCep (Context context) {
        this.context = context;
        Toast.makeText(context, "Mensagem de Teste", Toast.LENGTH_LONG).show();
    }

    @Override                                                                                       //Criar Override Mehtods DoInBack ..
    protected String doInBackground(String... strings) {
        String texto = null;

        try {
            String link = strings[0];                         //Conectar Internet
            URL url = new URL(link);
            HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();

            //Pegar Dados da Web em Bytes;
            InputStream input = new BufferedInputStream(conexao.getInputStream());

            //Converter Dados Pegos Para String;
            BufferedReader textoSite = new BufferedReader(new InputStreamReader(input));

            StringBuilder sb = new StringBuilder();           //Lista de Strings
            String linha = textoSite.readLine();              //Primeira Linha
            while (linha != null) {                           //Enquanto Linha Não Eh Vazia
                sb.append(linha + "\n");                      //Adiciona na Lista
                linha = textoSite.readLine();                 //Le nova linha
            }
            texto = sb.toString();                            //Converter Lista para String

        } catch (Exception e) {
            Toast.makeText(context, "Erro ao Buscar CEP", Toast.LENGTH_LONG).show();           //???????????Nâo Esta Funcionando
        }
        return texto;
    }

    @Override                                                                                       //Criar Override Mehtods onPostExecute
    protected void onPostExecute(String texto) {
        super.onPostExecute(texto);
        try {
            JSONObject json = new JSONObject(texto);                   //Instanciar Classe Interna
            String endPego = json.getString("logradouro");  //Buscar Valor Json
            String compPego = json.getString("complemento");
            String baiPego = json.getString("bairro");
            String cidPego = json.getString("localidade");

            end.setText(endPego);                                 //Colocar Valor na Cx Entrada
            comp.setText(compPego);
            bai.setText(baiPego);
            cid.setText(cidPego);
        } catch (Exception e) { }

    }

}



//Classe Thread    (Herança AsyncTask)  - Exige Métodos "Executar-DoInBack" e "Pós Executar-OnPost"
//Métodos Override (DoInBack e OnPost)  - Exige Try Catch;
//Declarar Objetos (Campos Retornados)  - EditText, TextView, Etc;
//Construtor       (Campos Retornados)  - Se Retornar Só Endereço no Json, Então Só Endereço Aqui;
//DoInBackGround   (Programar)          - ...
//OnPostExecute    (Programar)          - Instanciar Classe Interna Json; Buscar Json; Setar Caixas;
//Permissão Net    (Arquivo Manifest)   - User Permission.Internet;