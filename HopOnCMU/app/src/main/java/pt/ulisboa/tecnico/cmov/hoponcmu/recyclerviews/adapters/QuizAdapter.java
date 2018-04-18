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
    private LinearLayoutManager mLayoutManager;
    private Context mContext;

    public QuizAdapter(Context context, LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        this.quizList = new ArrayList<>();
        this.mContext = context;
    }

    public void setQuizzes(List<Quiz> newQuizList) {
        QuizAdapter.QuizzesDiffUtil monumentsDiffUtil =
                new QuizAdapter.QuizzesDiffUtil(quizList, newQuizList);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(monumentsDiffUtil);
        quizList.clear();
        quizList.addAll(newQuizList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_downloads_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new QuizViewHolder(mContext, v);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        holder.setQuiz(quizList.get(position));
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public class QuizzesDiffUtil extends DiffUtil.Callback {

        private List<Quiz> oldList, newList;

        public QuizzesDiffUtil(List<Quiz> oldList, List<Quiz> newList) {
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
