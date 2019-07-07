package sobow.flappy.bird.graphics;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;
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

        int[] data = new int[width * height];

        for (int i = 0; i < width * height; i++)
        {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00 >> 8);
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = GL11.glGenTextures();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, result);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL_TEXTURE_2D,
                          0,
                          GL_RGBA,
                          width,
                          height,
                          0,
                          GL_RGBA,
                          GL_UNSIGNED_BYTE,
                          BufferUtils.createIntBuffer(data));
        GL11.glBindTexture(GL_TEXTURE_2D, 0);
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
