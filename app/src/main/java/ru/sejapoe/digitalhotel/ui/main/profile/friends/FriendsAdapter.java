package ru.sejapoe.digitalhotel.ui.main.profile.friends;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.sejapoe.digitalhotel.data.model.user.FriendRequests;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.databinding.FriendItemBinding;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private OnClickListener onClickListener;
    private final SortedList<Friend> friends = new SortedList<>(Friend.class, new SortedListAdapterCallback<Friend>(this) {
        @Override
        public int compare(Friend o1, Friend o2) {
            if (o1.friendStatus == o2.friendStatus) {
                return Integer.compare(o1.userLess.getId(), o2.userLess.getId());
            }
            return o1.friendStatus.compareTo(o2.friendStatus);
        }

        @Override
        public boolean areContentsTheSame(Friend oldItem, Friend newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Friend item1, Friend item2) {
            return item1 == item2;
        }
    });

    public void addFriends(List<UserLess> friends) {
        this.friends.addAll(friends.stream().map(userLess -> new Friend(userLess, FriendStatus.ACCEPTED)).collect(Collectors.toList()));
    }

    public void addFriendRequests(FriendRequests friendRequests) {
        this.friends.addAll(friendRequests.getIncome().stream().map(userLess -> new Friend(userLess, FriendStatus.INCOME)).collect(Collectors.toList()));
        this.friends.addAll(friendRequests.getOutcome().stream().map(userLess -> new Friend(userLess, FriendStatus.OUTCOME)).collect(Collectors.toList()));
    }

    public void clear() {
        this.friends.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FriendItemBinding binding = FriendItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.text.setText(friends.get(position).userLess.getFullName());
        holder.binding.getRoot().setOnClickListener(v -> {
            if (onClickListener != null) onClickListener.onClick(friends.get(position));
        });
        switch (friends.get(position).friendStatus) {
            case ACCEPTED:
                holder.binding.text.setTextColor(Color.GREEN);
                break;
            case INCOME:
                holder.binding.text.setTextColor(Color.RED);
                break;
            case OUTCOME:
                holder.binding.text.setTextColor(Color.CYAN);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(Friend friend);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FriendItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendItemBinding.bind(itemView);
        }
    }

    public static class Friend {
        private final UserLess userLess;
        private final FriendStatus friendStatus;

        public Friend(UserLess userLess, FriendStatus friendStatus) {
            this.userLess = userLess;
            this.friendStatus = friendStatus;
        }

        public UserLess getUserLess() {
            return userLess;
        }

        public FriendStatus getFriendStatus() {
            return friendStatus;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Friend friend = (Friend) o;
            return userLess.getId() == friend.userLess.getId() && friendStatus == friend.friendStatus;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userLess, friendStatus);
        }
    }

    enum FriendStatus {
        INCOME, OUTCOME, ACCEPTED
    }
}
