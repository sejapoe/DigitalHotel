package ru.sejapoe.digitalhotel.ui.main.profile.friends;

import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.sejapoe.digitalhotel.data.model.user.FriendRequests;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.databinding.FriendItemBinding;
import ru.sejapoe.digitalhotel.ui.core.ClickableItemListAdapter;

public class FriendsAdapter extends ClickableItemListAdapter<FriendItemBinding, FriendsAdapter.Friend> {
    protected FriendsAdapter() {
        super(FriendItemBinding.class, Friend.class);
    }

    public void addFriends(List<UserLess> friends) {
        addItems(friends.stream().map(userLess -> new FriendsAdapter.Friend(userLess, FriendsAdapter.FriendStatus.ACCEPTED)).collect(Collectors.toList()));
    }

    public void addFriendRequests(FriendRequests friendRequests) {
        addItems(friendRequests.getIncome().stream().map(userLess -> new FriendsAdapter.Friend(userLess, FriendsAdapter.FriendStatus.INCOME)).collect(Collectors.toList()));
        addItems(friendRequests.getOutcome().stream().map(userLess -> new FriendsAdapter.Friend(userLess, FriendsAdapter.FriendStatus.OUTCOME)).collect(Collectors.toList()));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendItemBinding binding, @NonNull Friend element) {
        binding.text.setText(element.userLess.getFullName());
        switch (element.friendStatus) {
            case ACCEPTED:
                binding.text.setTextColor(Color.GREEN);
                break;
            case INCOME:
                binding.text.setTextColor(Color.RED);
                break;
            case OUTCOME:
                binding.text.setTextColor(Color.CYAN);
                break;
        }
    }

    public static class Friend implements Comparable<Friend> {
        private final UserLess userLess;
        private final FriendsAdapter.FriendStatus friendStatus;

        public Friend(UserLess userLess, FriendsAdapter.FriendStatus friendStatus) {
            this.userLess = userLess;
            this.friendStatus = friendStatus;
        }

        public UserLess getUserLess() {
            return userLess;
        }

        public FriendsAdapter.FriendStatus getFriendStatus() {
            return friendStatus;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FriendsAdapter.Friend friend = (FriendsAdapter.Friend) o;
            return userLess.getId() == friend.userLess.getId() && friendStatus == friend.friendStatus;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userLess, friendStatus);
        }

        @Override
        public int compareTo(Friend o) {
            return friendStatus == o.friendStatus ? Integer.compare(userLess.getId(), o.userLess.getId()) : friendStatus.compareTo(o.friendStatus);
        }
    }

    enum FriendStatus {
        INCOME, OUTCOME, ACCEPTED
    }
}
