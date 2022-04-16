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
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.DefaultViewHolder;

public class PremadeComboAdapter extends RecyclerView.Adapter<DefaultViewHolder> implements Updatable<Premade> {

    private final List<Premade> premadeList = new ArrayList<>();

    private Checkable currentlySelected;
    private Premade premadeSelected;
    private Context context;
    private Notifiable n;

    public  PremadeComboAdapter(Context context, Notifiable n) {
        this.context = context;
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
        Premade p = premadeList.get(position);

        holder.setName(p.getName());
        Glide.with(context)
                .asBitmap()
                .load(p.getImageUrl())
                .into(holder.getThumbNail());
        holder.addCheckListener(checkCardHandler(holder, p));
    }

    private View.OnClickListener checkCardHandler(DefaultViewHolder holder, Premade p) {
        return view -> {
            List<Topping> toppingList = p.getToppings();

            if (currentlySelected != null) {
                currentlySelected.updateDataCheckedState();

                if (!premadeSelected.getId().equals(p.getId())) {
                    holder.updateDataCheckedState();
                    currentlySelected = holder;
                    premadeSelected = p;
                } else {
                    toppingList = new ArrayList<>();
                    currentlySelected = null;
                    premadeSelected = null;
                }
            } else {
                currentlySelected = holder;
                premadeSelected = p;
                currentlySelected.updateDataCheckedState();
            }

            n.notifyUpdatedData(toppingList, MenuItem.MenuItemType.TOPPING);
        };
    }

    @Override
    public int getItemCount() {
        return premadeList.size();
    }

    @Override
    public List<Premade> getDataList() {
        return premadeList;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

}
