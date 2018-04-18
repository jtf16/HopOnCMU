package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class QuizViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private TextView name;
    private Quiz quiz;

    public QuizViewHolder(Context context, View itemView) {
        super(itemView);

        mContext = context;

        name = itemView.findViewById(R.id.monument_name);
        itemView.findViewById(R.id.download_name).setVisibility(View.GONE);
    }

    public void setQuiz(Quiz quiz) {

        this.quiz = quiz;
        name.setText(quiz.getName());
    }
}
