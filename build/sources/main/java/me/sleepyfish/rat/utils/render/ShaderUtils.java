package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShaderUtils {

    private final int programID;

    public ShaderUtils(String fragShaderLoc, String shaderLoc) {
        int program = GL20.glCreateProgram();
        try {
            int fragmentShaderID = createShader(MinecraftUtils.mc.getResourceManager().getResource(new ResourceLocation(fragShaderLoc)).getInputStream(), GL20.GL_FRAGMENT_SHADER);
            GL20.glAttachShader(program, fragmentShaderID);
            int vertexShaderID = createShader(MinecraftUtils.mc.getResourceManager().getResource(new ResourceLocation(shaderLoc)).getInputStream(), GL20.GL_VERTEX_SHADER);
            GL20.glAttachShader(program, vertexShaderID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GL20.glLinkProgram(program);
        int status = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
        if (status == 0)
            throw new IllegalStateException("Shader failed to link!");

        this.programID = program;
    }

    public ShaderUtils(String fragmentShaderLoc) {
        this(fragmentShaderLoc, MinecraftUtils.path + "/shaders/vertex.vsh");
    }

    public void init() {
        GL20.glUseProgram(programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public int getUniform(String name) {
        return GL20.glGetUniformLocation(programID, name);
    }

    public void setUniformf(String name, float... args) {
        int loc = GL20.glGetUniformLocation(programID, name);
        switch (args.length) {
            case 1:
                GL20.glUniform1f(loc, args[0]);
                break;
            case 2:
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            case 3:
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            case 4:
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
        }
    }

    public void setUniform(String name, int... args) {
        int loc = GL20.glGetUniformLocation(programID, name);
        if (args.length > 1) GL20.glUniform2i(loc, args[0], args[1]);
        else GL20.glUniform1i(loc, args[0]);
    }

    public static void drawQuads(float x, float y, float width, float height) {
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

    public static void drawQuads() {
        ScaledResolution sr = new ScaledResolution(MinecraftUtils.mc);
        float width = (float) sr.getScaledWidth_double();
        float height = (float) sr.getScaledHeight_double();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(width, 0);
        GL11.glEnd();
    }

    private int createShader(InputStream inputStream, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, readInputStream(inputStream));
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader failed to compile!", type));
        }

        return shader;
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}