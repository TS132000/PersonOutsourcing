package com.genimous.peopleoutsourcing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.genimous.core.util.TextUtil;
import com.genimous.peopleoutsourcing.activity.R;

/**
 * Created by wudi on 18/1/8.
 */

public class LoadStatusView extends LinearLayout{

    public enum LoadStatus {
        LOADING, COMPLETE, NO_NET, NO_DATA
    }

    private LoadStatus mCurrentStatus;

    private String mLoadingContent;
    private int mNoDataIcon;

    private String mNoNetContent = getResources().getString(R.string.noNetText);
    private View rootView;
    private String mNoDataContent;
    private ViewStub mStubLoading, mStubNoData, mStubNoNet;
    private boolean mIsInflateStubLoading, mIsInflateStubNoData, mIsInflateStubNoNet;
    private int mNoNetIcon = R.drawable.sds_pic_reload;


    public LoadStatusView(Context context) {
        this(context, null);
    }

    public LoadStatusView(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStatusView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_load_status,this,true);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        initStubView();
        initNoNetView();
    }

    private void initStubView() {
        mStubLoading = rootView.findViewById(R.id.ViewStub_LoadStatusView_loading);
        mStubNoData = rootView.findViewById(R.id.ViewStub_LoadStatusView_no_data);
        mStubNoNet = rootView.findViewById(R.id.ViewStub_LoadStatusView_no_net);
        mStubLoading.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                mIsInflateStubLoading = true;
            }
        });
        mStubNoData.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                mIsInflateStubNoData = true;
            }
        });
        mStubNoNet.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                mIsInflateStubNoNet = true;
            }
        });
    }


    private void initStubLoadingView() {
        if (mStubNoData != null) {
            mStubNoData.setVisibility(View.GONE);
        }
        if (mStubNoNet != null) {
            mStubNoNet.setVisibility(View.GONE);
        }
        if (!mIsInflateStubLoading) {
            mStubLoading.inflate();
            ProgressBar progressBarLoading = findViewById(R.id.ProgressBar_LoadStatusView_progress_loading);
            TextView textLoading = findViewById(R.id.TextView_LoadStatusView_load_content);
            setTextContent(textLoading, mLoadingContent);
        } else {
            mStubLoading.setVisibility(View.VISIBLE);
        }
    }

    private void initNoNetView() {
        if (mStubLoading != null) {
            mStubLoading.setVisibility(View.GONE);
        }
        if (mStubNoData != null) {
            mStubNoData.setVisibility(View.GONE);
        }
        if (!mIsInflateStubNoNet) {
            mStubNoNet.inflate();
            ImageView imageNoNet = findViewById(R.id.ImageView_LoadStatusView_no_net_icon);
            TextView textNoNet = findViewById(R.id.TextView_LoadStatusView_no_net_content);
            setImageResource(imageNoNet, mNoNetIcon);
            setTextContent(textNoNet, mNoNetContent);
        } else {
            mStubNoNet.setVisibility(View.VISIBLE);
        }
    }

    private void initNoDataView() {
        if (mStubLoading != null) {
            mStubLoading.setVisibility(View.GONE);
        }
        if (mStubNoNet != null) {
            mStubNoNet.setVisibility(View.GONE);
        }
        if (!mIsInflateStubNoData) {
            mStubNoData.inflate();
            ImageView imageNoData = findViewById(R.id.ImageView_LoadStatusView_no_data_icon);
            TextView textNoData = findViewById(R.id.TextView_LoadStatusView_no_data_content);
            setImageResource(imageNoData, mNoDataIcon);
            setTextContent(textNoData, mNoDataContent);
        } else {
            mStubNoData.setVisibility(View.VISIBLE);
        }
    }

    public void setNoDataIcon(int noDataIcon) {
        mNoDataIcon = noDataIcon;
    }

    public void setLoadingContent(String loadingContent) {
        mLoadingContent = loadingContent;
    }

    public void setNoNetContent(String noNetContent) {
        mNoNetContent = noNetContent;
    }

    private void setImageResource(ImageView imageView, int id) {
        if (id != 0) {
            imageView.setImageResource(id);
        }
    }
    private void setTextContent(TextView textView, String content) {
        if (!TextUtil.isEmptyString(content)) {
            textView.setText(content);
        }
    }


    public void setViewStatus(LoadStatus loadStatus) {
        mCurrentStatus = loadStatus;
        switch (loadStatus) {
            case LOADING:
                rootView.setVisibility(View.VISIBLE);
                initStubLoadingView();
                break;
            case COMPLETE:
                rootView.setVisibility(View.GONE);
                break;
            case NO_NET:
                rootView.setVisibility(View.VISIBLE);
                initNoNetView();
                break;
            case NO_DATA:
                rootView.setVisibility(View.VISIBLE);
                initNoDataView();
                break;
            default:
                rootView.setVisibility(View.GONE);
                break;
        }
    }

    public interface ReloadDataListener {
        void reloadData();
    }

}
