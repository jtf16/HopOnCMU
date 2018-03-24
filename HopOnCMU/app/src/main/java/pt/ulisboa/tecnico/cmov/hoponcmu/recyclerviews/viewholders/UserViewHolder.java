package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.User;

public class UserViewHolder extends RecyclerView.ViewHolder {
    LinearLayout userLayout;
    private TextView ranking;
    private TextView username;
    private TextView score;
    private User user;

    public UserViewHolder(View itemView) {
        super(itemView);
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(UserViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");
            }
        });
        userLayout = itemView.findViewById(R.id.user);
        ranking = itemView.findViewById(R.id.user_ranking);
        username = itemView.findViewById(R.id.username);
        score = itemView.findViewById(R.id.user_score);
    }

    public void setUser(Context context, User user) {
        this.user = user;
        switch (user.getRanking()) {
            case 1:
                userLayout.setBackgroundColor(
                        context.getResources().getColor(R.color.colorGold));
                break;
            case 2:
                userLayout.setBackgroundColor(
                        context.getResources().getColor(R.color.colorSilver));
                break;
            case 3:
                userLayout.setBackgroundColor(
                        context.getResources().getColor(R.color.colorBronze));
                break;
        }
        ranking.setText(Integer.toString(user.getRanking()));
        username.setText(user.getUserName());
        score.setText(Integer.toString(user.getScore()));
    }
}
