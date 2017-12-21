package com.sharingif.cube.batch.core.view;

import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobModelAndView;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;

import java.util.List;

/**
 * 处理job视图
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:07
 */
public class JobViewResolver implements ViewResolver {

    private JobView jobView;
    private JobService jobService;

    public JobViewResolver(JobService jobService) {
        jobView = new JobView();
        jobView.setJobService(jobService);
    }

    public void setJobView(JobView jobView) {
        this.jobView = jobView;
    }

    @Override
    public View resolveView(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        if(null == returnValue
                || returnValue instanceof JobModel
                || (returnValue instanceof List && ((List)returnValue).size()>0 && (((List)returnValue).get(0) instanceof JobModel))
                || ((returnValue instanceof JobModelAndView) && (JobModelAndView.DEFAULT_VIEW_NAME.equals(((JobModelAndView)returnValue).getViewName())))
                ) {
            return jobView;
        }

        return null;
    }

}
