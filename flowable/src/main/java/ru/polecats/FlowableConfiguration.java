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
public class FlowableConfiguration {

	@Bean
	public DataSource postgresDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driverª");
		ds.setUrl("jdbc:postgresql://localhost:5432/flowable");
		ds.setUsername("");
		ds.setPassword("");
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
	public ProcessEngineConfiguration processEngineConfiguration(DataSource postgresDataSource,
		AsyncExecutor asyncExecutor) {
		ProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration()
			.setAsyncExecutor(asyncExecutor)
			.setDataSource(postgresDataSource)
			.setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_FALSE)
			.setAsyncExecutorActivate(true);

		return processEngineConfiguration;
	}
}
