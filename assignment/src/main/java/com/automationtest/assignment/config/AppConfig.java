package com.automationtest.assignment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:application_${environment}.yml","classpath:environment/${environment}/testdata.properties"})
@ComponentScan("com.automationtest.assignment")
public class AppConfig {

}
