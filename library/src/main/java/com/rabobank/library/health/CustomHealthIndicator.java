package com.rabobank.library.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private Random random= new Random();

    @Override
    public Health health(){
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check(){
        int result = 0;

        //helath check logic
        if(random.nextBoolean()){
            result =42;
        }

        return result;
    }
}
