package com.example.plan;

public class CustomItemSpinner {
    private String spinnerItemName;
    private int spinerItemIcon;

    public CustomItemSpinner(String spinnerItemName, int spinerItemIcon) {
        this.spinnerItemName = spinnerItemName;
        this.spinerItemIcon = spinerItemIcon;
    }

    public String getSpinnerItemName() {
        return spinnerItemName;
    }

    public void setSpinnerItemName(String spinnerItemName) {
        this.spinnerItemName = spinnerItemName;
    }

    public int getSpinerItemIcon() {
        return spinerItemIcon;
    }

    public void setSpinerItemIcon(int spinerItemIcon) {
        this.spinerItemIcon = spinerItemIcon;
    }
}
