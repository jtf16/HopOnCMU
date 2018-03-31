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
    private LinearLayoutManager mLayoutManager;
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(Context context, LinearLayoutManager mLayoutManager) {
        this.mContext = context;
        this.mLayoutManager = mLayoutManager;
        this.users = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user_item, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new UserViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        // - Get element from clients at this position
        // - Replace the contents of the view with that element
        holder.setUser(mContext, users.get(position), position);
    }

    /**
     * Scrolls to the top of the {@link List<User>}
     */
    public void scrollToTop() {
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    /**
     * Use this method to update the {@link List<User>} to be shown to the user
     *
     * @param newUsers
     */
    public void setUsers(List<User> newUsers) {
        UsersDiffUtil usersDiffUtil =
                new UsersDiffUtil(users, newUsers);
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(usersDiffUtil);
        users.clear();
        users.addAll(newUsers);
        diffResult.dispatchUpdatesTo(this);
    }

    // Return the size of your users list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersDiffUtil extends DiffUtil.Callback {

        private List<User> oldList, newList;

        public UsersDiffUtil(List<User> oldList, List<User> newList) {
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
