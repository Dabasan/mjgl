#version 330

uniform sampler2D texVector;

in vec2 vsOutUV;
layout(location=0) out vec4 fsOutColor;

void main(){
    fsOutColor=vec4(texture(texVector,vsOutUV).rgb,1.0);
}
