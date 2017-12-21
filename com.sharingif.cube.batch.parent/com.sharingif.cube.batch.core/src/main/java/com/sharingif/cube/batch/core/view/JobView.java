package com.sharingif.cube.batch.core.view;

import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;

/**
 * job返回视图
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:04
 */
public class JobView implements View {

    private JobService jobService;

    public JobService getJobService() {
        return jobService;
    }

    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        JobRequest jobRequest = (JobRequest)requestContext.getRequest();
        JobModel jobModel = (JobModel)returnValue;

        if(exceptionContent != null) {
            ICubeException cubeException =  exceptionContent.getCubeException();

            if(StringUtils.isTrimEmpty(jobRequest.getId())) {
                return;
            }
            jobService.failure(jobRequest.getId(), cubeException.getMessage(), cubeException.getLocalizedMessage(), ((Exception)cubeException).getCause().toString());

            return;
        }

        if(!StringUtils.isTrimEmpty(jobRequest.getId())) {
            jobService.success(jobRequest.getId());
        }

        if(returnValue != null) {
            if(StringUtils.isTrimEmpty(jobRequest.getId())) {
                jobService.success(jobModel);
            } else {
                jobService.success(jobRequest.getId(), jobModel);
            }
        }


    }
}
