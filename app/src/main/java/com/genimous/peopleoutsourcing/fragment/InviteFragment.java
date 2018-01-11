package com.genimous.peopleoutsourcing.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.genimous.peopleoutsourcing.activity.R;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.widget.LoadStatusView;

/**
 * Created by wudi on 18/1/4.
 */

public class InviteFragment extends BaseFragment {

    ViewPager viewPagerTop;
    ViewPager ViewPagerBottom;

    ImageView[] imageViewsTopArray;
    ImageView[] imageViewsBottomArray;

    int[] imageIdTopArray = {R.drawable.mo_guide_image_one, R.drawable.mo_guide_image_two, R.drawable.mo_guide_image_three};
    int[] imageIdBottomArray = {R.drawable.share1,R.drawable.share2,R.drawable.share3,R.drawable.share4,R.drawable.share5};

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        viewPagerTop = (ViewPager) view.findViewById(R.id.Viewpager_invokeFragment_top);
        ViewPagerBottom = (ViewPager)view.findViewById(R.id.Viewpager_invokeFragment_bottom);

        initTopImage();



        viewPagerTop.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageIdTopArray.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(imageViewsTopArray[position % imageViewsTopArray.length]);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager)container).addView(imageViewsTopArray[position % imageViewsTopArray.length],0);
                return imageViewsTopArray[position % imageViewsTopArray.length];
            }
        });


    }

    private void initTopImage(){
        imageViewsTopArray = new ImageView[imageIdTopArray.length];
        for (int i = 0; i < imageViewsTopArray.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageViewsTopArray[i] = imageView;
            imageView.setBackgroundResource(imageIdTopArray[i]);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invoke;
    }

    public static InviteFragment newInstance(Bundle bundle) {
        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }



}
