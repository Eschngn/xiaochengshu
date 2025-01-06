package com.swc.orangeBook.kv.biz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/17 11:07
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${spring.cassandra.keyspace-name}")
    private String keySpace;
    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.cassandra.port}")
    private int port;

    /**
     * Provide a keyspace name to the configuration.
     * @return
     */
    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }
    @Override
    public String getContactPoints() {
        return contactPoints;
    }

    @Override
    public int getPort() {
        return port;
    }
}
