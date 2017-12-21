package com.sharingif.cube.batch.core;

import com.sharingif.cube.batch.core.request.JobRequest;

import java.util.List;

/**
 * job服务
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:16
 */
public interface JobService {

    /**
     * 添加job
     */
    void add(JobRequest jobRequest);

    /**
     * job处理成功
     * @param jobId : 处理的job id
     * @param jobModel : 成功处理信息
     */
    void add(String jobId, JobModel jobModel);

    /**
     * job处理成功
     * @param jobId : 处理的job id
     * @param jobModelList : 成功处理信息
     */
    void add(String jobId, List<JobModel> jobModelList);

    /**
     * job处理成功
     * @param jobId : 处理的job id
     */
    void success(String jobId);

    /**
     * job处理失败
     * @param jobId : 处理的job id
     * @param message : 错误码
     * @param localizedMessage : 本地错误消息
     * @param cause : 错误原因
     */
    void failure(String jobId, String message, String localizedMessage, String cause);

    /**
     * 加载数据库job记录到队列中
     */
    void putQueue();

    /**
     * 消费队列中的job
     */
    void consume();

    /**
     * 处理系统重启队列中的job状态
     */
    void updateJobStatusInQueueToSuspending();

}
