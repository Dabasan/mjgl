#version 330

uniform float z;

layout(location=0) in vec2 vsInPosition;
layout(location=1) in vec4 vsInColor;
out vec4 vsOutColor;

void main(){
    gl_Position=vec4(vsInPosition,z,1.0);
    vsOutColor=vsInColor;
}
