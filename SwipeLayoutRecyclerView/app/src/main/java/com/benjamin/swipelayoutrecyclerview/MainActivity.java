package com.benjamin.swipelayoutrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;

import com.benjamin.swipelayoutrecyclerview.adapter.PlayersDataAdapter;
import com.benjamin.swipelayoutrecyclerview.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlayersDataAdapter adapter;
    private SwipeController swipeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPlayersDataAdapter();
        setupRecyclerView();

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                adapter.getPlayers().remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void setPlayersDataAdapter() {
        List<Player> players = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("players.csv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            while ((line = reader.readLine()) != null) {
                st = line.split(",");
                Player player = new Player();
                player.setName(st[0]);
                player.setNationality(st[1]);
                player.setClub(st[4]);
                player.setRating(Integer.parseInt(st[9]));
                player.setAge(Integer.parseInt(st[14]));
                players.add(player);
            }
        } catch (IOException e) {

        }

        adapter = new PlayersDataAdapter(players);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
