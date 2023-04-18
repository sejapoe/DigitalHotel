package ru.sejapoe.digitalhotel.ui.main.booking.create.guestcount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sejapoe.digitalhotel.databinding.GuestsCountFormBinding;
import ru.sejapoe.digitalhotel.ui.main.booking.create.BookingCreateViewModel;

public class GuestCountDialogFragment extends DialogFragment {
    private GuestsCountFormBinding binding;
    private GuestCountViewModel viewModel;
    private GuestCountListener guestCountListener;

    public GuestCountDialogFragment(BookingCreateViewModel.GuestsCount value) {
        Bundle args = Bundle.EMPTY.deepCopy();
        args.putInt("adults_count", value.getAdultsCount());
        args.putInt("children_count", value.getChildrenCount());
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GuestsCountFormBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GuestCountViewModel.class);
        assert getArguments() != null;
        viewModel.setCounts(getArguments().getInt("adults_count"), getArguments().getInt("children_count"));

        viewModel.getStatus().observe(this, status -> {
            binding.adultCallback.setText(String.valueOf(status.getAdultsCount()));
            binding.adultPlus.setEnabled(status.canIncreaseAdults());
            binding.adultMinus.setEnabled(status.canDecreaseAdults());

            binding.childrenCallback.setText(String.valueOf(status.getChildrenCount()));
            binding.childrenPlus.setEnabled(status.canIncreaseChildren());
            binding.childrenMinus.setEnabled(status.canDecreaseChildren());
        });

        binding.adultPlus.setOnClickListener(viewModel::increaseAdultsCount);
        binding.adultMinus.setOnClickListener(viewModel::decreaseAdultsCount);
        binding.childrenPlus.setOnClickListener(viewModel::increaseChildrenCount);
        binding.childrenMinus.setOnClickListener(viewModel::decreaseChildrenCount);
        binding.ok.setOnClickListener(v -> {
            GuestCountViewModel.Status value = viewModel.getStatus().getValue();
            if (value == null) return;
            guestCountListener.onGuestCountSelected(value.getAdultsCount(), value.getChildrenCount());
            dismiss();
        });
    }

    public void setGuestCountListener(GuestCountListener guestCountListener) {
        this.guestCountListener = guestCountListener;
    }

    public interface GuestCountListener {
        void onGuestCountSelected(int adultCount, int childCount);
    }
}
