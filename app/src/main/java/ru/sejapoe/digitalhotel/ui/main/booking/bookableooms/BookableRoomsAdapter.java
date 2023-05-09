package ru.sejapoe.digitalhotel.ui.main.booking.bookableooms;

import androidx.annotation.NonNull;

import java.util.List;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.BookableRoom;
import ru.sejapoe.digitalhotel.databinding.BookableRoomItemBinding;
import ru.sejapoe.digitalhotel.ui.core.ClickableItemListAdapter;

public class BookableRoomsAdapter extends ClickableItemListAdapter<BookableRoomItemBinding, BookableRoom> {

    public BookableRoomsAdapter(List<BookableRoom> bookableRooms, OnItemClickListener<BookableRoom> listener) {
        super(BookableRoomItemBinding.class, BookableRoom.class);
        addItems(bookableRooms);
        setOnClickListener(listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookableRoomItemBinding binding, BookableRoom bookableRoom) {
        binding.roomType.setText(bookableRoom.getRoomType().getName());
        binding.roomPrice.setText(binding.getRoot().getResources().getString(R.string.price, bookableRoom.getRoomType().getPrice()));
        binding.roomsCount.setText(binding.getRoot().getResources().getQuantityString(R.plurals.rooms_count, bookableRoom.getCount(), bookableRoom.getCount()));
    }
}
