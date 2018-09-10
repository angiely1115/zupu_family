package com.lv.zupu.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;

@Configuration
@Slf4j
@Order(Integer.MAX_VALUE)
@AutoConfigureAfter(
        value = {DruidDataSourceAutoConfigure.class}
)
public class SpringContextConfig implements BeanPostProcessor,ApplicationContextAware,EnvironmentAware, DisposableBean {
    private static ApplicationContext applicationContext;
    private ConfigurableEnvironment environment;
    private static final String[] PROPERTIES_TO_COPY = new String[]{"log4jdbc.log4j2.properties.file", "log4jdbc.debug.stack.prefix", "log4jdbc.sqltiming.warn.threshold", "log4jdbc.sqltiming.error.threshold", "log4jdbc.dump.booleanastruefalse", "log4jdbc.dump.fulldebugstacktrace", "log4jdbc.dump.sql.maxlinelength", "log4jdbc.statement.warn", "log4jdbc.dump.sql.select", "log4jdbc.dump.sql.insert", "log4jdbc.dump.sql.update", "log4jdbc.dump.sql.delete", "log4jdbc.dump.sql.create", "log4jdbc.dump.sql.addsemicolon", "log4jdbc.auto.load.popular.drivers", "log4jdbc.drivers", "log4jdbc.trim.sql", "log4jdbc.trim.sql.extrablanklines", "log4jdbc.suppress.generated.keys.exception", "log4jdbc.log4j2.properties.file"};

    public SpringContextConfig() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public static <T> String[] getBeans(Class<T> requiredType) {
        return applicationContext.getBeanNamesForType(requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public static Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DataSource){
            DataSource dataSource = (DataSource) bean;
            log.info("**********dataSource*********:{}",dataSource);
            DataSourceSpy dataSourceSpy = new DataSourceSpy(dataSource);
            initLog4Jdbc();
            return dataSourceSpy;
        }
        return bean;

    }

 /*   @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }*/
    private void initLog4Jdbc() {
        String[] var1 = PROPERTIES_TO_COPY;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String property = var1[var3];
            if (this.environment.containsProperty(property)) {
                System.setProperty(property, this.environment.getProperty(property));
            }
        }

        System.setProperty("log4jdbc.spylogdelegator.name", this.environment.getProperty("log4jdbc.spylogdelegator.name", Slf4jSpyLogDelegator.class.getName()));
    }
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}