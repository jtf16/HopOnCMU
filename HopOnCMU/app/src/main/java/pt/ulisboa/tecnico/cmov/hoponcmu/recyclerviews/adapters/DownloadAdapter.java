package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.MonumentByIDLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders.DownloadViewHolder;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder>
        implements LoaderManager.LoaderCallbacks {

    public static final String ARG_QUIZ = "quiz";
    private static final int LOADER_MONUMENT = 1;
    private List<Quiz> quizzes;
    private LinearLayoutManager layoutManager;
    private Context context;
    private LoaderManager loader;
    private DownloadViewHolder downloadViewHolder;

    public DownloadAdapter(Context context, LinearLayoutManager layoutManager, LoaderManager loader) {
        this.layoutManager = layoutManager;
        this.quizzes = new ArrayList<>();
        this.context = context;
        this.loader = loader;
    }

    @Override
    public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new DownloadViewHolder(context, v, loader);
    }

    @Override
    public void onBindViewHolder(DownloadViewHolder holder, int position) {
        downloadViewHolder = holder;
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ, quizzes.get(position));
        loader.restartLoader(LOADER_MONUMENT, args, this);
        holder.setQuiz(quizzes.get(position));
    }

    public void scrollToTop() {
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    public void setQuizzes(List<Quiz> newQuizzes) {
        DownloadAdapter.DownloadsDiffUtil quizzesDiffUtil =
                new DownloadAdapter.DownloadsDiffUtil(quizzes, newQuizzes);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(quizzesDiffUtil);
        quizzes.clear();
        quizzes.addAll(newQuizzes);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MONUMENT:
                Quiz quiz = (Quiz) args.getSerializable(ARG_QUIZ);
                return new MonumentByIDLoader(context, quiz.getMonumentID());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case LOADER_MONUMENT:
                downloadViewHolder.setMonumentName((Monument) data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public class DownloadsDiffUtil extends DiffUtil.Callback {

        private List<Quiz> oldList, newList;

        DownloadsDiffUtil(List<Quiz> oldList, List<Quiz> newList) {
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
