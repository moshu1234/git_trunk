package com.example.andrewliu.fatbaby.DataBase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.GameAppOperation;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by liut1 on 6/22/16.
 */
public class QQShareKits {

    private Context context;
    public void QQShareKits(Context context){
        this.context = context;
    }
    public void shareToQzone (android.app.Activity activity,Tencent mTencent) {
        final Bundle params = new Bundle();
        Log.e("===shareToQzone","ca");
//分享类型
//        params.putString(QzoneShare.SHARE_TO_QQ_KEY_TYPE,SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,"http://www.aaa.com");
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
        mTencent.shareToQzone(activity, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e("===onComplete",""+o.toString());
            }

            @Override
            public void onError(UiError uiError) {

                Log.e("===onError",""+uiError.toString());
            }

            @Override
            public void onCancel() {

                Log.e("===onCancel","cancel");
            }
        });
    }
    public void share(Tencent mTencent)
    {
//        Bundle bundle = new Bundle();
//        //这条分享消息被好友点击后的跳转URL。
//        bundle.putString(Constants.PARAM_TARGET_URL, "http://connect.qq.com/");
//        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
//        bundle.putString(Constants.PARAM_TITLE, "我在测试");
//        //分享的图片URL
//        bundle.putString(Constants.PARAM_IMAGE_URL, "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
//        //分享的消息摘要，最长50个字
//        bundle.putString(Constants.PARAM_SUMMARY, "测试");
//        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//        bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
//        //标识该消息的来源应用，值为应用名称+AppId。
//        bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);
//
//        mTencent.shareToQQ(this, bundle , listener);

    }
    public void onClickShare(android.app.Activity activity,Tencent mTencent) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(activity, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e("===onComplete",""+o.toString());
            }

            @Override
            public void onError(UiError uiError) {

                Log.e("===onError",""+uiError.toString());
            }

            @Override
            public void onCancel() {

                Log.e("===onCancel","cancel");
            }
        });
    }
    public void onClickShareToQQGroup(android.app.Activity activity,Tencent mTencent){
        final Bundle params = new Bundle();
        params.putString(GameAppOperation.QQFAV_DATALINE_APPNAME, "FatBaby");
        params.putString(GameAppOperation.QQFAV_DATALINE_TITLE, "测试title");
        params.putString(GameAppOperation.QQFAV_DATALINE_DESCRIPTION, "测试简介测试简介测试简介测试简介测试简介测试简介测试简介");
        params.putString(GameAppOperation.TROOPBAR_ID, "198317283");
//        params.putStringArrayList(GameAppOperation.QQFAV_DATALINE_FILEDATA, fileDataList);
        mTencent.shareToTroopBar(activity, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e("===onComplete",""+o.toString());
            }

            @Override
            public void onError(UiError uiError) {

                Log.e("===onError",""+uiError.toString());
            }

            @Override
            public void onCancel() {

                Log.e("===onCancel","cancel");
            }
        });
    }
}
