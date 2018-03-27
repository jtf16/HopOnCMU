package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.User;

public class UserViewHolder extends RecyclerView.ViewHolder {
    CardView userLayout;
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
                ranking.setBackgroundResource(R.drawable.degrade_gold);
                break;
            case 2:
                ranking.setBackgroundResource(R.drawable.degrade_silver);
                break;
            case 3:
                ranking.setBackgroundResource(R.drawable.degrade_bronze);
                break;
            default:
                break;
        }
        switch (user.getRanking() % 2) {
            case 0:
                userLayout.setBackgroundResource(R.drawable.degrade_ranked_even);
                break;
            default:
                userLayout.setBackgroundResource(R.drawable.degrade_ranked_odd);
                break;
        }
        ranking.setText(user.getRanking() + ".");
        username.setText(user.getUserName());
        score.setText(Integer.toString(user.getScore()));
    }
}
