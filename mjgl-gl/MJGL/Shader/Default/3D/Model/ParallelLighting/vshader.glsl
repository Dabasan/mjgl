#version 330

layout(location=0) in vec3 vsInPosition;
layout(location=1) in vec2 vsInUV;
out vec2 vsOutUV;

void main(){
    gl_Position=vec4(vsInPosition,0.0,1.0);
    vsOutUV=vsInUV;
}
