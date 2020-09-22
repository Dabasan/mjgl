#version 330

uniform mat4 depthVP;

layout(location=0) in vec3 vsInPosition;

void main(){
    gl_Position=depthVP*vec4(vsInPosition,1.0);
}
