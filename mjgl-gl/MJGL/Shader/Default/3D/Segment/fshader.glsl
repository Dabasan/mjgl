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

in vec3 vsOutPosition;
in vec4 vsOutColor;
layout(location=0) out vec4 fsOutColor;

void main(){
    float linearPos=length(camera.position-vsOutPosition);
    float fogFactor=clamp((fog.end-linearPos)/(fog.end-fog.start),0.0,1.0);

    fsOutColor=mix(fog.color,vsOutColor,fogFactor);
}
