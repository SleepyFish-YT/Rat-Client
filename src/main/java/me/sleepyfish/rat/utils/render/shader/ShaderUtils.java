package me.sleepyfish.rat.utils.render.shader;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This class contains code of Soar shit skid client made by a retard named eldodebug
 * @author SleepyFish 2024
 */
public class ShaderUtils {

    private static final Minecraft mc = MinecraftUtils.mc;
    private final int programID;

    public ShaderUtils(final String fragShaderLoc, final String shaderLoc) {
        final int program = GL20.glCreateProgram();

        try {
            final int fragmentShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(fragShaderLoc)).getInputStream(), GL20.GL_FRAGMENT_SHADER);
            GL20.glAttachShader(program, fragmentShaderID);
            final int vertexShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(shaderLoc)).getInputStream(), GL20.GL_VERTEX_SHADER);
            GL20.glAttachShader(program, vertexShaderID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GL20.glLinkProgram(program);

        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0)
            throw new IllegalStateException("Shader failed to link!");

        this.programID = program;
    }

    public ShaderUtils(final String fragmentShaderLoc) {
        this(fragmentShaderLoc, MinecraftUtils.resourcePath + "/shaders/vertex.vsh");
    }

    public void init() {
        GL20.glUseProgram(programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public void setUniformf(final String name, final float... args) {
        final int loc = GL20.glGetUniformLocation(programID, name);

        switch (args.length) {
            case 1: {
                GL20.glUniform1f(loc, args[0]);
                break;
            }

            case 2: {
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            }

            case 3: {
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            }

            case 4: {
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
            }
        }
    }

    public void setUniform(final String name, final int... args) {
        final int loc = GL20.glGetUniformLocation(programID, name);

        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        } else {
            GL20.glUniform1i(loc, args[0]);
        }
    }

    public static void drawQuads(final float x, final float y, final float width, final float height) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
    }

    private int createShader(final InputStream inputStream, final int type) {
        final int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, readInputStream(inputStream));
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0)
            return type;

        return shader;
    }

    public static String readInputStream(final InputStream inputStream) {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

}