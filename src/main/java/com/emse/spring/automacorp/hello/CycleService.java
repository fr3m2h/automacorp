package com.emse.spring.automacorp.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CycleService {

    private final ConsoleGreetingService consoleGreetingService;

    @Autowired
    public CycleService(ConsoleGreetingService consoleGreetingService) {
        this.consoleGreetingService = consoleGreetingService;
    }
}