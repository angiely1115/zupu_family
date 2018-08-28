package com.lv.zupu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.lv.zupu.mapperDao")
public class ZupuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZupuApplication.class, args);
	}
}
