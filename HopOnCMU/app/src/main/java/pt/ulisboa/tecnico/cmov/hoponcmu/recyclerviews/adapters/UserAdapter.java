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
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders.UserViewHolder;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> users;
    private LinearLayoutManager layoutManager;
    private Context context;

    public UserAdapter(Context context, LinearLayoutManager layoutManager) {
        this.context = context;
        this.layoutManager = layoutManager;
        this.users = new ArrayList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.setUser(context, users.get(position), position);
    }

    public void scrollToTop() {
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    public void setUsers(List<User> newUsers) {
        UsersDiffUtil usersDiffUtil =
                new UsersDiffUtil(users, newUsers);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(usersDiffUtil);
        users.clear();
        users.addAll(newUsers);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersDiffUtil extends DiffUtil.Callback {

        private List<User> oldList, newList;

        UsersDiffUtil(List<User> oldList, List<User> newList) {
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
            return oldList.get(i).getUsername().equals(newList.get(i1).getUsername());
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return oldList.get(i).equals(newList.get(i1));
        }
    }
}
