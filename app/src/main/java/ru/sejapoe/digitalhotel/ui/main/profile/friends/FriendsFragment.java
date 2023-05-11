package ru.sejapoe.digitalhotel.ui.main.profile.friends;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.databinding.FragmentFriendsBinding;
import ru.sejapoe.digitalhotel.ui.core.ItemPaddingDecorator;
import ru.sejapoe.digitalhotel.ui.login.LoginViewModel;

@AndroidEntryPoint
public class FriendsFragment extends Fragment {
    private FragmentFriendsBinding binding;
    private FriendViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        FriendsAdapter adapter = new FriendsAdapter();
        adapter.setOnClickListener(friend -> {
            if (friend.getFriendStatus() == FriendsAdapter.FriendStatus.INCOME) {
                viewModel.sendOrAcceptRequest(friend.getUserLess().getUsername()).observe(getViewLifecycleOwner(), this::sendOrAcceptRequestObserver);
            }
        });
        binding.friendsRecycler.setAdapter(adapter);
        binding.friendsRecycler.addItemDecoration(new ItemPaddingDecorator(16));
        initFriends();
        binding.addFriend.setOnClickListener(v -> {
            new MaterialDialog.Builder(requireContext())
                    .title(R.string.search_friend)
                    .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    .input(getString(R.string.friend_s_email), "", false, (dialog, input) -> {
                        boolean isInputValid = LoginViewModel.EMAIL_PATTERN.matcher(input).matches();
                        dialog.getInputEditText().setError(
                                isInputValid ? null : getString(R.string.invalid_username)
                        );
                        dialog.getActionButton(DialogAction.POSITIVE).setEnabled(isInputValid);
                    })
                    .positiveText(R.string.search)
                    .cancelable(true)
                    .onPositive((dialog, which) -> {
                        viewModel.sendOrAcceptRequest(dialog.getInputEditText().getEditableText().toString()).observe(getViewLifecycleOwner(), this::sendOrAcceptRequestObserver);
                    })
                    .show();
        });
    }

    private void sendOrAcceptRequestObserver(int integer) {
        switch (integer) {
            case 200:
                initFriends();
                break;
            case 404:
                Toast.makeText(requireContext(), R.string.user_not_found, Toast.LENGTH_SHORT).show();
                break;
            case 302:
                Toast.makeText(requireContext(), R.string.already_friends, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initFriends() {
        ((FriendsAdapter) binding.friendsRecycler.getAdapter()).clearItems();
        viewModel.getFriends().observe(getViewLifecycleOwner(), friends -> {
            ((FriendsAdapter) binding.friendsRecycler.getAdapter()).addFriends(friends);
        });
        viewModel.getFriendRequests().observe(getViewLifecycleOwner(), friendRequests -> {
            ((FriendsAdapter) binding.friendsRecycler.getAdapter()).addFriendRequests(friendRequests);
        });
    }
}
