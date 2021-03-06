package com.sharingif.cube.batch.app.autoconfigure.persistence.datasource;

import com.sharingif.cube.persistence.database.DataSourcePoolConfig;
import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.aes.AESECBEncryptor;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PersistenceComponentsAutoconfigure
 * 2017年5月3日 上午11:12:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class PersistenceComponentsAutoconfigure {

	@Bean(name="dataSourcePoolConfig")
	public DataSourcePoolConfig createDataSourcePoolConfig(
			@Value("${batch.dataSource.jndi.type}")String type
			,@Value("${batch.dataSource.driverClassName}")String driverClassName
			,@Value("${batch.dataSource.url}")String url
			,@Value("${batch.dataSource.username}")String username
			,@Value("${batch.dataSource.password}")String password
			,@Value("${batch.dataSource.initialSize}")int initialSize
			,@Value("${batch.dataSource.maxTotal}")int maxTotal
			,@Value("${batch.dataSource.maxWaitMillis}")int maxWaitMillis
			,@Value("${batch.dataSource.maxIdle}")int maxIdle
			,@Value("${batch.dataSource.minIdle}")int minIdle
	) {

		DataSourcePoolConfig dataSourcePoolConfig = new DataSourcePoolConfig();
		dataSourcePoolConfig.setType(type);
		dataSourcePoolConfig.setDriverClassName(driverClassName);
		dataSourcePoolConfig.setUrl(url);
		dataSourcePoolConfig.setUsername(username);
		dataSourcePoolConfig.setPassword(password);
		dataSourcePoolConfig.setInitialSize(initialSize);
		dataSourcePoolConfig.setMaxTotal(maxTotal);
		dataSourcePoolConfig.setMaxWaitMillis(maxWaitMillis);
		dataSourcePoolConfig.setMaxIdle(maxIdle);
		dataSourcePoolConfig.setMinIdle(minIdle);

		return dataSourcePoolConfig;
	}

	@Bean("propertyTextEncryptor")
	public TextEncryptor createPropertyTextEncryptor(@Value("${batch.property.key}") String key) throws UnsupportedEncodingException {
		Base64Coder base64Coder = new Base64Coder();
		byte[] keysByte = base64Coder.decode(key);
		AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);

		return encryptor;
	}

	@Bean(name="dataSource")
	public org.apache.tomcat.jdbc.pool.DataSource createOpayDataSource(DataSourcePoolConfig dataSourcePoolConfig, TextEncryptor propertyTextEncryptor) throws SQLException {
		PoolProperties poolProperties = new PoolProperties();
		poolProperties.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

		poolProperties.setDriverClassName(dataSourcePoolConfig.getDriverClassName());
		poolProperties.setUrl(dataSourcePoolConfig.getUrl());
		poolProperties.setUsername(dataSourcePoolConfig.getUsername());

		String password = dataSourcePoolConfig.getPassword();
		if(propertyTextEncryptor != null) {
			password = propertyTextEncryptor.decrypt(password);
		}
		poolProperties.setPassword(password);

		// 创建连接阶段
		poolProperties.setInitialSize(dataSourcePoolConfig.getInitialSize());										// 启动时初始化多少连接数
		poolProperties.setMaxActive(dataSourcePoolConfig.getMaxTotal());											// 连接池中最多能有多少连接数
		poolProperties.setMaxWait(dataSourcePoolConfig.getMaxWaitMillis());									        // 请求连接池最大等待时间，单位：毫秒
		poolProperties.setMaxIdle(dataSourcePoolConfig.getMaxIdle());												// 链接池中最大空闲连接
		poolProperties.setMinIdle(dataSourcePoolConfig.getMinIdle());												// 链接池中最小空闲连接

		// 程序关闭连接阶段,归还连接到连接池
		poolProperties.setRemoveAbandoned(dataSourcePoolConfig.isRemoveAbandonedOnBorrow());				        // 是否清理被遗弃的连接，和removeAbandonedTimeout一起使用
		poolProperties.setRemoveAbandonedTimeout(dataSourcePoolConfig.getRemoveAbandonedTimeout());					// 清理被遗弃的连接等待时间，单位：秒

		// 空闭连接处理阶段,空闭连接断开从连接池中清处掉
		poolProperties.setTimeBetweenEvictionRunsMillis(dataSourcePoolConfig.getTimeBetweenEvictionRunsMillis()); 	// 多长时间检查一次连接池中空闲的连接,单位：毫秒
		poolProperties.setMinEvictableIdleTimeMillis(dataSourcePoolConfig.getMinEvictableIdleTimeMillis());  		// 空闲时间超过多少时间的连接断开,直到连接池中的连接数到minIdle为止，单位：毫秒
		poolProperties.setNumTestsPerEvictionRun(dataSourcePoolConfig.getNumTestsPerEvictionRun());					// 每次检查连接的数目，建议设置和maxActive一样大
		poolProperties.setLogAbandoned(dataSourcePoolConfig.isLogAbandoned());										// 接池收回空闲的活动连接时是否打印消息

		// 连接验证阶段,防止mysql主动断开连接
		poolProperties.setTestOnBorrow(dataSourcePoolConfig.isTestOnBorrow());										// 取时检验
		poolProperties.setTestOnReturn(dataSourcePoolConfig.isTestOnReturn());										// 归还检验
		poolProperties.setTestWhileIdle(dataSourcePoolConfig.isTestWhileIdle());									// 空闲检验
		poolProperties.setValidationQuery(dataSourcePoolConfig.getValidationQuery());

		org.apache.tomcat.jdbc.pool.DataSource opayDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		opayDataSource.setPoolProperties(poolProperties);

		return opayDataSource;

	}
	
	@Bean(name="dataSourceTransactionManager")
	public DataSourceTransactionManager createDataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		
		return dataSourceTransactionManager;
	}
	
}