package com.genimous.peopleoutsourcing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;


import com.genimous.core.util.TextUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.peopleoutsourcing.activity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


public class SharePopupWindow extends PopupWindow {

    public enum EventChannel {
        UserCenter, LoanDetail
    }

    private View mMenuView;
    private Context mContext;
    private GridView mGvShare;
    private TypedArray mShareImage;
    private String[] mShareName;
    private String[] mSharePlatform;
    private String mstrTitle, mstrUrl, mstrRemark;
    private ShareDismiss mShareDismiss;
    private EventChannel mEventChannel;


    public SharePopupWindow(Context context, ShareDismiss shareDismiss) {
        super(context);
        mContext = context;
        mShareDismiss = shareDismiss;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_window_share, null);
        mShareName = mContext.getResources().getStringArray(R.array.share_list_name);
        mShareImage = mContext.getResources().obtainTypedArray(R.array.share_list_image);
        mSharePlatform = new String[]{WechatMoments.NAME, Wechat.NAME, SinaWeibo.NAME, QQ.NAME, QZone.NAME, ShortMessage.NAME};
        findHeadPortraitImageViews();
    }

    private void findHeadPortraitImageViews() {
        mGvShare = mMenuView.findViewById(R.id.GridView_SharePopupWindow_share);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupWindowAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return true;
            }
        });
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < mSharePlatform.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("imageItem", mShareImage.getResourceId(i, R.drawable.ic_share_wechat));
            item.put("textItem", mShareName[i]);
            items.add(item);
        }
        mShareImage.recycle();
        SimpleAdapter adapter = new SimpleAdapter(mContext,
                items,
                R.layout.item_share,
                new String[]{"imageItem", "textItem"},
                new int[]{R.id.image_item, R.id.text_item});
        mGvShare.setAdapter(adapter);
        mGvShare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                shareToPlatformByNewsDetail(mSharePlatform[i]);
            }
        });
    }

    public void setEventChannel(EventChannel channel) {
        mEventChannel = channel;
    }

    public void setShareTitle(String title) {
        mstrTitle = title;
    }

    public void setShareRemark(String remark) {
        mstrRemark = remark;
    }

    public void setShareUrl(String url) {
        mstrUrl = url;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mShareDismiss != null) {
            mShareDismiss.shareDismiss();
        }
    }

    public interface ShareDismiss {
        void shareDismiss();
    }

    public void shareToPlatformByNewsDetail(final String argPlatform) {
        PlatformActionListener shareListener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> stringObjectHashMap) {
                dismiss();
            }

            @Override
            public void onError(Platform platform, final int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        };

        Platform.ShareParams shareParams = new Platform.ShareParams();
        if (null != shareParams) {
            if (argPlatform.equals(SinaWeibo.NAME)) {
                shareParams.setImageUrl("http://api.360fangxindai.com:9191/images/fangxindai_icon.png");
            } else {
                shareParams.setImageData(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
            }
            if (argPlatform.equals(SinaWeibo.NAME) || argPlatform.equals(ShortMessage.NAME) || argPlatform.equals(QZone.NAME)) {
                shareParams.setText(mstrTitle + "" + mstrUrl);
            } else if (argPlatform.equals(Wechat.NAME) || argPlatform.equals(WechatMoments.NAME)) {
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setTitle(mstrTitle);
                shareParams.setUrl(mstrUrl);
            } else if (argPlatform.equals(QQ.NAME)) {
                shareParams.setTitle(mstrTitle);
                shareParams.setTitleUrl(mstrUrl);
            }
            if (argPlatform.equals(Wechat.NAME)) {
                if (mEventChannel == EventChannel.UserCenter) {
//                    TCAgent.onEvent(mContext, EventConstant.USERCENTER_SHARE_WECHAT);
                } else if (mEventChannel == EventChannel.LoanDetail) {
//                    TCAgent.onEvent(mContext, EventConstant.LOANDETAIL_SHARE_WECHAT);
                }
                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                if (!platform.isClientValid()) {
                    ToastUtil.show(mContext.getResources().getString(R.string.no_wechat));
                    return;
                }
                platform.setPlatformActionListener(shareListener);
                if (!TextUtil.isEmptyString(mstrRemark)) {
                    shareParams.setText(mstrRemark);
                }
                platform.share(shareParams);
            } else if (argPlatform.equals(WechatMoments.NAME)) {
                if (mEventChannel == EventChannel.UserCenter) {
//                    TCAgent.onEvent(mContext, EventConstant.USERCENTER_SHARE_PENGYOUQUAN);
                } else if (mEventChannel == EventChannel.LoanDetail) {
//                    TCAgent.onEvent(mContext, EventConstant.LOANDETAIL_SHARE_PENGYOUQUAN);
                }
                Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
                if (!platform.isClientValid()) {
                    ToastUtil.show( mContext.getResources().getString(R.string.no_wechat));
                    return;
                }
                platform.setPlatformActionListener(shareListener);
                platform.share(shareParams);
            } else if (argPlatform.equals(SinaWeibo.NAME)) {
                if (mEventChannel == EventChannel.UserCenter) {
//                    TCAgent.onEvent(mContext, EventConstant.USERCENTER_SHARE_SINA);
                } else if (mEventChannel == EventChannel.LoanDetail) {
//                    TCAgent.onEvent(mContext, EventConstant.LOANDETAIL_SHARE_SINA);
                }
                Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (!platform.isClientValid()) {
                    ToastUtil.show( mContext.getResources().getString(R.string.no_sina_weibo));
                    return;
                }
                platform.SSOSetting(false);
                platform.setPlatformActionListener(shareListener);
                platform.share(shareParams);
            } else if (argPlatform.equals(QQ.NAME)) {
                if (mEventChannel == EventChannel.UserCenter) {
//                    TCAgent.onEvent(mContext, EventConstant.USERCENTER_SHARE_QQ);
                } else if (mEventChannel == EventChannel.LoanDetail) {
//                    TCAgent.onEvent(mContext, EventConstant.LOANDETAIL_SHARE_QQ);
                }
                Platform platform = ShareSDK.getPlatform(QQ.NAME);
                platform.setPlatformActionListener(shareListener);
                platform.SSOSetting(false);
                platform.share(shareParams);
            } else if (argPlatform.equals(QZone.NAME)) {
                if (mEventChannel == EventChannel.UserCenter) {
//                    TCAgent.onEvent(mContext, EventConstant.USERCENTER_SHARE_QZONE);
                } else if (mEventChannel == EventChannel.LoanDetail) {
//                    TCAgent.onEvent(mContext, EventConstant.LOANDETAIL_SHARE_QZONE);
                }
                Platform platform = ShareSDK.getPlatform(QZone.NAME);
                platform.setPlatformActionListener(shareListener);
                platform.SSOSetting(false);
                platform.share(shareParams);
            } else if (argPlatform.equals(ShortMessage.NAME)) {
                if (mEventChannel == EventChannel.UserCenter) {
//                    TCAgent.onEvent(mContext, EventConstant.USERCENTER_SHARE_MESSAGE);
                } else if (mEventChannel == EventChannel.LoanDetail) {
//                    TCAgent.onEvent(mContext, EventConstant.LOANDETAIL_SHARE_MESSAGE);
                }
                Platform platform = ShareSDK.getPlatform(ShortMessage.NAME);
                platform.setPlatformActionListener(shareListener);
//                shareParams.setAddress();
                platform.share(shareParams);
            }
        }
    }
}
