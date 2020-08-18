#version 330

uniform float z;

layout(location=0) in vec2 vsInPosition;
layout(location=1) in vec2 vsInUV;
out vec2 vsOutUV;

void main(){
    gl_Position=vec4(vsInPosition,z,1.0);
    vsOutUV=vsInUV;
}
