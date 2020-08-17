#version 330

uniform sampler2D textureSampler;

in vec2 vsOutUV;
layout(location=0) out vec4 fsOutColor;

void main(){
    fsOutColor=texture(textureSampler,vsOutUV);
}
