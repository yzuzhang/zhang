package com.feicent.zhang.guava.eventbus;

public class TestEvent {
    private final String message;
    public TestEvent(String message) {        
        this.message = message;
        System.out.println("Send Message: "+message);
    }
    public String getMessage() {
        return message;
    }
}
