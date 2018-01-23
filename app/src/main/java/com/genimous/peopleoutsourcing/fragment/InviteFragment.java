package com.genimous.peopleoutsourcing.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.genimous.peopleoutsourcing.activity.R;
import com.genimous.peopleoutsourcing.base.BaseFragment;
import com.genimous.peopleoutsourcing.view.SharePopupWindow;
import com.genimous.peopleoutsourcing.widget.LoadStatusView;

/**
 * Created by wudi on 18/1/4.
 */

public class InviteFragment extends BaseFragment implements SharePopupWindow.ShareDismiss{

    ViewPager viewPagerTop;
    ViewPager ViewPagerBottom;

    private Context mContext;
    ImageView[] imageViewsTopArray;
    ImageView[] imageViewsBottomArray;

    ImageView imageView_invokeFragment_share;
    private SharePopupWindow mSharePopupWindow;
    RelativeLayout rootView;


    View mShareBg;
    int[] imageIdTopArray = {R.drawable.mo_guide_image_one, R.drawable.mo_guide_image_two, R.drawable.mo_guide_image_three};
    int[] imageIdBottomArray = {R.drawable.share1,R.drawable.share2,R.drawable.share3,R.drawable.share4,R.drawable.share5};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        viewPagerTop = (ViewPager) view.findViewById(R.id.Viewpager_invokeFragment_top);
        ViewPagerBottom = (ViewPager)view.findViewById(R.id.Viewpager_invokeFragment_bottom);
        imageView_invokeFragment_share = (ImageView)view.findViewById(R.id.imageView_invokeFragment_share);
        mShareBg = (View)view.findViewById(R.id.View_invokeFragment_share);
        rootView = (RelativeLayout)view.findViewById(R.id.RelativeLayout_InvokeFragemnt_root);
        imageView_invokeFragment_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSharePopupWindow == null) {
                    mSharePopupWindow = new SharePopupWindow(mContext, InviteFragment.this);
                }
                mShareBg.setVisibility(View.VISIBLE);
                mSharePopupWindow.setEventChannel(SharePopupWindow.EventChannel.UserCenter);
//                mSharePopupWindow.setShareTitle(mContext.getResources().getString(R.string.share_invite_friend));
                mSharePopupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (mSharePopupWindow != null && mSharePopupWindow.isShowing()) {
                mShareBg.setVisibility(View.GONE);
                mSharePopupWindow.dismiss();
            }
        }
    }

    @Override
    public void shareDismiss() {
        mShareBg.setVisibility(View.GONE);
    }
}
