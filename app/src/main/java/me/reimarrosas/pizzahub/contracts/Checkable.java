package me.reimarrosas.pizzahub.contracts;

import android.view.View;

public interface Checkable {

    void updateDataCheckedState();

    void addCheckListener(View.OnClickListener ocl);

}
