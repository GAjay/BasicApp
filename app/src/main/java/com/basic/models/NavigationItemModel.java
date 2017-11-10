package com.basic.models;

/**
 * Created by vikas on 13/9/16.
 */
public class NavigationItemModel {
    private int itemImage;
    private int itemImageSelected;
    private int itemName;
    private int colorName;
    private boolean isChecked;

    private String tag;

    public NavigationItemModel(int itemImage, int itemName,int imageSelected) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemImageSelected = imageSelected;
    }

    public NavigationItemModel(int itemImage, int itemName, String tag) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.tag = tag;
    }

    public int getItemImage() {
        return itemImage;
    }

    public int getColorName() {
        return colorName;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public int getItemName() {
        return itemName;
    }

    public void setItemName(int itemName) {
        this.itemName = itemName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public NavigationItemModel(int itemImageSelected) {
        this.itemImageSelected = itemImageSelected;
    }

    public int getItemImageSelected() {
        return itemImageSelected;
    }

    @Override
    public String toString() {
        return "NavigationItemModel{" +
                "itemImage=" + itemImage +
                ", itemName=" + itemName +
                ", tag='" + tag + '\'' +
                '}';
    }
}
