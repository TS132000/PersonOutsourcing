package com.genimous.peopleoutsourcing.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.genimous.core.base.BaseMvpActivity;
import com.genimous.core.download.DownloadManager;
import com.genimous.core.download.DownloadStatus;
import com.genimous.core.download.DownloadTask;
import com.genimous.core.download.DownloadTaskListener;
import com.genimous.core.util.DeviceUtil;
import com.genimous.core.util.GsonUtil;
import com.genimous.core.util.ToastUtil;
import com.genimous.core.widget.DialogMaker;
import com.genimous.net.NetAPI;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.adapter.TryGameViewHolder;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.contract.TryGameContract;
import com.genimous.peopleoutsourcing.presenter.LoginPresenter;
import com.genimous.peopleoutsourcing.presenter.TryGamePresenter;
import com.genimous.peopleoutsourcing.utils.AppUser;
import com.genimous.peopleoutsourcing.utils.MD5;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;


/**
 * Created by wudi on 18/1/18.
 */

public class TryPlayActivity extends BaseMvpActivity<TryGamePresenter> implements TryGameContract.tryGameView, TryGameViewHolder.OnRecyclerViewItemClickListener {

    TryGameViewHolder tryGameViewHolder;


    RecyclerView recyclerView;
    ImageView imageBack;
    TextView TextView_baseFragmentTitle;

    private DownloadManager downloadManager;
    private Handler handler;
    NotificationManager mNotificationManager;

    private String downLoadTaskID;
    private List<String> taskIds = new ArrayList<>();

    HashMap<Integer, NotificationCompat.Builder> notificationHashMap = new HashMap<>();

    ArrayList<TryGameBean> tryGameList = new ArrayList<TryGameBean>(8);

    @Override
    protected TryGamePresenter onLoadPresenter() {
        return new TryGamePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_tryplay;
    }

