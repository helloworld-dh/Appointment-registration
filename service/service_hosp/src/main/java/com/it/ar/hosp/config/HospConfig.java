package com.it.ar.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.it.ar.hosp.mapper")
public class HospConfig {
}
