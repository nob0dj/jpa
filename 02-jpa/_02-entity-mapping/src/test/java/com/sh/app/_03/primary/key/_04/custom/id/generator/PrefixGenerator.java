package com.sh.app._03.primary.key._04.custom.id.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class PrefixGenerator implements IdentifierGenerator {
    private String prefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        String query = String.format("select %s from %s", session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(), obj.getClass().getSimpleName());
        System.out.println(query);
        Stream ids = session.createQuery(query).stream();
        Long max = ids.map(o -> o.toString().replace(prefix + "-", "")).mapToLong(value -> Long.parseLong(value.toString())).max().orElse(0L);
        return prefix + "-" + (max + 1);
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) {
        prefix = properties.getProperty("prefix");
    }
}