    @Override
    protected void onInitialization(Bundle bundle) {

        imageBack = (ImageView) findViewById(R.id.ImageView_baseActivity_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView_baseFragmentTitle = (TextView) findViewById(R.id.TextView_baseFragmentTitle);
        TextView_baseFragmentTitle.setText("试玩专区");

        //初始化RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_tryplayActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //初始化下载模块
        handler = new Handler();
        downloadManager = DownloadManager.getInstance(getApplicationContext());
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRefresh();

    }

    private void onRefresh() {
        showLoading("");
        this.mPresenter.getGameList();
    }

    private void initData() {

        //初始化适配器
        tryGameViewHolder = new TryGameViewHolder(tryGameList, TryPlayActivity.this, TryPlayActivity.this);
        recyclerView.setAdapter(tryGameViewHolder);
//        PermissionsUtil.isPermissionOpen("android.permission.CAMERA");
//        Log.i("aaa",    "PermissionsUtil.isPermissionOpen(\"android.permission.CAMERA\"); === "+PermissionsUtil.isPermissionOpen("android.permission.CAMERA"));
    }

    @Override
    public void getGameSuccess(ArrayList<TryGameBean> gameList) {
        hideLoading();
        tryGameList = gameList;
        initData();
    }

    @Override
    public void getGameFailed(String msg) {
        hideLoading();
        ToastUtil.show(msg);
    }

    @Override
    public void showLoading(String loadingStr) {

        DialogMaker.showProgressDialog(this, loadingStr, false);
    }

    @Override
    public void hideLoading() {
        DialogMaker.dismissProgressDialog();
    }


    //list按钮点击
    @Override
    public void onItemClick(int position) {
        Log.i("aaa", "onItemClickonItemClick");

        Log.i("aaa", "appid ===== " + tryGameList.get(position).getApp_id());
        Log.i("aaa", "getName ===== " + tryGameList.get(position).getName());

        if(downLoadRecord(tryGameList.get(position).getApp_id(), tryGameList.get(position).getName(), NetAPI.RECORD_STATUS_STARTED)){
            download(tryGameList.get(position).getDownUrl(), tryGameList.get(position).getPackagename(), tryGameList.get(position).getApp_id(), tryGameList.get(position).getName());
        }


    }


    private void finishedDownload(int notifyId, String fileName) {
        notificationHashMap.get(notifyId).setContentText("Finished.");
        notificationHashMap.get(notifyId).setSmallIcon(android.R.drawable.stat_sys_download_done);

        mNotificationManager.notify(notifyId, notificationHashMap.get(notifyId).build());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(getExternalCacheDir().getPath() + "/" + fileName)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    private void updateNotification(String fileTitle, int progress, long totalBytes, int notifyId) {


        notificationHashMap.get(notifyId).setContentText(getBytesDownloaded(progress, totalBytes));
        notificationHashMap.get(notifyId).setContentTitle(progress + "% | " + fileTitle);
        notificationHashMap.get(notifyId).setProgress(100, progress, false);
        mNotificationManager.notify(notifyId, notificationHashMap.get(notifyId).build());
    }

    private void setNotification(String title, String content, int notifyId) {

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_sys_download);

        mNotificationManager.notify(
                notifyId,
                mNotifyBuilder.build());

        notificationHashMap.put(notifyId, mNotifyBuilder);
    }

    int counter = 1001;

    private void download(String url, String packageName, String appId, String appName) {
//        tv.setEnabled(false);
        DownloadTask task = new DownloadTask();
        String fileName = MD5.MD5(url);

        task.setFileName(fileName);
        Log.i("aaa", "packageName= " + packageName);
        task.setPackageName(packageName);
        task.setAppId(appId);
        task.setAppName(appName);
        taskIds.add(fileName);
        task.setId(fileName);
        task.setSaveDirPath(getExternalCacheDir().getPath() + "/");

        task.setUrl(url);
        downloadManager.addDownloadTask(task, new DownloadTaskListener() {
            @Override
            public void onPrepare(final DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

//                        if(tv.getId()==R.id.tv4){
//                            btControl.setVisibility(View.VISIBLE);
//                            btCancel.setVisibility(View.VISIBLE);
//                        }
//                        tv.setText("preparing ");

                        setNotification(downloadTask.getFileName(),
                                "Will start to download counter " + counter, downloadTask.getId().hashCode());

                    }
                });
            }

            @Override
            public void onStart(final DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        tv.setText("start");
//                        downLoadRecord(downloadTask.getAppId(), downloadTask.getAppName(), NetAPI.RECORD_STATUS_STARTED);
                    }
                });

            }

            @Override
            public void onDownloading(final DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        tv.setText((int) downloadTask.getPercent() + "%       ");

                        updateNotification(downloadTask.getFileName(),
                                (int) downloadTask.getPercent(), downloadTask.getToolSize(), downloadTask.getId().hashCode());

                    }
                });
            }

            @Override
            public void onPause(DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancel(DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCompleted(final DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        tv.setText("finished");
                        downLoadRecord(downloadTask.getAppId(), downloadTask.getAppName(), NetAPI.RECORD_STATUS_DOWN);
                        finishedDownload(downloadTask.getId().hashCode(), downloadTask.getFileName());

                    }
                });

            }

            @Override
            public void onError(DownloadTask downloadTask, int errorCode) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        tv.setText("error");

                    }
                });

            }
        });

    }

    private String getBytesDownloaded(int progress, long totalBytes) {
        //Greater than 1 MB
        long bytesCompleted = (progress * totalBytes) / 100;
        if (totalBytes >= 1000000) {
            return ("" + (String.format(Locale.ENGLISH, "%.1f",
                    (float) bytesCompleted / 1000000)) + "/" + (String.format(Locale.ENGLISH,
                    "%.1f", (float) totalBytes / 1000000)) + " MB");
        }
        if (totalBytes >= 1000) {
            return ("" + (String.format(Locale.ENGLISH, "%.1f",
                    (float) bytesCompleted / 1000)) + "/" + (String.format(Locale.ENGLISH,
                    "%.1f", (float) totalBytes / 1000)) + " Kb");

        } else {
            return ("" + bytesCompleted + "/" + totalBytes);
        }
    }

    public boolean downLoadRecord(final String appId, String appName, String status) {
        //联网

        Log.i("aaa","checkSelfPermissioncheckSelfPermissioncheckSelfPermission");
        //联网
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("aaa","checkSelfPermission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, DeviceUtil.REQUEST_READ_PHONE_STATE);
            return false;
        }
        OkHttpUtils
                .post()
                .url(NetAPI.GET_RECORD_LIST)
                .addParams("user_id", AppUser.getUserInfoEntity().getId())
                .addParams("app_id", appId)
                .addParams("device_id", DeviceUtil.getTelephonyManager(TryPlayActivity.this).getDeviceId())
                .addParams("name", appName)
                .addParams("status", status)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
//                            Log.i("aaa", ""+ (e.printStackTrace()));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("aaa", "response ＝＝＝＝＝ "+ response);
                        if (!TextUtils.isEmpty(response)) {


                        }
                    }


                });
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case DeviceUtil.REQUEST_READ_PHONE_STATE:
//                downLoadRecord();
                Log.i("aaa","onRequestPermissionsResult === "+grantResults[0]);
                break;
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
