#version 330

uniform sampler2D textureSampler;

in vec2 vsOutUV;
layout(location=0) out vec4 fsOutColor;

void main(){
    float depth=texture(textureSampler,vsOutUV).r;
    fsOutColor=vec4(vec3(depth),1.0);
}
