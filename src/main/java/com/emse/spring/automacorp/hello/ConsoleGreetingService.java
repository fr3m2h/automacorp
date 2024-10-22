package com.emse.spring.automacorp.hello;

 /*

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

 */
import org.springframework.stereotype.Service;

@Service
public class ConsoleGreetingService  implements GreetingService{
    public void greet(String name){
        System.out.printf("Hello, %s!%n", name);
    }
}

 /*
@Service
public class ConsoleGreetingService implements GreetingService {

    private final CycleService cycleService;

    @Autowired
    public ConsoleGreetingService(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @Override
    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
}

 */