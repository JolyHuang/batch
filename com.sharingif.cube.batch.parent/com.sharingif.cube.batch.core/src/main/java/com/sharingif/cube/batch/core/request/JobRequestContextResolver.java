package com.sharingif.cube.batch.core.request;

import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.request.RequestContextResolver;

/**
 * 解析JobRequest请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午3:40
 */
public class JobRequestContextResolver implements RequestContextResolver<JobRequest, RequestContext<JobRequest>> {

    @Override
    public RequestContext<JobRequest> resolveRequest(JobRequest request) {

        RequestContext<JobRequest> requestContext = new RequestContext<JobRequest>("object", request.getLookupPath(), null, "POST",  request);

        return requestContext;
    }

}
