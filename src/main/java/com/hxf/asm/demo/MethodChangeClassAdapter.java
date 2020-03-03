package com.hxf.asm.two;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodChangeClassAdapter extends ClassVisitor implements Opcodes {
    public MethodChangeClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        if (cv != null) {
            cv.visit(version, access, name, signature, superName, interfaces);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        //当方法名为testFirst时，修改方法名为testFirstNew
        if (cv != null && "testFirst".equals(name)){
            return cv.visitMethod(access, name + "New", desc, signature, exceptions);
        }

        //此处的testSecond即为需要修改的方法  ，修改方法內容
        if("testSecond".equals(name)){
            //先得到原始的方法
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            MethodVisitor newMethod = null;
            //访问需要修改的方法，使用ASMMethodVisit进行改造
            newMethod = new AsmMethodVisit(mv);
            return newMethod;
        }
        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return null;
    }
}
