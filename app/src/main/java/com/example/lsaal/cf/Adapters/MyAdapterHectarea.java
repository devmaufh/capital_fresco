package com.example.lsaal.cf.Adapters;

import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lsaal.cf.Clases_Datos.hectareas;
import com.example.lsaal.cf.R;

import java.util.List;

/**
 * Created by mauri on 12/04/2018.
 */

public class MyAdapterHectarea extends  RecyclerView.Adapter<MyAdapterHectarea.ViewHolder>{
    private List<hectareas> hectareasList;
    private int layout;
    private OnItemClickListener ItemClickListener;

    public MyAdapterHectarea(List<hectareas> hectareasList, int layout, OnItemClickListener listener){
        this.hectareasList=hectareasList;
        this.layout=layout;
        this.ItemClickListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh= new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Volca datos en los elementos gráficos
        holder.bind(hectareasList.get(position),ItemClickListener);

    }

    @Override
    public int getItemCount() {
        return hectareasList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //Elementos del CardView
        TextView txtName;// ejemplo, hacer esto con todos los elementos de los cardView

        public ViewHolder(View itemView) {
            super(itemView);
            //this.txtName=(TextView) itemView.findViewById(R.id.text0o) //Extraer referencias

        }
        public void bind(final hectareas hectarea, final OnItemClickListener listener){
            //this.txtName.setText(hectarea.getId_hectarea());//ejemplo
            //Se traen los datos de la clase para mostrarlos en los elementos gráficos

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(hectarea,getAdapterPosition()); //Accion en el click del elemento
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(hectareas hect, int position);
    }
}
