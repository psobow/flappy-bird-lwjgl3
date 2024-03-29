package sobow.flappy.bird.level;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import java.util.Random;
import sobow.flappy.bird.graphics.Shader;
import sobow.flappy.bird.graphics.Texture;
import sobow.flappy.bird.graphics.VertexArray;
import sobow.flappy.bird.math.Matrix4f;
import sobow.flappy.bird.math.Vector3f;
import sobow.flappy.bird.utils.Input;

public class Level
{
    private VertexArray background;
    private Texture bgTexture;
    private int xScroll = 0;
    private int map = 0;
    private Bird bird;
    private Pipe[] pipes = new Pipe[5 * 2];
    private int index = 0;
    private Random random = new Random();
    private float OFFSET = 8.0f;

    private static boolean playerInControl = true;
    private static boolean playerStartedGame = false;
    private static boolean gameOver = false;

    public Level()
    {
        // reset these values after creation new level
        playerInControl = true;
        playerStartedGame = false;
        gameOver = false;

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
        bird = new Bird();
        createPipes();
    }

    private void createPipes()
    {
        Pipe.create();
        for (int i = 0; i < 5 * 2; i += 2)
        {
            pipes[i] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f);
            pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 12.0f);
            index += 2;
        }
    }

    public void update()
    {
        if (playerStartedGame)
        {
            if (playerInControl)
            {
                xScroll--;
                if (-xScroll % 335 == 0)
                {
                    map++;
                }
                if (-xScroll > 400 && -xScroll % 120 == 0)
                {
                    updatePipes();
                }
            }

            bird.update();

            if (playerInControl && collisionWithPipes()) // action if collisionWithPipes occured
            {
                playerInControl = false;
                bird.fall();
            }

            if (playerInControl == false && Input.isKeyDown(GLFW_KEY_ENTER))
            {
                gameOver = true;
            }

        }
        else // Wait with rendering till player press space bar for first time
        {
            playerStartedGame = Input.isKeyDown(GLFW_KEY_SPACE);
        }
    }

    private void updatePipes()
    {
        pipes[index % 10] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f);
        pipes[(index + 1) % 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - 12.0f);
        index += 2;
    }

    private boolean collisionWithPipes()
    {
        for (int i = 0; i < 5 * 2; i++)
        {
            float bx = -xScroll * 0.05f;
            float by = bird.getY();

            float px = pipes[i].getX();
            float py = pipes[i].getY();

            float bx0 = bx - bird.getSize() / 2.0f;
            float bx1 = bx + bird.getSize() / 2.0f;

            float by0 = by - bird.getSize() / 2.0f;
            float by1 = by + bird.getSize() / 2.0f;

            float px0 = px;
            float px1 = px + Pipe.getWidth();

            float py0 = py;
            float py1 = py + Pipe.getHeight();

            if (bx1 > px0 && bx0 < px1) // bird is in pipe horizontaly
            {
                if (by1 > py0 && by0 < py1) // bird is in pipe verticaly
                {
                    return true;
                }
            }
        }

        return false;
    }

    public void render()
    {
        bgTexture.bind();

        Shader.BG.enable();
        Shader.BG.setUniform2f("bird", bird.getX(), bird.getY());
        background.bind();
        for (int i = map; i < map + 4; i++)
        {
            Shader.BG.setUniformMat4f("vw_matrix",
                                      Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
            background.draw();
        }
        Shader.BG.disable();

        bgTexture.unbind();
        renderPipes();
        bird.render();
    }

    private void renderPipes()
    {
        Shader.PIPE.enable();
        Shader.PIPE.setUniform2f("bird", bird.getX(), bird.getY());
        Shader.PIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();

        for (int i = 0; i < 5 * 2; i++)
        {
            Shader.PIPE.setUniformMat4f("ml_matrix", pipes[i].getMl_matrix());
            Shader.PIPE.setUniform1i("top", i % 2 == 0 ? 1 : 0);
            Pipe.getMesh().draw();
        }

        Pipe.getMesh().unbind();
        Pipe.getTexture().unbind();
    }

    public static boolean isGameOver()
    {
        return gameOver;
    }

    public static boolean getPlayerInControl()
    {
        return playerInControl;
    }

}
