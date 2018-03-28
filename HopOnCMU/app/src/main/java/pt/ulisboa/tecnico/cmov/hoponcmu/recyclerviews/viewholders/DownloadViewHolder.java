package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.QuizActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.Quiz;

public class DownloadViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private Quiz quiz;

    public DownloadViewHolder(final Context context, View itemView) {
        super(itemView);
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DownloadViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("quiz", quiz);
                context.startActivity(intent);
            }
        });
        name = itemView.findViewById(R.id.download_name);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        name.setText(quiz.getName());
    }
}
