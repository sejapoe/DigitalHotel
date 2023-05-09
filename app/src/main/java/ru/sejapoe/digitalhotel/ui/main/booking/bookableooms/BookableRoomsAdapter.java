package ru.sejapoe.digitalhotel.ui.main.booking.bookableooms;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.BookableRoom;
import ru.sejapoe.digitalhotel.databinding.BookableRoomItemBinding;

public class BookableRoomsAdapter extends RecyclerView.Adapter<BookableRoomsAdapter.ViewHolder> {
    private final List<BookableRoom> bookableRooms;
    private final OnItemClickListener listener;

    public BookableRoomsAdapter(List<BookableRoom> bookableRooms, OnItemClickListener listener) {
        this.bookableRooms = bookableRooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BookableRoomItemBinding binding = BookableRoomItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot(), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookableRoom bookableRoom = bookableRooms.get(position);
        holder.binding.roomType.setText(bookableRoom.getRoomType().getName());
        holder.binding.roomPrice.setText(holder.itemView.getResources().getString(R.string.price, bookableRoom.getRoomType().getPrice()));
        holder.binding.roomsCount.setText(holder.itemView.getResources().getQuantityString(R.plurals.rooms_count, bookableRoom.getCount(), bookableRoom.getCount()));
        holder.binding.getRoot().setOnClickListener(v -> holder.listener.onClick(bookableRooms.get(position)));
    }


    @Override
    public int getItemCount() {
        return bookableRooms.size();
    }

    interface OnItemClickListener {
        void onClick(BookableRoom bookableRoom);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final BookableRoomItemBinding binding;
        private final OnItemClickListener listener;

        public ViewHolder(@NonNull View view, OnItemClickListener listener) {
            super(view);
            binding = BookableRoomItemBinding.bind(view);
            this.listener = listener;
        }
    }

    public static class ItemPaddingDecorator extends RecyclerView.ItemDecoration {
        private final int padding;


        public ItemPaddingDecorator(int padding) {
            this.padding = padding;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.top = padding;
            outRect.bottom = padding;
            outRect.left = padding;
            outRect.right = padding;
        }
    }
}
