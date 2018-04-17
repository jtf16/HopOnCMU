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
    CardView userLayout;
    private TextView ranking;
    private TextView username;
    private TextView score;
    private TextView time;
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
        time = itemView.findViewById(R.id.user_time);
    }

    public void setUser(Context context, User user, int position) {
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
                ranking.setBackgroundResource(android.R.color.transparent);
                break;
        }
        switch (position % 2) {
            case 0:
                userLayout.setBackgroundResource(R.drawable.degrade_ranked_even);
                break;
            default:
                userLayout.setBackgroundResource(R.drawable.degrade_ranked_odd);
                break;
        }
        ranking.setText(user.getRanking() + ".");
        username.setText(user.getUsername());
        score.setText(Integer.toString(user.getScore()));
        time.setText(String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(user.getTime()),
                TimeUnit.MILLISECONDS.toMinutes(user.getTime()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(user.getTime())),
                TimeUnit.MILLISECONDS.toSeconds(user.getTime()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(user.getTime()))));
    }
}
