/**
 * 
 */
package de.haproxyhq.config.nosql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.WriteConcern;

import de.haproxyhq.utils.PackageUtils;

/**
 * 
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = { PackageUtils.NOSQL_REPOSITORIES_PACKAGE })
public class CustomMongoDBRepositoryConfig {
	
	@Configuration
	@Profile("default")
	static class Default extends AbstractMongoConfiguration {
		
		@Value("${database.nosql.host}")
		private String databaseHost;
		
		@Value("${database.nosql.user}")
		private String databaseUsername;
		
		@Value("${database.nosql.password}")
		private String databasePassword;
		
		@Value("${database.nosql.port}")
		private int databasePort;
		
		@Value("${database.nosql.database}")
		private String databaseName;
		
		@Override
		protected String getDatabaseName() {
			return databaseName;
		}
		
		@Override
	    protected String getMappingBasePackage() {
	        return PackageUtils.NOSQL_PACKAGE;
	    }
		
		@Override
		public Mongo mongo() throws Exception {
			MongoClient mongoClient = new MongoClient(databaseHost, databasePort);
			mongoClient.getCredentialsList().add(getMongoCredentials());
			mongoClient.setWriteConcern(WriteConcern.UNACKNOWLEDGED);
			return mongoClient;
		}
		
		protected MongoCredential getMongoCredentials() {
			return MongoCredential.createCredential(databaseUsername, 
					databaseName, databasePassword.toCharArray());
		}

	}
	
}