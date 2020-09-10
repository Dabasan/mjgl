#version 330

struct Camera{
    float near;
    float far;
    vec3 position;
    vec3 target;
    mat4 vp;
};
uniform Camera camera;

struct Fog{
    float start;
    float end;
    vec4 color;
};
uniform Fog fog;

struct DirectionalLight{
    vec3 position;
    vec3 target;
    vec4 colorAmbient;
    vec4 colorDiffuse;
    vec4 colorSpecular;
};
uniform DirectionalLight directionalLight;

uniform sampler2D textureSampler;

in vec3 vsOutPosition;
in vec2 vsOutUV;
in vec3 vsOutNormal;
layout(location=0) out vec4 fsOutColor;

void main(){
    //Lighting
    vec3 cameraDirection=normalize(camera.target-camera.position);
    vec3 lightDirection=normalize(directioanlLight.target-directionalLight.position);
    vec3 halfLE=-normalize(cameraDirection+lightDirection);

    float coefDiffuse=clamp(dot(vsOutNormal,-lightDirection),0.0,1.0);
    float coefSpecular=pow(clamp(dot(vsOutNormal,halfLE),0.0,1.0),2.0);

    vec4 colorAmbient=vec4(directioanlLight.colorAmbient);
    vec4 colorDiffuse=vec4(directioanlLight.colorDiffuse*coefDiffuse);
    vec4 colorSpecular=vec4(directioanlLight.colorSpecular*coefSpecular);

    vec4 colorPostLighting=colorAmbient+colorDiffuse+colorSpecular;
    colorPostLighting.a=1.0;

    //Fog
    float linearPos=length(camera.position-vsOutPosition);
    float fogFactor=clamp((fog.end-linearPos)/(fog.end-fog.start),0.0,1.0);

    fsOutColor=mix(
        fog.color,
        colorPostLighting*texture(textureSampler,vsOutUV),
        fogFactor
    );
}
