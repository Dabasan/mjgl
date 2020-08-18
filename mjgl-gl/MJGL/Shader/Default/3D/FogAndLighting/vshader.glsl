#version 330

struct Camera{
    float near;
    float far;
    vec3 position;
    vec3 target;
    mat4 vp;
};

uniform Camera camera;

layout(location=0) in vec3 vsInPosition;
layout(location=1) in vec2 vsInUV;
layout(location=2) in vec3 vsInNormal;
out vec3 vsOutPosition;
out vec2 vsOutUV;
out vec3 vsOutNormal;

void main(){
    gl_Position=camera.vp*vec4(vsInPosition,1.0);
    vsOutPosition=vsInPosition;
    vsOutUV=vsInUV;
    vsOutNormal=vsInNormal;
}
