package me.reimarrosas.pizzahub.recycleradapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.reimarrosas.pizzahub.R;

public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView sectionName;

    public SectionHeaderViewHolder(@NonNull View itemView) {
        super(itemView);

        sectionName = itemView.findViewById(R.id.textViewSectionHeader);
    }

    public void setSectionName(String sectionName) {
        this.sectionName.setText(sectionName);
    }

}
