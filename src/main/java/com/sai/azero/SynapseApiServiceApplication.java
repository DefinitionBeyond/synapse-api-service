package com.sai.azero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = {"com.sai.azero.mapper"})
@SpringBootApplication
public class SynapseApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynapseApiServiceApplication.class, args);
    }

}
