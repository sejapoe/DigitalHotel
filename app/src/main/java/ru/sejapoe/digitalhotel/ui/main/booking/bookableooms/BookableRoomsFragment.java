package ru.sejapoe.digitalhotel.ui.main.booking.bookableooms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.BookableRoom;
import ru.sejapoe.digitalhotel.databinding.FragmentBookableRoomsBinding;

@AndroidEntryPoint
public class BookableRoomsFragment extends Fragment {
    private FragmentBookableRoomsBinding binding;
    private BookableRoomsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookableRoomsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookableRoomsViewModel.class);

        Bundle arguments = requireArguments();
        BookableRoom[] bookableRooms = (BookableRoom[]) arguments.getParcelableArray("bookableRooms");
        String checkIn = arguments.getString("checkIn");
        String checkOut = arguments.getString("checkOut");
        int hotelId = arguments.getInt("hotelId");

        BookableRoomsAdapter.OnItemClickListener listener = bookableRoom -> {
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            viewModel.book(hotelId, checkIn, checkOut, bookableRoom.getRoomType().getId()).observe(getViewLifecycleOwner(), unused ->
                    NavHostFragment.findNavController(this).navigate(R.id.action_bookableRoomsFragment_to_bookingFragment));
        };
        binding.bookableRoomsRecyclerView.addItemDecoration(new BookableRoomsAdapter.ItemPaddingDecorator(getResources().getDimensionPixelSize(R.dimen.item_padding)));
        if (bookableRooms != null) {
            binding.bookableRoomsRecyclerView.setAdapter(new BookableRoomsAdapter(Arrays.asList(bookableRooms), listener));
        }
//        binding.bookableRoomsRecyclerView.getAdapter()
    }
}
