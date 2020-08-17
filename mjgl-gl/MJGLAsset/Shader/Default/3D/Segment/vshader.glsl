#version 330

struct Camera{
    float near;
    float far;
    vec3 position;
    vec3 target;
    mat4 vp;
};
struct Fog{
    float start;
    float end;
    vec4 color;
};

uniform Camera camera;
uniform Fog fog;

layout(location=0) in vec3 vsInPosition;
layout(location=1) in vec4 vsInColor;
out vec3 vsOutPosition;
out vec4 vsOutColor;

void main(){
    gl_Position=camera.vp*vec4(vsInPosition,1.0);
    vsOutPosition=vsInPosition;
    vsOutColor=vsInColor;
}
