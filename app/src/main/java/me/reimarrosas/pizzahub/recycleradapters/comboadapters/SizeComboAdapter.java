package me.reimarrosas.pizzahub.recycleradapters.comboadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Checkable;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Size;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;

public class SizeComboAdapter extends RecyclerView.Adapter<DefaultViewHolder> implements Updatable<Size> {

    private final List<Size> sizeList = new ArrayList<>();

    private Checkable previousClicked;
    private Context context;
    private Notifiable n;

    public SizeComboAdapter(Context context, Notifiable n) {
        this.context = context;
        this.previousClicked = null;
        this.n = n;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row, parent, false);
        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        Size s = sizeList.get(position);

        if (s.getName().equalsIgnoreCase("small")) {
            previousClicked = holder;
            holder.updateDataCheckedState();
            n.notifyUpdatedData(s, MenuItem.MenuItemType.SIZE);
        }

        holder.setName(s.getName());
        Glide.with(context)
                .asBitmap()
                .load(s.getImageUrl())
                .into(holder.getThumbNail());
        holder.addCheckListener(checkCardHandler(holder, s));
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    @Override
    public List<Size> getDataList() {
        return sizeList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

    public void updateClickedState(Checkable current) {
        if (previousClicked != null) {
            previousClicked.updateDataCheckedState();
        }

        previousClicked = current;
        current.updateDataCheckedState();
    }

    private View.OnClickListener checkCardHandler(DefaultViewHolder holder, Size s) {
        return view -> {
            updateClickedState(holder);
            n.notifyUpdatedData(s, MenuItem.MenuItemType.SIZE);
        };
    }

}
