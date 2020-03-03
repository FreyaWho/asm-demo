package com.hxf.asm.demo;

public class Foo {
    public void testFirst(){
        System.out.println("do the first process!");
    }

    public void testSecond() {

        System.out.println("second process starts!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("second process ends!");
    }
}