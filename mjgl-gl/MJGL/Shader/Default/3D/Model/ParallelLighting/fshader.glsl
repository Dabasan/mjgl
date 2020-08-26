#version 330

struct GBuffer{
    sampler2D texAlbedo;
    sampler2D texPosition;
    sampler2D texNormal;
};
uniform GBuffer gBuffer;

const int MAX_NUM_LIGHTS=4;
struct ParallelLight{
    vec3 direction;
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
layout(location=0) out vec3 fsOutFactor;

void main(){
    vec3 cameraDirection=normalize(camera.target-camera.position);
    vec3 fragNormal=texture(gBuffer.texNormal,vsOutUV).rgb;

    vec4 sumColorPostLighting=vec4(0.0);

    int boundNumLights=max(numLights,MAX_NUM_LIGHTS);
    for(int i=0;i<boundNumLights;i++){
        vec3 halfLE=-normalize(cameraDirection+light.direction);

        float coefDiffuse=clamp(dot(fragNormal,-light.direction),0.0,1.0);
        float coefSpecular=pow(clamp(dot(fragNormal,halfLE),0.0,1.0),2.0);

        vec4 colorAmbient=vec4(light.colorAmbient*light.powerAmbient);
        vec4 colorDiffuse=vec4(light.colorDiffuse*light.powerDiffuse*coefDiffuse);
        vec4 colorSpecular=vec4(light.colorSpecular*light.powerSpecular*coefSpecular);

        vec4 colorPostLighting=colorAmbient+colorDiffuse+colorSpecular;
        colorPostlighting.a=1.0;

        sumColorPostLighting+=colorPostLighting;
    }

    fsOutFactor=sumColorPostLighting.xyz;
}
