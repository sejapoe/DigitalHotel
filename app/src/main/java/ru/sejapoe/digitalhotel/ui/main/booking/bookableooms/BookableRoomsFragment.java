package ru.sejapoe.digitalhotel.ui.main.booking.bookableooms;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
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
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.BookableRoom;
import ru.sejapoe.digitalhotel.databinding.FragmentBookableRoomsBinding;
import ru.sejapoe.digitalhotel.ui.core.ClickableItemListAdapter;
import ru.sejapoe.digitalhotel.ui.core.ItemPaddingDecorator;

@AndroidEntryPoint
public class BookableRoomsFragment extends Fragment {
    private FragmentBookableRoomsBinding binding;
    private BookableRoomsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookableRoomsBinding.inflate(inflater, container, false);
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

        ClickableItemListAdapter.OnItemClickListener<BookableRoom> listener = bookableRoom -> {
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            viewModel.book(hotelId, checkIn, checkOut, bookableRoom.getRoomType().getId()).observe(getViewLifecycleOwner(), unused -> {
                Calendar beginTime = Calendar.getInstance();
                List<Integer> checkInTimes = Arrays.stream(checkIn.split("-")).map(Integer::parseInt).collect(Collectors.toList());
                beginTime.set(checkInTimes.get(0), checkInTimes.get(1), checkInTimes.get(2), 12, 0);
                Calendar endTime = Calendar.getInstance();
                beginTime.set(checkInTimes.get(0), checkInTimes.get(1), checkInTimes.get(2), 12, 30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(Events.TITLE, "Your booking")
                        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
                startActivity(intent);
                NavHostFragment.findNavController(this).navigate(R.id.action_bookableRoomsFragment_to_bookingFragment);
            });
        };
        binding.bookableRoomsRecyclerView.addItemDecoration(new ItemPaddingDecorator(getResources().getDimensionPixelSize(R.dimen.item_padding)));
        if (bookableRooms != null) {
            binding.bookableRoomsRecyclerView.setAdapter(new BookableRoomsAdapter(Arrays.asList(bookableRooms), listener));
        }
//        binding.bookableRoomsRecyclerView.getAdapter()
    }
}
