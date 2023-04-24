package ru.sejapoe.digitalhotel.ui.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sejapoe.digitalhotel.databinding.FragmentQrCodeBinding;

public class QrCodeDialogFragment extends DialogFragment {
    private FragmentQrCodeBinding binding;
    private QrCodeViewModel viewModel;

    public QrCodeDialogFragment(String content) {
        Bundle args = Bundle.EMPTY.deepCopy();
        args.putString("content", content);
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QrCodeViewModel.class);
        String content = requireArguments().getString("content");
        viewModel.generateQrCode(content).observe(getViewLifecycleOwner(), bitmap -> {
            binding.qrCodeContainer.setImageBitmap(bitmap);
        });
    }
}
