package fiap.com.br.ceepapp.ui.recyclerview.helper.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import fiap.com.br.ceepapp.dao.NotaDAO;
import fiap.com.br.ceepapp.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapter adapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesArrastar, marcacoesDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        new NotaDAO().troca(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        adapter.troca(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        new NotaDAO().remove(viewHolder.getAdapterPosition());
        adapter.remove(viewHolder.getAdapterPosition());
    }
}
