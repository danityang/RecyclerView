package com.cdemo.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yangdi on 2017/6/2.
 */

public class LinearItemDecoration extends RecyclerView.ItemDecoration {


    private static final int[] ATTRS = new int[]{
            // 分割线的样式（颜色，渐变等）
            android.R.attr.listDivider
    };

    private Drawable mDivider;
    private int mOrientation;

    public LinearItemDecoration(Context context, int mOrientation) {
        this.mOrientation = mOrientation;
        // 获取属性
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(mOrientation);
    }

    private void setOrientation(int orientation) {
        if(orientation != LinearLayoutManager.HORIZONTAL&&
                orientation != LinearLayoutManager.VERTICAL ){
            throw new IllegalArgumentException( "invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if(mOrientation == LinearLayoutManager.HORIZONTAL){
            drawHorizontal(c, parent);
        }else{
            drawVertical(c, parent);
        }
    }

    /**
     * 画竖着的横向分割线
     * @param c Canvas
     * @param parent 整个父容器
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        // 分割线的左起始位置
        int left = parent.getPaddingLeft();
        // 分割线的横向长度（宽度）
        int right = parent.getWidth() - parent.getPaddingRight();
       int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = parent.getChildAt(i);
            // layoutparams
            RecyclerView.LayoutParams rl = (RecyclerView.LayoutParams) v.getLayoutParams();
            // 分各线的顶部位置
            int top = rl.bottomMargin + v.getBottom();
            // 分割线的底部位置,这里加宽了分割线的高度，在getItemOffsets方法中要想赢的增加偏移位置
            int bottom = top + mDivider.getIntrinsicHeight()+20;
            // 分割线的形状，setBunods默认创建一个矩形
            mDivider.setBounds(left, top, right, bottom);
            // draw
            mDivider.draw(c);
        }

    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams rl = (RecyclerView.LayoutParams) v.getLayoutParams();
            int left = v.getRight() + rl.rightMargin;
            int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    /**
     * 由于Divider也有长宽高，每一个Item需要向下或者向右偏移
     * @param outRect
     * @param itemPosition
     * @param parent
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if(mOrientation == LinearLayoutManager.HORIZONTAL){
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }else{
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight()+20);
        }
    }
}
