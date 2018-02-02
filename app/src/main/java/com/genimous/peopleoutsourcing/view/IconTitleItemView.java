package com.genimous.peopleoutsourcing.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;


import com.genimous.peopleoutsourcing.R;

import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;


/**
 * 自定义一个只有图标的Item
 */
public class IconTitleItemView extends BaseTabItem {

    private ImageView mIcon;
    private TextView mTitle;

    private int mDefaultDrawable;
    private int mCheckedDrawable;

    public IconTitleItemView(Context context) {
        this(context, null);
    }

    public IconTitleItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTitleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_title_icon, this, true);

        mIcon = findViewById(R.id.ImageView_IconTitleItemView_icon);
        mTitle = findViewById(R.id.TextView_IconTitleItemView_title);
    }

    public void initialize(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes, String title) {
        mDefaultDrawable = drawableRes;
        mCheckedDrawable = checkedDrawableRes;
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        mIcon.setImageResource(checked ? mCheckedDrawable : mDefaultDrawable);
        mTitle.setTextColor(checked ? getResources().getColor(R.color.orange) : getResources().getColor(R.color.mine_load_fail_color));
    }

    @Override
    public void setMessageNumber(int number) {
        //不需要就不用管
    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        //不需要就不用管
    }

    @Override
    public String getTitle() {
        return "no title";
    }
}
