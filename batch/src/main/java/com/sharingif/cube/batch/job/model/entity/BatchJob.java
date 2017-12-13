package com.sharingif.cube.batch.job.model.entity;


import javax.validation.constraints.*;

import com.sharingif.cube.batch.core.request.JobRequest;
import org.hibernate.validator.constraints.*;


public class BatchJob implements java.io.Serializable {

	/**
	 * 待处理
	 */
	public static final String STATUS_SUSPENDING="0";
	/**
	 * 队列中
	 */
	public static final String STATUS_IN_QUEUE="1";
	/**
	 * 处理中
	 */
	public static final String STATUS_HANDLING="2";
	/**
	 * 处理完成
	 */
	public static final String STATUS_SOLVED="3";
	/**
	 * 处理失败
	 */
	public static final String STATUS_FAILED="4";
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键id			db_column: ID 
     */	
	@Length(max=40)
	private java.lang.String id;
    /**
     * 父级job id			db_column: PARENT_JOB_ID 
     */	
	@Length(max=40)
	private java.lang.String parentJobId;
    /**
     * 交易名			db_column: LOOKUP_PATH 
     */	
	@NotBlank @Length(max=200)
	private java.lang.String lookupPath;
    /**
     * 计划执行时间			db_column: PLAN_EXECUTE_TIME 
     */	
	@NotNull 
	private java.util.Date planExecuteTime;
    /**
     * 实际执行时间			db_column: ACTUAL_EXECUTE_TIME 
     */	
	@NotNull 
	private java.util.Date actualExecuteTime;
    /**
     * 执行次数			db_column: EXECUTE_COUNT 
     */	
	@NotNull 
	private java.lang.Integer executeCount;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	@NotNull 
	private java.util.Date createTime;
    /**
     * 失败次数			db_column: STATUS 
     */	
	@NotBlank @Length(max=1)
	private java.lang.String status;
    /**
     * 错误码			db_column: ERROR_MESSAGE_CODE 
     */	
	@Length(max=200)
	private java.lang.String errorMessageCode;
    /**
     * 本地错误消息			db_column: ERROR_LOCALIZED_MESSAGE 
     */	
	@Length(max=500)
	private java.lang.String errorLocalizedMessage;
    /**
     * 错误原因			db_column: ERROR_CAUSE 
     */	
	@Length(max=6000)
	private java.lang.String errorCause;
	//columns END

	public JobRequest convertJobRequest() {
		JobRequest jobRequest = new JobRequest();
		jobRequest.setParentJobId(getParentJobId());
		jobRequest.setId(getId());
		jobRequest.setLookupPath(getLookupPath());
		jobRequest.setPlanExecuteTime(getPlanExecuteTime());
		jobRequest.setExecuteCount(getExecuteCount());
		jobRequest.setCreatetime(getCreateTime());

		return jobRequest;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getId() {
		return this.id;
	}
	public void setParentJobId(java.lang.String parentJobId) {
		this.parentJobId = parentJobId;
	}
	public java.lang.String getParentJobId() {
		return this.parentJobId;
	}
	public void setLookupPath(java.lang.String lookupPath) {
		this.lookupPath = lookupPath;
	}
	public java.lang.String getLookupPath() {
		return this.lookupPath;
	}
	public void setPlanExecuteTime(java.util.Date planExecuteTime) {
		this.planExecuteTime = planExecuteTime;
	}
	public java.util.Date getPlanExecuteTime() {
		return this.planExecuteTime;
	}
	public void setActualExecuteTime(java.util.Date actualExecuteTime) {
		this.actualExecuteTime = actualExecuteTime;
	}
	public java.util.Date getActualExecuteTime() {
		return this.actualExecuteTime;
	}
	public void setExecuteCount(java.lang.Integer executeCount) {
		this.executeCount = executeCount;
	}
	public java.lang.Integer getExecuteCount() {
		return this.executeCount;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setErrorMessageCode(java.lang.String errorMessageCode) {
		this.errorMessageCode = errorMessageCode;
	}
	public java.lang.String getErrorMessageCode() {
		return this.errorMessageCode;
	}
	public void setErrorLocalizedMessage(java.lang.String errorLocalizedMessage) {
		this.errorLocalizedMessage = errorLocalizedMessage;
	}
	public java.lang.String getErrorLocalizedMessage() {
		return this.errorLocalizedMessage;
	}
	public void setErrorCause(java.lang.String errorCause) {
		this.errorCause = errorCause;
	}
	public java.lang.String getErrorCause() {
		return this.errorCause;
	}

	public String toString() {
		return new StringBuilder("BatchJob [")
			.append("Id=").append(getId()).append(", ")
					.append("ParentJobId=").append(getParentJobId()).append(", ")
					.append("LookupPath=").append(getLookupPath()).append(", ")
					.append("PlanExecuteTime=").append(getPlanExecuteTime()).append(", ")
					.append("ActualExecuteTime=").append(getActualExecuteTime()).append(", ")
					.append("ExecuteCount=").append(getExecuteCount()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("ErrorMessageCode=").append(getErrorMessageCode()).append(", ")
					.append("ErrorLocalizedMessage=").append(getErrorLocalizedMessage()).append(", ")
					.append("ErrorCause=").append(getErrorCause())
		.append("]").toString();
	}
	
}

