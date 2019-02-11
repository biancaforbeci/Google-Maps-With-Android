package com.example.bianca.googlemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bianca.googlemaps.Database.MarkerDAO;
import com.example.bianca.googlemaps.Database.MarkerMaps;

import java.util.ArrayList;

public class MarkersActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private LineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);

        mRecyclerView = findViewById(R.id.recycler_view_layour_recycler);
        setupRecycler();
    }

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(new MarkerDAO().list().isEmpty()){
            mAdapter = new LineAdapter(new ArrayList<>(0));
            Toast.makeText(this,"Nenhum marcador cadastrado",Toast.LENGTH_LONG).show();
        }else{
            mAdapter = new LineAdapter(new MarkerDAO().list());
        }

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
