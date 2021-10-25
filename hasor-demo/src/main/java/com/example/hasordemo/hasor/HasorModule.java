package com.example.hasordemo.hasor;

import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.db.Level;
import net.hasor.db.JdbcModule;
import net.hasor.spring.SpringModule;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@DimModule
@Component
public class HasorModule implements SpringModule {
    @Resource
    private DataSource dataSource;
    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }
}