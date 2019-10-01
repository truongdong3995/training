package com.training.api.configs;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Data
@Component
public class CommonConfigurationProperties implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
