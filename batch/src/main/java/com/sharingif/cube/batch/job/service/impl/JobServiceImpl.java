package com.sharingif.cube.batch.job.service.impl;

import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.batch.job.model.entity.BatchJob;
import com.sharingif.cube.batch.job.service.BatchJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * JobServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 上午11:32
 */
@Service
public class JobServiceImpl implements JobService, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 队列容量
     */
    private int queueSize;
    /**
     * 队列
     */
    private Queue<JobRequest> queue = new ConcurrentLinkedQueue<JobRequest>();
    /**
     * 查询job列表最大条数
     */
    private int queryJobSize;

    private BatchJobService batchJobService;
    private SimpleDispatcherHandler simpleDispatcherHandler;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Value("${job.queue.size}")
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
    @Value("${job.query.jobSize}")
    public void setQueryJobSize(int queryJobSize) {
        this.queryJobSize = queryJobSize;
    }
    @Resource
    public void setBatchJobService(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }
    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource(name = "workThreadPoolTaskExecutor")
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }
    @Resource
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Override
    public void success(String jobId) {

    }

    @Override
    public void success(String jobId, JobModel jobModel) {

    }

    @Override
    public void failure(String jobId, String message, String localizedMessage, String cause) {

    }

    @Override
    public void putQueue() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if(queue.size()>=queueSize) {

                        try {
                            TimeUnit.MINUTES.sleep(1);
                        } catch (InterruptedException e) {
                            logger.error("putQueue thread sleep error", e);
                        }

                        return;
                    }

                    try {

                        List<BatchJob> suspendingJobRequestList = batchJobService.getSuspendingStatus(queryJobSize);

                        if(suspendingJobRequestList == null || suspendingJobRequestList.size() == 0) {
                            try {
                                TimeUnit.MINUTES.sleep(1);
                            } catch (InterruptedException e) {
                                logger.error("putQueue thread sleep error", e);
                            }
                        }

                        for(BatchJob batchJob : suspendingJobRequestList){
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                            TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

                            batchJobService.updateStatusToInQueue(batchJob.getId());

                            queue.add(batchJob.convertJobRequest());

                            dataSourceTransactionManager.commit(status);
                        }

                    } catch (Exception e) {
                        logger.error("putQueue error", e);
                    }

                }
            }
        }).start();

    }

    @Override
    public void consume() {
        while (true) {

            try {

                JobRequest inQueueJobRequest = queue.peek();
                if(null == inQueueJobRequest){
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (InterruptedException e) {
                        logger.error("consume thread sleep error", e);
                    }
                    return;
                }

                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

                // 修改job状态
                batchJobService.updateStatusToHandling(inQueueJobRequest.getId());

                // 从队列中删除job
                queue.remove(inQueueJobRequest);

                dataSourceTransactionManager.commit(status);

                threadPoolTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        simpleDispatcherHandler.doDispatch(inQueueJobRequest);
                    }
                });

            } catch(Exception e) {
                logger.error("consume error", e);
            }
        }
    }

    @Override
    public void updateJobStatusInQueueToSuspending() {
        batchJobService.updateJobStatusInQueueToSuspending();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        updateJobStatusInQueueToSuspending();
        putQueue();
        consume();
    }
}
