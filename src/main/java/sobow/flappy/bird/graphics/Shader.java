package sobow.flappy.bird.graphics;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL20;
import sobow.flappy.bird.math.Matrix4f;
import sobow.flappy.bird.math.Vector3f;
import sobow.flappy.bird.utils.ShaderUtils;

public class Shader
{
    private final int ID;
    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader(String vertex, String fragment)
    {
        ID = ShaderUtils.load(vertex, fragment);
    }

    public int getUniform(String name)
    {
        if (locationCache.containsKey(name))
        {
            return locationCache.get(name);
        }

        int result = GL20.glGetUniformLocation(ID, name);
        if (result == -1)
        {
            throw new RuntimeException("Could not find uniform variable '" + name + "'.");
        }
        else
        {
            locationCache.put(name, result);
        }
        return result;
    }

    public void setUniform1i(String name, int value)
    {
        GL20.glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value)
    {
        GL20.glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y)
    {
        GL20.glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector3f)
    {
        GL20.glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z);
    }

    public void setUniformMat4f(String name, Matrix4f matrix4f)
    {
        GL20.glUniformMatrix4fv(getUniform(name), false, matrix4f.toFloatBuffer());
    }

    public void enable()
    {
        GL20.glUseProgram(ID);
    }

    public void disable()
    {
        GL20.glUseProgram(0);
    }
}
