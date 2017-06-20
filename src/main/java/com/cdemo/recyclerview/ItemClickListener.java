package com.cdemo.recyclerview;

import android.view.View;

/**
 * Created by yangdi on 2017/6/20.
 */

public interface ItemClickListener {

    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
