#version 330

struct GBuffer{
    sampler2D texColor;
    sampler2D texPosition;
    sampler2D texNormal;
};
uniform GBuffer gBuffer;

struct Fog{
    float start;
    float end;
    vec4 color;
};
uniform Fog fog;

struct Camera{
    float near;
    float far;
    vec3 position;
    vec3 target;
    mat4 vp;
};
uniform Camera camera;

in vec2 vsOutUV;
layout(location=0) out vec4 fsOutFactor;

void main(){
    vec3 vertexPosition=texture(gBuffer.texPosition,vsOutUV);
    float linearPos=length(camera.position-vertexPosition);
    float fogFactor=clamp((fog.end-linearPos)/(fog.end-fog.start),0.0,1.0);

    fsOutFactor=vec4(vec3(fogFactor),1.0);
}
