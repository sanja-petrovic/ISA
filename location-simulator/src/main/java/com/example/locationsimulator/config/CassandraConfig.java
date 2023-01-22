package com.example.locationsimulator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    private final String hosts;
    private final String keyspace;

    CassandraConfig(
            @Value("${spring.cassandra.contact-points}") String hosts,
            @Value("${spring.cassandra.keyspace-name}") String keyspace) {
        this.hosts = hosts;
        this.keyspace = keyspace;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .ifNotExists();

        return Arrays.asList(specification);
    }

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }


    @Override
    protected String getContactPoints() {
        return hosts;
    }
}
