package com.example.demo.context;

public class Context {
	private static ThreadLocal<Session> threadLocal = new InheritableThreadLocal<>();
	
	public static Session get() {
        return threadLocal.get();
    }

    public static void set(Session session) {
    	threadLocal.set(session);
    }

    public static void remove() {
    	threadLocal.remove();
    }
}
