#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
  vec2 tc;
  vec3 position;
} fs_in;

uniform sampler2D tex;
uniform vec2 bird;

void main()
{
  color = texture(tex, fs_in.tc);
  color *= 3.0 / (length(bird - fs_in.position.xy) + 2.5) + 0.3;
}