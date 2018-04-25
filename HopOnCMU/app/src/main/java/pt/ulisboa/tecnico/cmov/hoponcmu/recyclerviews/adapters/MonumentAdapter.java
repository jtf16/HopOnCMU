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
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders.MonumentViewHolder;

public class MonumentAdapter extends RecyclerView.Adapter<MonumentViewHolder> {

    private List<Monument> monuments;
    private LinearLayoutManager layoutManager;
    private Context context;
    private LoaderManager loader;

    public MonumentAdapter(Context context, LinearLayoutManager layoutManager, LoaderManager loader) {
        this.context = context;
        this.layoutManager = layoutManager;
        this.monuments = new ArrayList<>();
        this.loader = loader;
    }

    @Override
    public MonumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new MonumentViewHolder(context, v, loader);
    }

    @Override
    public void onBindViewHolder(MonumentViewHolder holder, int position) {
        holder.setMonument(monuments.get(position));
    }

    public void scrollToTop() {
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    public void setMonuments(List<Monument> newMonuments) {
        MonumentAdapter.MonumentsDiffUtil monumentsDiffUtil =
                new MonumentAdapter.MonumentsDiffUtil(monuments, newMonuments);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(monumentsDiffUtil);
        monuments.clear();
        monuments.addAll(newMonuments);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return monuments.size();
    }

    public class MonumentsDiffUtil extends DiffUtil.Callback {

        private List<Monument> oldList, newList;

        MonumentsDiffUtil(List<Monument> oldList, List<Monument> newList) {
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
