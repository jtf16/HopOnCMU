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
import pt.ulisboa.tecnico.cmov.hoponcmu.data.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders.MonumentViewHolder;

public class MonumentAdapter extends RecyclerView.Adapter<MonumentViewHolder> {

    private List<Monument> monuments;
    private LinearLayoutManager mLayoutManager;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MonumentAdapter(Context context, LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        this.monuments = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MonumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_monument_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new MonumentViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MonumentViewHolder holder, int position) {
        // - Get element from clients at this position
        // - Replace the contents of the view with that element
        holder.setMonument(monuments.get(position));
    }

    /**
     * Scrolls to the top of the {@link List<Monument>}
     */
    public void scrollToTop() {
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    /**
     * Use this method to update the {@link List<Monument>} to be shown to the user
     *
     * @param newMonuments
     */
    public void setUsers(List<Monument> newMonuments) {
        MonumentAdapter.MonumentsDiffUtil usersDiffUtil =
                new MonumentAdapter.MonumentsDiffUtil(monuments, newMonuments);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(usersDiffUtil);
        monuments.clear();
        monuments.addAll(newMonuments);
        diffResult.dispatchUpdatesTo(this);
    }

    // Return the size of your monuments list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return monuments.size();
    }

    public class MonumentsDiffUtil extends DiffUtil.Callback {

        private List<Monument> oldList, newList;

        public MonumentsDiffUtil(List<Monument> oldList, List<Monument> newList) {
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
