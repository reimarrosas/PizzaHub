package me.reimarrosas.pizzahub.recycleradapters.adapterdata;

import me.reimarrosas.pizzahub.models.Topping;

public class CustomizePizzaData {

    private DataType dataType;
    private Topping topping;
    private String sectionName;

    public CustomizePizzaData(DataType dataType, Topping topping, String sectionName) {
        this.dataType = dataType;
        this.topping = topping;
        this.sectionName = sectionName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Topping getTopping() {
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
