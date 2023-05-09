package ru.sejapoe.digitalhotel.ui.core;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemPaddingDecorator extends RecyclerView.ItemDecoration {
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
