package com.sharingif.cube.batch.job.dao;

import com.sharingif.cube.batch.app.dao.BaseDAO;
import com.sharingif.cube.batch.job.model.entity.BatchJob;

/**
 * BatchJobDAO
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 上午11:42
 */
public interface BatchJobDAO extends BaseDAO<BatchJob, String> {

    /**
     * 修改当前job状态为另外一种状态
     * @param currentStatus : 当前状态
     * @param updateStatus : 修改状态
     * @return
     */
    int updateStatusByStatus(String currentStatus, String updateStatus);

}
