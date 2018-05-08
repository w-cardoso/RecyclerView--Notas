package fiap.com.br.ceepapp.ui.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


import fiap.com.br.ceepapp.R;
import fiap.com.br.ceepapp.model.Nota;
import fiap.com.br.ceepapp.ui.recyclerview.adapter.listener.OnItemClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final List<Nota> notes;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context, List<Nota> notes) {
        this.context = context;
        this.notes = notes;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View createView = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(createView);
    }

    @Override
    public void onBindViewHolder(ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota note = notes.get(position);
        holder.setBinds(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void update(int receivedPosition, Nota receivedNote) {
        notes.set(receivedPosition, receivedNote);
        notifyDataSetChanged();
    }

    public void remove(int adapterPosition) {
        notes.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notes, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private Nota nota;

        public NotaViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_nota_titulo);
            description = itemView.findViewById(R.id.item_nota_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });

        }


        public void setBinds(Nota nota) {
            this.nota = nota;
            fieldFill(nota);
        }

        private void fieldFill(Nota note) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
    }

    public void addNotes(Nota note) {
        notes.add(note);
        notifyDataSetChanged();
    }
}
