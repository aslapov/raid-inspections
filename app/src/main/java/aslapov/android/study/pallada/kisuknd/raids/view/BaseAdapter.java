package aslapov.android.study.pallada.kisuknd.raids.view;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    private final List<T> mItems = new ArrayList<>();
    private int mSelectedItemPosition = 0;

    @Nullable
    private OnItemClickListener<T> mOnItemClickListener;

    private final View.OnClickListener mInternalListener = (view) -> {
        if (mOnItemClickListener != null) {
            int position = (int) view.getTag();
            T item = mItems.get(position);
            mOnItemClickListener.onItemClick(item);

            int previousItem = mSelectedItemPosition;
            mSelectedItemPosition = position;
            notifyItemChanged(previousItem);
            notifyItemChanged(position);
        }
    };

    @Nullable
    private EmptyRecyclerView mRecyclerView;

    public BaseAdapter(@NonNull List<T> items) {
        mItems.addAll(items);
    }

    public void attachToRecyclerView(@NonNull EmptyRecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(this);
        refreshRecycler();
    }

    public final void add(@NonNull T value) {
        mItems.add(value);
        refreshRecycler();
    }

    public final void changeDataSet(@NonNull List<T> values) {
        mItems.clear();
        mItems.addAll(values);
        refreshRecycler();
    }

    public final void clear() {
        mItems.clear();
        refreshRecycler();
    }

    public void refreshRecycler() {
        notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.checkIfEmpty();
        }
    }

    @CallSuper
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mInternalListener);

        /*holder.itemView.setBackgroundColor(holder.itemView.getSolidColor());
        if (mSelectedItemPosition == position)
            holder.itemView.setBackgroundColor(Color.GRAY);*/
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener<T> {

        void onItemClick(@NonNull T item);

    }

}
