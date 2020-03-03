package com.hxf.asm.two;

import jdk.internal.org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AsmDemo extends ClassLoader implements Opcodes {

    public static void main(String args[]) throws IOException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException{
        ClassReader cr = new ClassReader(Foo.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new MethodChangeClassAdapter(cw);
        cr.accept(cv, Opcodes.ASM4);

        //新增加一个方法
//        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "add", "([Ljava/lang/String;)V", null, null);
//        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mw.visitLdcInsn("this is add method print!");
//        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
//        mw.visitInsn(RETURN);
//        // this code uses a maximum of two stack elements and two local
//        // variables
//        mw.visitMaxs(0, 0);
//        mw.visitEnd();

        byte[] code = cw.toByteArray();
        AsmDemo loader = new AsmDemo();
        Class<?> exampleClass = loader.defineClass(Foo.class.getName(), code, 0, code.length);

//        for(Method method : exampleClass.getMethods()){
//            System.out.println(method);
//        }

        //testFirst方法修改后名字变了
        System.out.println("testFirstMethod的名字修改："+Foo.class.getMethods()[0].getName()+"---->"+exampleClass.getMethods()[0].getName());
        exampleClass.getMethods()[0].invoke(exampleClass.newInstance(), null);

        System.out.println("***************************");

        exampleClass.getMethods()[1].invoke(exampleClass.newInstance(), null);
        // gets the bytecode of the Example class, and loads it dynamically

//        FileOutputStream fos = new FileOutputStream("e:\\logs\\Example.class");
//        fos.write(code);
//        fos.close();
    }

}
