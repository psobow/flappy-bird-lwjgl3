package sobow.flappy.bird;

import sobow.flappy.bird.graphics.Shader;
import sobow.flappy.bird.graphics.Texture;
import sobow.flappy.bird.graphics.VertexArray;

public class Level
{
    private VertexArray background;
    private Texture bgTexture;

    public Level()
    {
        float[] vertices = new float[]{
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,

                -10.0f, 10.0f * 9.0f / 16.0f, 0.0f,

                0.0f, 10.0f * 9.0f / 16.0f, 0.0f,

                0.0f, -10.0f * 9.0f / 16.0f, 0.0f
        };

        byte[] indices = new byte[]{
                0, 1, 2, 2, 3, 0
        };

        float[] tcs = new float[]{
                0, 1, 0, 0, 1, 0, 1, 1
        };

        background = new VertexArray(vertices, indices, tcs);
        bgTexture = new Texture("res/bg.jpeg");
    }

    public void render()
    {
        bgTexture.bind();

        Shader.background.enable();
        background.render();
        Shader.background.disable();

        bgTexture.unbind();
    }
}
