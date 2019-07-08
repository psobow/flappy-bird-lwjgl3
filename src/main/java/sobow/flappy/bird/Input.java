package sobow.flappy.bird;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback
{
    public static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action != GLFW.GLFW_RELEASE;

        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
        {
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        }
    }

    public static boolean isKeyDown(int keyCode)
    {
        return keys[keyCode];
    }

}
