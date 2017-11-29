package com.sharingif.cube.batch.app.dao;

import com.sharingif.cube.persistence.mybatis.dao.ICubeMyBatisDAO;

import java.io.Serializable;

/**
 * BaseDAO
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 上午11:44
 */
public interface BaseDAO<T, ID extends Serializable> extends ICubeMyBatisDAO<T, ID> {
}
