package com.database.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserHolder> {
    //private List<User> users = new ArrayList<>();
    private OnItemClickListener listener;

    public UserAdapter() {
        super(DIFF_CALLBACK);
    }
    //This use of DiffUtil allows for
    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getFirstname().equals(newItem.getFirstname()) &&
                   oldItem.getLastname().equals(newItem.getLastname()) &&
                   oldItem.getAddress().equals(newItem.getAddress());
        }
    };

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userrow, parent, false);
        return new UserHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser = getItem(position);
        holder.firstname.setText(currentUser.getFirstname());
        holder.lastname.setText(currentUser.getLastname());
        holder.address.setText(currentUser.getAddress());
    }
    //This removed/commented out as it is now handled by the ListView Adapter
    /*@Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }*/

    public User getUserPosition(int position) {
        return getItem(position);
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView firstname;
        private TextView lastname;
        private TextView address;

        public UserHolder(View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.nameO);
            lastname = itemView.findViewById(R.id.surnameO);
            address = itemView.findViewById(R.id.AddO);

            itemView.setOnClickListener(view -> {
                //listener.onItemClick(users.get(getAdapterPosition()));
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
