package com.genimous.peopleoutsourcing.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.genimous.core.util.ToastUtil;
import com.genimous.core.widget.DialogMaker;
import com.genimous.peopleoutsourcing.R;
import com.genimous.peopleoutsourcing.adapter.TryGameViewHolder;
import com.genimous.peopleoutsourcing.bean.TryGameBean;
import com.genimous.peopleoutsourcing.contract.TryGameContract;
import com.genimous.peopleoutsourcing.presenter.TryGamePresenter;
import com.genimous.peopleoutsourcing.utils.MD5;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by wudi on 18/1/18.
 */

public class TryPlayActivity extends BaseMvpActivity<TryGamePresenter> implements TryGameContract.tryGameView,TryGameViewHolder.OnRecyclerViewItemClickListener {

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

        imageBack = (ImageView)findViewById(R.id.ImageView_baseActivity_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView_baseFragmentTitle = (TextView) findViewById(R.id.TextView_baseFragmentTitle);
        TextView_baseFragmentTitle.setText("试玩专区");

        //初始化RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_tryplayActivity);
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

    private void onRefresh(){
        showLoading("");
        this.mPresenter.getGameList();
    }

    private void initData(){

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

        DialogMaker.showProgressDialog(this,loadingStr,false);
    }

    @Override
    public void hideLoading() {
        DialogMaker.dismissProgressDialog();
    }


    //list按钮点击
    @Override
    public void onItemClick(int position) {
        Log.i("aaa","onItemClickonItemClick");
        download(tryGameList.get(position).getDownUrl(), tryGameList.get(position).getPackagename());
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

    private void download(String url, String packageName) {
//        tv.setEnabled(false);
        DownloadTask task = new DownloadTask();
        String fileName = MD5.MD5(url);

        task.setFileName(fileName);
        Log.i("aaa", "packageName= "+packageName);
        task.setPackageName(packageName);
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
                        Toast.makeText(getApplicationContext(),"onPause",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancel(DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"onCancel",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCompleted(final DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        tv.setText("finished");
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

}
