package com.benjamin.swipelayoutrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.swipelayoutrecyclerview.R;
import com.benjamin.swipelayoutrecyclerview.model.Player;

import java.util.List;

public class PlayersDataAdapter extends RecyclerView.Adapter<PlayersDataAdapter.PlayerViewHolder> {
    private List<Player> players;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name, nationality, club, rating, age;

        public PlayerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            nationality = (TextView) view.findViewById(R.id.nationality);
            club = (TextView) view.findViewById(R.id.club);
            rating = (TextView) view.findViewById(R.id.rating);
            age = (TextView) view.findViewById(R.id.age);
        }
    }

    public PlayersDataAdapter(List<Player> players) {
        this.players = players;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.name.setText(player.getName());
        holder.nationality.setText(player.getNationality());
        holder.club.setText(player.getClub());
        holder.rating.setText(player.getRating().toString());
        holder.age.setText(player.getAge().toString());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }
}