package com.example.nicolasf.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> names;
    private int layout;
    private OnitemClickListener itemClickListener;

    public MyAdapter(List<String> names, int layout, OnitemClickListener listener) {
        this.names = names;
        this.layout = layout;
        this.itemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v); // se infla la vista y se pasa como constructor del view holder
        return vh;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) { // si se actualiza la lista se agrega el nuevo elemento. Se usa cuando se crea la recyvler. Luego no se vuelve a renderizar por cada elemento.
        holder.bind(names.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;

        public ViewHolder(View itemView) { // guardamos la vista en nuestro atributo
            super(itemView); // le pasamos la vista al padre (onCreate) y la extraemos la refencia
            this.textViewName= (TextView) itemView.findViewById(R.id.textView);

        }

        public void bind(final String name, final OnitemClickListener listener) {
            this.textViewName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() { // este se renderiza cada vez que hagamos un click en un elemento
                @Override
                public void onClick(View view) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    public interface OnitemClickListener {
        void onItemClick(String name, int position);
    }
}
