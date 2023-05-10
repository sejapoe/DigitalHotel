package ru.sejapoe.digitalhotel.ui.main.room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomAccess;
import ru.sejapoe.digitalhotel.ui.main.room.access.AccessFragment;

public class RoomPagerAdapter extends FragmentStateAdapter {
    private final List<RoomAccess> accesses = new ArrayList<>();

    public RoomPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public void setAccesses(Collection<RoomAccess> accesses) {
        this.accesses.clear();
        this.accesses.addAll(accesses);
        notifyItemRangeChanged(0, accesses.size());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        RoomAccess roomAccess = accesses.get(position);
        AccessFragment accessFragment = new AccessFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AccessFragment.ARG_ACCESS, roomAccess);
        accessFragment.setArguments(bundle);
        return accessFragment;
    }

    @Override
    public int getItemCount() {
        return accesses.size();
    }

    public RoomAccess getItem(int position) {
        return accesses.get(position);
    }
}
