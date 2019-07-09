#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
  vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform int top;

void main()
{
  vec2 myTc = vec2(fs_in.tc.x, fs_in.tc.y);

  if (top == 1)
  {
    myTc.y = 1 - myTc.y;
  }

  color = texture(tex, myTc);

  if (color.w < 1.0)
  {
    discard;
  }
}