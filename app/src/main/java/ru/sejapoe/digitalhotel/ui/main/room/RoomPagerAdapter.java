package ru.sejapoe.digitalhotel.ui.main.room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.sejapoe.digitalhotel.data.model.hotel.room.Occupation;
import ru.sejapoe.digitalhotel.ui.main.room.occupation.OccupationFragment;

public class RoomPagerAdapter extends FragmentStateAdapter {
    private final List<Occupation> occupations = new ArrayList<>();

    public RoomPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public void setOccupations(Collection<Occupation> occupations) {
        this.occupations.clear();
        this.occupations.addAll(occupations);
        notifyItemRangeChanged(0, occupations.size());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Occupation occupation = occupations.get(position);
        OccupationFragment occupationFragment = new OccupationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(OccupationFragment.ARG_OCCUPATION, occupation);
        occupationFragment.setArguments(bundle);
        return occupationFragment;
    }

    @Override
    public int getItemCount() {
        return occupations.size();
    }

    public Occupation getItem(int position) {
        return occupations.get(position);
    }
}
