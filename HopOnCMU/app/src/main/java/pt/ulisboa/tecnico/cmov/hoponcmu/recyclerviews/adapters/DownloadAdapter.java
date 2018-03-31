package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters;

import android.content.Context;
import android.support.v4.app.LoaderManager;
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
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders.DownloadViewHolder;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder> {

    private List<Quiz> quizzes;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    private LoaderManager mLoader;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DownloadAdapter(Context context, LinearLayoutManager mLayoutManager, LoaderManager loader) {
        this.mLayoutManager = mLayoutManager;
        this.quizzes = new ArrayList<>();
        this.mContext = context;
        this.mLoader = loader;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_downloads_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new DownloadViewHolder(mContext, v, mLoader);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DownloadViewHolder holder, int position) {
        // - Get element from clients at this position
        // - Replace the contents of the view with that element
        holder.setQuiz(quizzes.get(position));
    }

    /**
     * Scrolls to the top of the {@link List<Quiz>}
     */
    public void scrollToTop() {
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    /**
     * Use this method to update the {@link List<Quiz>} to be shown to the user
     *
     * @param newQuizzes
     */
    public void setQuizzes(List<Quiz> newQuizzes) {
        DownloadAdapter.DownloadsDiffUtil quizzesDiffUtil =
                new DownloadAdapter.DownloadsDiffUtil(quizzes, newQuizzes);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(quizzesDiffUtil);
        quizzes.clear();
        quizzes.addAll(newQuizzes);
        diffResult.dispatchUpdatesTo(this);
    }

    // Return the size of your quizzes list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class DownloadsDiffUtil extends DiffUtil.Callback {

        private List<Quiz> oldList, newList;

        public DownloadsDiffUtil(List<Quiz> oldList, List<Quiz> newList) {
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
