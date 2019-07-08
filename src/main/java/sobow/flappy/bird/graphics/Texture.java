package sobow.flappy.bird.graphics;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_BGRA;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import sobow.flappy.bird.utils.BufferUtils;

public class Texture
{
    private int width, height;
    private int texture;

    public Texture(String path)
    {
        texture = load(path);
    }

    private int load(String path)
    {
        int[] pixels = null;
        try
        {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        /*
        this part is unnessesray
        insted of bit operation we can do this:
        in function glTexImage2D first fromat parameter should be GL_RGBA,
        and the second format parameter should be GL_BGRA

        int[] data = new int[width * height];

        for (int i = 0; i < width * height; i++)
        {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00 >> 8);
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        */

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D,
                     0,
                     GL_RGBA,
                     width,
                     height,
                     0,
                     GL_BGRA,
                     GL_UNSIGNED_BYTE,
                     BufferUtils.createIntBuffer(pixels));
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
