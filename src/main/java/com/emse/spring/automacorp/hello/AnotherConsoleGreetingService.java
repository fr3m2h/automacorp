package com.emse.spring.automacorp.hello;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service @Primary
public class AnotherConsoleGreetingService  implements GreetingService{
    public void greet(String name){
        System.out.printf("Bonjour, %s!%n", name);
    }
}
