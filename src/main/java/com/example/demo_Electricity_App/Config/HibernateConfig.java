package com.example.demo_Electricity_App.Config;

import com.example.demo_Electricity_App.Tenant.SchemaMultiTenantConnectionProvider;
import com.example.demo_Electricity_App.Tenant.TenantIdentifierResolver;
import org.hibernate.cfg.MultiTenancySettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HibernateConfig implements HibernatePropertiesCustomizer {
    @Autowired
    private SchemaMultiTenantConnectionProvider connectionProvider;
    @Autowired
    private TenantIdentifierResolver Resolver;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, Resolver);
    }
}
