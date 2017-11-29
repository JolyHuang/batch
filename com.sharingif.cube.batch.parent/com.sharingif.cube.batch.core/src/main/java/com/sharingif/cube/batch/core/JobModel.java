package com.sharingif.cube.batch.core;

import java.util.Date;

/**
 * job返回信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/28 下午6:10
 */
public class JobModel {

    /**
     * 交易名
     */
    private String lookupPath;
    /**
     * 计划执行时间
     */
    private Date planExecuteTime;

    public String getLookupPath() {
        return lookupPath;
    }

    public void setLookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
    }

    public Date getPlanExecuteTime() {
        return planExecuteTime;
    }

    public void setPlanExecuteTime(Date planExecuteTime) {
        this.planExecuteTime = planExecuteTime;
    }

}
