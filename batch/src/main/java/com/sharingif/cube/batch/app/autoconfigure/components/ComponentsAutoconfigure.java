package com.sharingif.cube.batch.app.autoconfigure.components;

import com.sharingif.cube.persistence.database.DataSourcePoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * ComponentsAutoconfigure
 * 2017/5/23 上午11:43
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class ComponentsAutoconfigure {

    @Bean("workThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor(@Value("${work.max.pool.size}") int poolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setMaxPoolSize(poolSize);

        return threadPoolTaskExecutor;
    }

}
