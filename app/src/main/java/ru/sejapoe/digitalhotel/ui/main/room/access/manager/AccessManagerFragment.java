package ru.sejapoe.digitalhotel.ui.main.room.access.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.databinding.FragmentAccessManagerBinding;
import ru.sejapoe.digitalhotel.ui.core.ItemPaddingDecorator;

@AndroidEntryPoint
public class AccessManagerFragment extends Fragment {
    private FragmentAccessManagerBinding binding;
    private AccessManagerViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccessManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AccessManagerViewModel.class);
        int roomId = AccessManagerFragmentArgs.fromBundle(requireArguments()).getRoomId();
        viewModel.setRoomId(roomId);

        AccessManagerAdapter adapter = new AccessManagerAdapter();
        adapter.setOnClickListener(element -> {
            NavHostFragment.findNavController(this).navigate(AccessManagerFragmentDirections.actionRoomFragmentToAccessEditorFragment(element.getId()));
        });
        binding.accessShareRecyclerView.setAdapter(adapter);
        binding.accessShareRecyclerView.addItemDecoration(new ItemPaddingDecorator(getResources().getDimensionPixelSize(R.dimen.item_padding)));
        binding.add.setOnClickListener(v -> viewModel.getFriends().observe(getViewLifecycleOwner(), friends -> {
            new MaterialDialog.Builder(requireContext())
                    .title(R.string.sharing_access)
                    .items(friends.stream().map(UserLess::getFullName).collect(Collectors.toList()))
                    .itemsCallbackMultiChoice(null, (dialog, which, text) -> {
                        List<String> usernames = Arrays.stream(which).map(integer -> friends.get(integer).getUsername()).collect(Collectors.toList());
                        viewModel.shareAccess(usernames).observe(getViewLifecycleOwner(), aBoolean -> {
                            if (aBoolean) load();
                        });
                        return true;
                            })
                            .positiveText(R.string.share_access)
                            .show();
                }
        ));
        load();
    }

    private void load() {
        viewModel.getSharedAccesses().observe(getViewLifecycleOwner(), accessShares -> ((AccessManagerAdapter) binding.accessShareRecyclerView.getAdapter()).addItems(accessShares));
    }
}
