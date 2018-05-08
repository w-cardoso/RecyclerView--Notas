package fiap.com.br.ceepapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fiap.com.br.ceepapp.R;
import fiap.com.br.ceepapp.dao.NotaDAO;
import fiap.com.br.ceepapp.model.Nota;
import fiap.com.br.ceepapp.ui.recyclerview.adapter.ListaNotasAdapter;
import fiap.com.br.ceepapp.ui.recyclerview.adapter.listener.OnItemClickListener;
import fiap.com.br.ceepapp.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.INVALITE_POSITION;
import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.KEY_NOTE;
import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.POSITION_KEY;
import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.REQUEST_CODE_INSERT_NOTE;
import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.REQUEST_CODE_UPDATE_NOTE;


public class ListaNotasActivity extends AppCompatActivity {


    public static final String APP_BAR_TITLE = "Notas";
    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(APP_BAR_TITLE);

        List<Nota> todasNotas = getAllNotes();
        configuraRecyclerView(todasNotas);
        buttonInsertNote();
    }

    private void buttonInsertNote() {
        TextView btnInsertNote = findViewById(R.id.lista_notas_insere_nota);
        btnInsertNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goesToFormInsert();
            }
        });
    }

    private void goesToFormInsert() {
        Intent openForm = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(openForm, REQUEST_CODE_INSERT_NOTE);
    }

    private List<Nota> getAllNotes() {
        NotaDAO dao = new NotaDAO();
        for (int i = 1; i <= 20; i++) {
            dao.insere(new Nota("Titulo" + i, "Descrição" + i));
        }
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (isResultInsertNote(requestCode, data)) {
            if (isResultOk(resultCode)) {
                Nota noteReceived = (Nota) data.getSerializableExtra(KEY_NOTE);
                addNote(noteReceived);
            }

        }
        if (isResultUpdateNote(requestCode, data)) {
            if (isResultOk(resultCode)) {
                Nota receivedNote = (Nota) data.getSerializableExtra(KEY_NOTE);
                int receivedPosition = data.getIntExtra(POSITION_KEY, INVALITE_POSITION);
                if (isValidPosition(receivedPosition)) {
                    updateNote(receivedNote, receivedPosition);
                } else {
                    Toast.makeText(this, R.string.text_failure, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void updateNote(Nota receivedNote, int receivedPosition) {
        new NotaDAO().altera(receivedPosition, receivedNote);
        adapter.update(receivedPosition, receivedNote);
    }

    private boolean isValidPosition(int receivedPosition) {
        return receivedPosition > INVALITE_POSITION;
    }

    private boolean isResultUpdateNote(int requestCode, Intent data) {
        return isRequestCodeUpdate(requestCode)
                && hasNote(data);
    }

    private boolean isRequestCodeUpdate(int requestCode) {
        return requestCode == REQUEST_CODE_UPDATE_NOTE;
    }


    private void addNote(Nota noteReceived) {
        new NotaDAO().insere(noteReceived);
        adapter.addNotes(noteReceived);
    }

    private void configuraRecyclerView(List<Nota> allNotes) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerView);
        configuraAdapter(allNotes, listaNotas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> allNotes, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, allNotes);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                goesForFormNoteUpdate(nota, posicao);
            }
        });
    }

    private void goesForFormNoteUpdate(Nota nota, int posicao) {
        Intent formOpenWithNote = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        formOpenWithNote.putExtra(KEY_NOTE, nota);
        formOpenWithNote.putExtra(POSITION_KEY, posicao);
        startActivityForResult(formOpenWithNote, REQUEST_CODE_UPDATE_NOTE);
    }

    private boolean isResultInsertNote(int requestCode, Intent data) {
        return isCodeRequestInsertNote(requestCode) &&
                hasNote(data);
    }

    private boolean isCodeRequestInsertNote(int requestCode) {
        return requestCode == REQUEST_CODE_INSERT_NOTE;
    }

    private boolean isResultOk(int resultCode) {
        return resultCode == RESULT_OK;
    }

    private boolean hasNote(Intent data) {
        return data != null && data.hasExtra(KEY_NOTE);
    }

}
