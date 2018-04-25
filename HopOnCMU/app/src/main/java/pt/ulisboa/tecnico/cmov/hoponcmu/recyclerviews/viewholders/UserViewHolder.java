package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private User user;

    private CardView cardView;
    private TextView rankingView, usernameView, scoreView, timeView;

    public UserViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.user);
        rankingView = itemView.findViewById(R.id.user_ranking);
        usernameView = itemView.findViewById(R.id.username);
        scoreView = itemView.findViewById(R.id.user_score);
        timeView = itemView.findViewById(R.id.user_time);
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(UserViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");
            }
        });
    }

    public void setUser(Context context, User user, int position) {
        this.user = user;
        long hours = TimeUnit.MILLISECONDS.toHours(user.getTime()),
                minutes = TimeUnit.MILLISECONDS.toMinutes(user.getTime()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(user.getTime())),
                seconds = TimeUnit.MILLISECONDS.toSeconds(user.getTime()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(user.getTime()));
        switch (user.getRanking()) {
            case 1:
                rankingView.setBackgroundResource(R.drawable.degrade_gold);
                break;
            case 2:
                rankingView.setBackgroundResource(R.drawable.degrade_silver);
                break;
            case 3:
                rankingView.setBackgroundResource(R.drawable.degrade_bronze);
                break;
            default:
                rankingView.setBackgroundResource(android.R.color.transparent);
                break;
        }
        switch (position % 2) {
            case 0:
                cardView.setBackgroundResource(R.drawable.degrade_ranked_even);
                break;
            default:
                cardView.setBackgroundResource(R.drawable.degrade_ranked_odd);
                break;
        }
        rankingView.setText(context.getString(R.string.user_ranking, user.getRanking()));
        usernameView.setText(context.getString(R.string.user_username, user.getUsername()));
        scoreView.setText(context.getString(R.string.user_score, user.getScore()));
        timeView.setText(context.getString(R.string.user_time, hours, minutes, seconds));
    }
}
