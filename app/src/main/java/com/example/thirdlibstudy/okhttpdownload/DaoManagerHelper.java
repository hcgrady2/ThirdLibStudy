package com.example.thirdlibstudy.okhttpdownload;

import android.content.Context;

import com.example.thirdlibstudy.okhttpdownload.db.DaoSupportFactory;
import com.example.thirdlibstudy.okhttpdownload.db.DownloadEntity;
import com.example.thirdlibstudy.okhttpdownload.db.IDaoSupport;

import java.util.List;

/**
 * 数据库管理类
 */
final class DaoManagerHelper {
    private final static DaoManagerHelper sManager = new DaoManagerHelper();
    IDaoSupport<DownloadEntity> mDaoSupport;

    private DaoManagerHelper() {

    }

    public static DaoManagerHelper getManager() {
        return sManager;
    }

    public void init(Context context) {
        DaoSupportFactory.getFactory().init(context);

        //传入的实体的名字也是表名字

        mDaoSupport = DaoSupportFactory.getFactory().getDao(DownloadEntity.class);
    }

    public void addEntity(DownloadEntity entity) {
        long delete = mDaoSupport.delete("url = ? and threadId = ?", entity.getUrl(), entity.getThreadId() + "");
        long size = mDaoSupport.insert(entity);
    }

    public List<DownloadEntity> queryAll(String url) {
        return mDaoSupport.querySupport().selection("url = ?").selectionArgs(url).query();
    }

    public void remove(String url) {
        mDaoSupport.delete("url = ?", url);
    }
}
