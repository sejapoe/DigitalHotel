package ru.sejapoe.digitalhotel.ui.main.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.Booking;
import ru.sejapoe.digitalhotel.databinding.BookingItemBinding;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private List<Booking> items = new ArrayList<>();

    private final OnItemClickListener listener;

    public BookingsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void setItems(List<Booking> items) {
        this.items = items;
        notifyItemRangeChanged(0, items.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BookingItemBinding binding = BookingItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking item = items.get(position);
        holder.binding.hotelName.setText(item.getHotel().getName());
        holder.binding.roomType.setText(item.getRoomType().getName());
        holder.binding.price.setText(holder.itemView.getResources().getString(R.string.price, item.getRoomType().getPrice()));
        holder.binding.dates.setText(holder.itemView.getResources().getString(R.string.dates, item.getCheckInDate(), item.getCheckOutDate()));
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(items.get(position)));
    }

    interface OnItemClickListener {
        void onClick(Booking booking);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final BookingItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BookingItemBinding.bind(itemView);
        }
    }
}
