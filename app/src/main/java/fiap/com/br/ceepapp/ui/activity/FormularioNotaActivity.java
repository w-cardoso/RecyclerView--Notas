package fiap.com.br.ceepapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import fiap.com.br.ceepapp.R;
import fiap.com.br.ceepapp.model.Nota;

import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.INVALITE_POSITION;
import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.KEY_NOTE;
import static fiap.com.br.ceepapp.ui.activity.NotaActivityConstantes.POSITION_KEY;


public class FormularioNotaActivity extends AppCompatActivity {


    private EditText title;
    private EditText description;
    private int receivedPosition = INVALITE_POSITION;
    public static final String APP_BAR_TITLE_UPDATE = "Altera Nota";
    public static final String APP_BAR_TITLE_INSERT = "Insere Nota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(APP_BAR_TITLE_INSERT);

        initializeComponents();
        Intent receivedData = getIntent();
        if (receivedData.hasExtra(KEY_NOTE)) {
            setTitle(APP_BAR_TITLE_UPDATE);
            Nota receivedNote = (Nota) receivedData.getSerializableExtra(KEY_NOTE);
            receivedPosition = receivedData.getIntExtra(POSITION_KEY, INVALITE_POSITION);
            fieldsFill(receivedNote);
        }

    }

    private void fieldsFill(Nota receivedNote) {
        title.setText(receivedNote.getTitle());
        description.setText(receivedNote.getDescription());
    }

    private void initializeComponents() {
        title = findViewById(R.id.formulario_nota_titulo);
        description = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_formulario_nota) {
            Nota noteCreate = createNote();
            returnNote(noteCreate);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnNote(Nota note) {
        Intent resultInsertion = new Intent();
        resultInsertion.putExtra(KEY_NOTE, note);
        resultInsertion.putExtra(POSITION_KEY, receivedPosition);
        setResult(RESULT_OK, resultInsertion);
    }

    @NonNull
    private Nota createNote() {
        title = findViewById(R.id.formulario_nota_titulo);
        description = findViewById(R.id.formulario_nota_descricao);
        return new Nota(title.getText().toString(),
                description.getText().toString());
    }
}
