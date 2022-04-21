package me.reimarrosas.pizzahub.recycleradapters.adapterdata;

import me.reimarrosas.pizzahub.contracts.Reproducible;
import me.reimarrosas.pizzahub.models.Topping;

public class CustomizePizzaData {

    private DataType dataType;
    private Reproducible<Topping> topping;
    private String sectionName;

    public CustomizePizzaData(DataType dataType, Reproducible<Topping> topping, String sectionName) {
        this.dataType = dataType;
        this.topping = topping;
        this.sectionName = sectionName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Reproducible<Topping> getTopping() {
        return topping;
    }

    public String getSectionName() {
        return sectionName;
    }

    @Override
    public String toString() {
        return "CustomizePizzaData{" +
                "dataType=" + dataType +
                ", topping=" + topping +
                ", sectionName='" + sectionName + '\'' +
                '}';
    }

    public enum DataType {
        TOPPING, HEADER
    }

}
