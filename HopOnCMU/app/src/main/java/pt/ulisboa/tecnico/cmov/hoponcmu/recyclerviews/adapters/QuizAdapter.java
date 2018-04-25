package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders.QuizViewHolder;

public class QuizAdapter extends RecyclerView.Adapter<QuizViewHolder> {

    private List<Quiz> quizList;
    private List<Long> listIDs;
    private LinearLayoutManager layoutManager;
    private Context context;

    public QuizAdapter(Context context, LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.quizList = new ArrayList<>();
        this.context = context;
    }

    public void setQuizzes(List<Quiz> newQuizList, List<Long> newIDs) {
        QuizAdapter.QuizzesDiffUtil monumentsDiffUtil =
                new QuizAdapter.QuizzesDiffUtil(quizList, newQuizList);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(monumentsDiffUtil);
        quizList.clear();
        quizList.addAll(newQuizList);
        diffResult.dispatchUpdatesTo(this);
        this.listIDs = newIDs;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new QuizViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        boolean isDownloaded = listIDs.contains(quiz.getId());
        holder.setQuiz(quiz, isDownloaded);
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public void scrollToTop() {
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    public class QuizzesDiffUtil extends DiffUtil.Callback {

        private List<Quiz> oldList, newList;

        QuizzesDiffUtil(List<Quiz> oldList, List<Quiz> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return oldList.get(i).getName().equals(newList.get(i1).getName());
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return oldList.get(i).equals(newList.get(i1));
        }
    }
}
