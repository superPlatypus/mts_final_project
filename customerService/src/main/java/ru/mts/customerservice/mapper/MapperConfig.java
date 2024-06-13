package ru.mts.customerservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerSerializer customerSerializer = new CustomerSerializer();
        SimpleModule simpleModule = new SimpleModule("Customer");
        simpleModule.addSerializer(customerSerializer);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
