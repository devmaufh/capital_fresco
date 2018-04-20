package com.example.lsaal.cf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lsaal.cf.Adapters.MyAdapterHectarea;
import com.example.lsaal.cf.Clases_Datos.hectareas;

import java.util.ArrayList;
import java.util.List;

public class Agricultor extends AppCompatActivity {

    private List<hectareas> hectareas;

    private RecyclerView recycler_agricultor;
    private RecyclerView.Adapter agriAdapter;
    private RecyclerView.LayoutManager agriManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agricultor);

        hectareas = getHectareas();

        recycler_agricultor = findViewById(R.id.recycler_agricultor);
        agriManager = new LinearLayoutManager(this);

        agriAdapter = new MyAdapterHectarea(hectareas, R.drawable.card_agricultor, new MyAdapterHectarea.OnItemClickListener() {
            @Override
            public void onItemClick(hectareas hect, int position) {

            }
        });
        recycler_agricultor.setHasFixedSize(true);
        recycler_agricultor.setItemAnimator(new DefaultItemAnimator());
        recycler_agricultor.setAdapter(agriAdapter);
        recycler_agricultor.setLayoutManager(agriManager);


    }

    private List<hectareas> getHectareas(){
        return new ArrayList<hectareas>(){{
            add(new hectareas());
        }};
    }

}
