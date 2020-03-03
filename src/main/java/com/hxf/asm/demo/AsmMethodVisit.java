package com.hxf.asm.demo;

//import jdk.internal.org.objectweb.asm.MethodVisitor;
//import jdk.internal.org.objectweb.asm.Opcodes;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmMethodVisit extends MethodVisitor {
    public AsmMethodVisit(MethodVisitor mv) {
        super(Opcodes.ASM4, mv);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name,
                                String desc) {
        super.visitMethodInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitCode(){
        //此方法在访问方法的头部时被访问到，仅被访问一次
        //此处可插入新的指令

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // pushes the "Hello World!" String constant
        mv.visitLdcInsn("start timing for the second method!");
        // invokes the 'println' method (defined in the PrintStream class)
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");


        visitMethodInsn(Opcodes.INVOKESTATIC, Monitor.class.getName().replace(".","/"),  "start", "()V");


        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode){
        //此方法可以获取方法中每一条指令的操作类型，被访问多次
        //如应在方法结尾处添加新指令，则应判断：
        if(opcode == Opcodes.RETURN){
            // pushes the 'out' field (of type PrintStream) of the System class
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            // pushes the String constant
            mv.visitLdcInsn("stop the timing!");
            // invokes the 'println' method (defined in the PrintStream class)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

            visitMethodInsn(Opcodes.INVOKESTATIC, Monitor.class.getName().replace(".","/"),
                    "end", "()V");
        }
        super.visitInsn(opcode);
    }
}