package me.reimarrosas.pizzahub.recycleradapters.adminadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.AdminManageable;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.contracts.Updatable;
import me.reimarrosas.pizzahub.fragments.admin.AdminPremadeFragment;
import me.reimarrosas.pizzahub.fragments.admin.AdminPremadeFragmentDirections;
import me.reimarrosas.pizzahub.helper.CollectionConverters;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.viewholders.AdminPremadeViewHolder;

public class AdminPremadeAdapter extends RecyclerView.Adapter<AdminPremadeViewHolder> implements Updatable<Premade>, AdminManageable {

    private final List<Premade> premades = new ArrayList<>();
    private final Context context;
    private final Service<Premade> service;
    private final Notifiable n;

    public AdminPremadeAdapter(Context context, Service<Premade> service, Notifiable n) {
        this.context = context;
        this.service = service;
        this.n = n;
    }

    @NonNull
    @Override
    public AdminPremadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.admin_premade_row, parent, false);
        return new AdminPremadeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPremadeViewHolder holder, int position) {
        Premade p = premades.get(position);

        String price = String.format("$%.2f", calculatePrice(p));
        holder.setNameAndPrice(p.getName(), price);
        holder.setOnViewClick(view -> {
            NavDirections action = AdminPremadeFragmentDirections
                    .actionAdminPremadeFragmentToSavePremadeFragment(p);
            Navigation.findNavController(view).navigate(action);
        });
        Glide.with(context)
                .asBitmap()
                .load(p.getImageUrl())
                .into(holder.getPremadeImage());
    }

    @Override
    public int getItemCount() {
        return premades.size();
    }

    @Override
    public void onViewSwiped(int position) {
        Premade p = premades.get(position);
        premades.remove(position);
        notifyItemRemoved(position);
        service.deleteData(p.getId());
    }

    @Override
    public List<Premade> getDataList() {
        return premades;
    }

    @Override
    public void notifyDataListChange() {
        notifyDataSetChanged();
    }

    private double calculatePrice(Premade p) {
        double total = 0.0f;

        for (Topping t : p.getToppings()) {
            total += t.getPrice();
        }

        BigDecimal oneHundred = BigDecimal.valueOf(100);
        return BigDecimal.valueOf(total)
                .multiply(oneHundred)
                .setScale(0, RoundingMode.HALF_UP)
                .divide(oneHundred)
                .doubleValue();
    }

}
