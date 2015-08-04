package com.thingsle.sameer.thingsle;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sameer on 7/13/2015.
 */
public class Drawer_Item {

    int ivIcon;
    String tvItemName;

    public Drawer_Item(int ivIcon, String tvItemName) {
        this.ivIcon = ivIcon;
        this.tvItemName = tvItemName;
    }

    public int getIvIcon() {
        return ivIcon;
    }

    public String getTvItemName() {
        return tvItemName;
    }

    public void setIvIcon(int ivIcon) {
        this.ivIcon = ivIcon;
    }

    public void setTvItemName(String tvItemName) {
        this.tvItemName = tvItemName;
    }
}
