package com.sharingif.cube.batch.job.service.impl;

import com.sharingif.cube.batch.job.dao.BatchJobDAO;
import com.sharingif.cube.batch.job.model.entity.BatchJob;
import com.sharingif.cube.batch.job.service.BatchJobService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * BatchJobServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 下午5:57
 */
@Service
public class BatchJobServiceImpl implements BatchJobService {

    private BatchJobDAO batchJobDAO;

    @Resource
    public void setBatchJobDAO(BatchJobDAO batchJobDAO) {
        this.batchJobDAO = batchJobDAO;
    }

    @Override
    public List<BatchJob> getSuspendingStatus(int queryJobSize) {

        BatchJob batchJob = new BatchJob();
        batchJob.setStatus(BatchJob.STATUS_SUSPENDING);
        PaginationCondition<BatchJob> suspendingPaginationCondition = new PaginationCondition<BatchJob>();
        suspendingPaginationCondition.setCondition(batchJob);
        suspendingPaginationCondition.setQueryCount(false);
        suspendingPaginationCondition.setCurrentPage(1);
        suspendingPaginationCondition.setPageSize(queryJobSize);

        PaginationRepertory suspendingPaginationRepertory = batchJobDAO.queryPagination(suspendingPaginationCondition);

        return suspendingPaginationRepertory.getPageItems();
    }

    @Override
    public int updateStatusToInQueue(String id) {

        BatchJob batchJob = new BatchJob();
        batchJob.setId(id);
        batchJob.setStatus(BatchJob.STATUS_IN_QUEUE);

        return batchJobDAO.updateById(batchJob);
    }

    @Override
    public int updateStatusToHandling(String id) {
        BatchJob batchJob = new BatchJob();
        batchJob.setId(id);
        batchJob.setStatus(BatchJob.STATUS_HANDLING);

        return batchJobDAO.updateById(batchJob);
    }

    @Override
    public int updateJobStatusInQueueToSuspending() {
        return batchJobDAO.updateStatusByStatus(BatchJob.STATUS_IN_QUEUE, BatchJob.STATUS_SUSPENDING);
    }

}
