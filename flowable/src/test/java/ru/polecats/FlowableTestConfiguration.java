package ru.polecats;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
import org.flowable.job.service.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static org.flowable.common.engine.impl.AbstractEngineConfiguration.DB_SCHEMA_UPDATE_FALSE;

@Configuration
public class FlowableTestConfiguration {

	@Bean
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:tcp://localhost/flowable");
//		ds.setUsername("");
//		ds.setPassword("");
		ds.setDefaultAutoCommit(false);
		return ds;
	}

	@Bean
	public AsyncExecutor asyncExecutor() {
		DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
		asyncExecutor.setAsyncJobAcquisitionEnabled(true);
		return asyncExecutor;
	}

	@Bean
	public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,
		AsyncExecutor asyncExecutor) {
		ProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration()
			.setAsyncExecutor(asyncExecutor)
			.setDataSource(dataSource)
			.setAsyncExecutorActivate(true)
			.setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_FALSE);

		return processEngineConfiguration;
	}
}
