package br.unigran.bancodados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Currency;
import java.util.List;

import br.unigran.listatelefonica.Contato;

public class ContatoDB {
    private DBHelper db;
    private SQLiteDatabase conexao;
    public ContatoDB(DBHelper db){this.db=db;}

    public void inserir(Contato contato){
        conexao = db.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome",contato.getNome());
        valores.put("telefone",contato.getTelefone());
        valores.put("dataNasc",contato.getDataNasc());
        if(contato.getId()>0)
            conexao.update("ListaTelefonica",valores,"id=?",
                    new String[]{contato.getId().toString()});
        else
            conexao.insertOrThrow("ListaTelefonica",null,valores);
        conexao.close();
    }

    public void atualizar(){}

    public void remover(int id){
        conexao = db.getWritableDatabase();
        conexao.delete("Lista","id=?",new String[]{id+""});
    }

    public void lista(List dados){
        dados.clear();
        conexao=db.getReadableDatabase();
        String names[]={"id","nome","telefone","dataNasc"};
        Cursor query = conexao.query("ListaTelefonica",names,
                null,null,null,
                null,"nome");
        while (query.moveToNext()){
            Contato contato = new Contato();
            contato.setId(Integer.parseInt(query.getString(0)));
            contato.setNome(query.getString(1));
            contato.setTelefone(query.getString(2));
            contato.setDataNasc(query.getString(3));
            dados.add(contato);
        }
        conexao.close();
    }

}
