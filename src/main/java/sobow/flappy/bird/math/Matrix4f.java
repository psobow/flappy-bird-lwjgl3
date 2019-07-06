package sobow.flappy.bird.math;

import java.nio.FloatBuffer;
import sobow.flappy.bird.utils.BufferUtils;

public class Matrix4f
{
    public float[] elements = new float[4 * 4];

    private Matrix4f()
    {
    }

    public static Matrix4f identity()
    {
        Matrix4f result = new Matrix4f();

        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 3] = 1.0f;
        return result;
    }

    public static Matrix4f orthopgrahic(float left, float right, float bottom, float top, float near, float far)
    {
        Matrix4f result = identity();
        result.elements[0 + 0 * 4] = 2.0f / (right - left);
        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
        result.elements[2 + 2 * 4] = 2.0f / (near - far);

        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = (far + near) / (far - near);

        return result;
    }

    public static Matrix4f translate(Vector3f vector3f)
    {
        Matrix4f result = identity();
        result.elements[0 + 3 * 4] = vector3f.x;
        result.elements[1 + 3 * 4] = vector3f.y;
        result.elements[2 + 3 * 4] = vector3f.z;

        return result;
    }

    public static Matrix4f rotate(float angle)
    {
        Matrix4f result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0 + 0 * 4] = cos;
        result.elements[1 + 0 * 4] = sin;
        result.elements[0 + 1 * 4] = -sin;
        result.elements[1 + 1 * 4] = cos;

        return result;
    }

    public Matrix4f multiply(Matrix4f matrix4f)
    {
        Matrix4f result = new Matrix4f();
        for (int row = 0; row < 4; row++)
        {
            for (int column = 0; column < 4; column++)
            {
                float sum = 0.0f;
                for (int i = 0; i < 4; i++)
                {
                    sum += elements[column + i * 4] * matrix4f.elements[i + row * 4];
                }
                /*
                    0  1  2  3
                    4  5  6  7
                    8  9  10 11
                    12 13 14 15
                indecies from first iteration
                0 * 0
                4 * 1
                8 * 2
                12 * 3
                */
                result.elements[row + column * 4] = sum;
            }
        }
        return result;
    }

    public FloatBuffer toFloatBuffer()
    {
        return BufferUtils.createFloatBuffer(elements);
    }
}
