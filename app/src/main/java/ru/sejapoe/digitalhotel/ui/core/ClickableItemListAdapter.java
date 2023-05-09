package ru.sejapoe.digitalhotel.ui.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class ClickableItemListAdapter<T extends ViewBinding, E extends Comparable<E>> extends RecyclerView.Adapter<ClickableItemListAdapter<T, E>.ViewHolder> {
    private final Class<T> viewHolderBindingClazz;
    private final SortedList<E> items;
    private OnItemClickListener<E> clickListener;

    protected ClickableItemListAdapter(Class<T> viewHolderBindingClazz, Class<E> elementClazz) {
        this.viewHolderBindingClazz = viewHolderBindingClazz;
        this.items = new SortedList<>(elementClazz, new SortedListAdapterCallback<E>(this) {
            @Override
            public int compare(E o1, E o2) {
                return o1.compareTo(o2);
            }

            @Override
            public boolean areContentsTheSame(E oldItem, E newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(E item1, E item2) {
                return item1 == item2;
            }
        });
    }

    public void setItems(List<E> newItems) {
        clearItems();
        addItems(newItems);
    }

    public void addItems(List<E> newItems) {
        items.addAll(newItems);
    }

    public void clearItems() {
        items.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        try {
            T binding = viewHolderBindingClazz.cast(viewHolderBindingClazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class).invoke(null, inflater, parent, false));
            assert binding != null;
            return new ViewHolder(binding.getRoot());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 AssertionError e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final E element = items.get(position);
        onBindViewHolder(holder.binding, element);
        holder.binding.getRoot().setOnClickListener(v -> invokeListener(element));
    }

    private void invokeListener(E element) {
        if (clickListener != null) clickListener.onClick(element);
    }

    public abstract void onBindViewHolder(@NonNull T binding, @NonNull E element);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickListener(OnItemClickListener<E> clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener<E> {
        void onClick(E element);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        T binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                binding = viewHolderBindingClazz.cast(viewHolderBindingClazz.getMethod("bind", View.class).invoke(null, itemView));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
