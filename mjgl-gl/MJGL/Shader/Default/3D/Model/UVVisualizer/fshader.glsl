#version 330

uniform sampler2D texUV;

in vec2 vsOutUV;
layout(location=0) out vec4 fsOutColor;

void main(){
    fsOutColor=vec4(texture(texUV,vsOutUV).rg,1.0,1.0);
}
