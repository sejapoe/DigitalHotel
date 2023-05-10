package ru.sejapoe.digitalhotel.ui.main.room.access.manager;

import androidx.annotation.NonNull;

import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShare;
import ru.sejapoe.digitalhotel.databinding.AccessShareItemBinding;
import ru.sejapoe.digitalhotel.ui.core.ClickableItemListAdapter;

public class AccessManagerAdapter extends ClickableItemListAdapter<AccessShareItemBinding, AccessShare> {

    public AccessManagerAdapter() {
        super(AccessShareItemBinding.class, AccessShare.class);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessShareItemBinding binding, @NonNull AccessShare element) {
        binding.toTextView.setText(element.getUser().getFullName());
    }
}
