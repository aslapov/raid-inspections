package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    private final List<T> mItems = new ArrayList<>();
    private Integer mSelectedPosition = -1; // -1 - Элемент не выбран

    @Nullable
    private OnItemClickListener<T> mOnItemClickListener;

    private final View.OnClickListener mInternalListener = (view) -> {
        if (mOnItemClickListener != null) {
            int position = (int) view.getTag();
            T item = mItems.get(position);
            mSelectedPosition = position;
            mOnItemClickListener.onItemClick(item);
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

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        //int previousPosition = mSelectedPosition;
        mSelectedPosition = position;
        /*notifyItemChanged(previousPosition);
        notifyItemChanged(position);*/
        refreshRecycler();
    }

    public void unSelectPosition(int position) {
        notifyItemChanged(position);
        mSelectedPosition = -1;
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

        holder.itemView.setSelected(false);
        if (position == mSelectedPosition)
            holder.itemView.setSelected(true);
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
