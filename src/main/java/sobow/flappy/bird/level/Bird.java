package sobow.flappy.bird.level;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import sobow.flappy.bird.graphics.Shader;
import sobow.flappy.bird.graphics.Texture;
import sobow.flappy.bird.graphics.VertexArray;
import sobow.flappy.bird.math.Matrix4f;
import sobow.flappy.bird.math.Vector3f;
import sobow.flappy.bird.utils.Input;

public class Bird
{
    private float SIZE = 1.0f;
    private VertexArray mesh;
    private Texture texture;

    private Vector3f position = new Vector3f();
    private float rot;
    private float delta = 0.0f;

    public Bird()
    {
        float[] vertices = new float[]{
                -SIZE / 2.0f, -SIZE / 2.0f, 0.2f,

                -SIZE / 2.0f, SIZE / 2.0f, 0.2f,

                SIZE / 2.0f, SIZE / 2.0f, 0.2f,

                SIZE / 2.0f, -SIZE / 2.0f, 0.2f
        };

        byte[] indices = new byte[]{
                0, 1, 2, 2, 3, 0
        };

        float[] tcs = new float[]{
                0, 1, 0, 0, 1, 0, 1, 1
        };

        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture("res/bird.png");
    }

    public void update()
    {
        // modify bird Y position only if he is located between top and bottom of the window
        if (position.y - delta > -5.0f && position.y - delta < 5.0f)
        {
            rot = -delta * 90.0f;
            position.y -= delta;
        }

        if (Input.isKeyDown(GLFW_KEY_SPACE) && Level.getPlayerInControl())
        {
            delta = -0.15f;
        }
        else
        {
            delta += 0.01f;
        }
    }

    public void render()
    {
        Shader.BIRD.enable();
        Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
        texture.bind();
        mesh.render();
        Shader.BIRD.disable();
    }

    public void fall()
    {
        delta = 0.02f;
    }

    public float getY()
    {
        return position.y;
    }

    public float getSize()
    {
        return SIZE;
    }

    public void resetBird()
    {
        delta = 0.0f;
        position.y = 0.0f;
        rot = 0.0f;
    }
}
