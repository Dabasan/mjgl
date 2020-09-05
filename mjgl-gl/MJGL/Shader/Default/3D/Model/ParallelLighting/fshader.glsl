#version 330

struct GBuffer{
    sampler2D texColor;
    sampler2D texPosition;
    sampler2D texUV;
    sampler2D texNormal;
};
uniform GBuffer gBuffer;

const int MAX_NUM_LIGHTS=4;
struct ParallelLight{
    vec3 position;
    vec3 target;
    vec4 colorAmbient;
    vec4 colorDiffuse;
    vec4 colorSpecular;
    float powerAmbient;
    float powerDiffuse;
    float powerSpecular;
};
uniform ParallelLight lights[MAX_NUM_LIGHTS];
uniform int numLights;

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
    vec3 cameraDirection=normalize(camera.target-camera.position);
    vec3 fragNormal=texture(gBuffer.texNormal,vsOutUV).rgb;

    vec4 sumColorPostLighting=vec4(0.0);

    int boundNumLights=min(numLights,MAX_NUM_LIGHTS);
    for(int i=0;i<boundNumLights;i++){
        vec3 lightDirection=normalize(lights[i].target-lights[i].position);

        vec3 halfLE=-normalize(cameraDirection+lightDirection);

        float coefDiffuse=clamp(dot(fragNormal,-lightDirection),0.0,1.0);
        float coefSpecular=pow(clamp(dot(fragNormal,halfLE),0.0,1.0),2.0);

        vec4 colorAmbient=vec4(lights[i].colorAmbient*lights[i].powerAmbient);
        vec4 colorDiffuse=vec4(lights[i].colorDiffuse*lights[i].powerDiffuse*coefDiffuse);
        vec4 colorSpecular=vec4(lights[i].colorSpecular*lights[i].powerSpecular*coefSpecular);

        vec4 colorPostLighting=colorAmbient+colorDiffuse+colorSpecular;
        colorPostLighting.a=1.0;

        sumColorPostLighting+=colorPostLighting;
    }

    fsOutFactor=vec4(sumColorPostLighting.xyz,1.0);
}
