package com.vanda.vRbac.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages="org.chu.mapper",annotationClass=Mapper.class)
@EnableTransactionManagement //<tx:annotaion-driver>
public class DataConfig {
	
    //四要素
	@Value("${db.driverClassName}")
	private String driverClassName;
	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;

	//1.数据源
	@Bean(name="dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource=new  BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaxTotal(10);
		dataSource.setMaxWaitMillis(30000);
		return dataSource;
	}
	
	//2.会话工厂
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory() {
		SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
		SqlSessionFactory sessionFactory=null;
		try {
			//指定数据源
			factoryBean.setDataSource(this.getDataSource());
			//org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
			//驼峰命名法的支持
			//configuration.setMapUnderscoreToCamelCase(true);
			//factoryBean.setConfiguration(configuration);
			//填充SqlSessionFactoryBean对象参数到SqlSessionFactory会话工厂
			factoryBean.afterPropertiesSet();
			sessionFactory = factoryBean.getObject();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return sessionFactory;
	}
	
	//3.配置Mybatis的动态对象加入到Spring容器
	//问题：Spring框架已经提供了ComponentScan扫描对象到容器里面了，为什么Mybatis还需要自己扫描
	//答：因为Spring的ComponentScan只能扫描实现类的对象，而Mybatis使用的是接口的动态对象。
	//Spring框架无法扫描，所以只能Mybatis自己实现
	/*
	@Bean //方法是静态的，表示配置类还是没有创建对象，就可以被调用
	public static MapperScannerConfigurer getMapperScannerConfigurer() {
		MapperScannerConfigurer scan =new MapperScannerConfigurer();
		//ָ指定使用的会话工厂
		scan.setSqlSessionFactoryBeanName("sqlSessionFactory");
		//ָ指定扫描的包名
		scan.setBasePackage("org.chu.mapper");
		//ָ指定映射接口的限制注解
		scan.setAnnotationClass(Mapper.class);
		return scan;
	}*/
	
	//4.事务代理对象
	@Bean
	public DataSourceTransactionManager getTransactionManager() {
		DataSourceTransactionManager tm=new DataSourceTransactionManager();
		//指定数据源
		tm.setDataSource(this.getDataSource());
		return tm;
	}
	

}
