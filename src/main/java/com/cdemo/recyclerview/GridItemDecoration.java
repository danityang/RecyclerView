package com.cdemo.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by yangdi on 2017/6/20.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    // 分割线样式
    private static final int[] attrs = new int[]{android.R.attr.listDivider};
    private Drawable mDrawable;

    public GridItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDrawable = typedArray.getDrawable(0);
        typedArray.recycle();
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 横线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParam = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - layoutParam.leftMargin;
            int right = child.getRight() + layoutParam.rightMargin + mDrawable.getIntrinsicWidth();
            int top = child.getBottom() + layoutParam.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    /**
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParam = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 子view的右边位置即
            int left = child.getRight() - layoutParam.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            int top = child.getTop() - layoutParam.topMargin;
            int bottom = child.getBottom() + layoutParam.bottomMargin;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }

    }


    /**
     * 判断是否为最后一列
     * @param parent 父类
     * @param pos item位置position
     * @param spanCount 列数
     * @param childCount 子view个数
     * @return
     */
    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是最后一列，则不需要绘制右边
            if ((pos + 1) % spanCount == 0) {
                return true;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否为最后一行
     * @param parent 父类
     * @param pos item位置position
     * @param spanCount 行数
     * @param childCount 子view个数
     * @return true,false
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            }
        } else {
            if ((pos + 1) % spanCount == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置分割线偏移位置
     * @param outRect 分割线
     * @param itemPosition
     * @param parent
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }
    }

    /**
     * 获取行数/列数
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layouyManager = parent.getLayoutManager();
        if (layouyManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layouyManager).getSpanCount();
        } else if (layouyManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layouyManager).getSpanCount();
        }
        return spanCount;
    }
}
