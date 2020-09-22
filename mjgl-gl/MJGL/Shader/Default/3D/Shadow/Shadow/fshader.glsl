#version 330

struct Light{
    int projectionType;
    mat4 depthBiasVP;
    vec3 direction;
};
const int MAX_NUM_LIGHTS=16;
uniform Light lights[MAX_NUM_LIGHTS];
uniform int numLights;

const int PROJECTION_TYPE_ORTHOGRAPHIC=0;
const int PROJECTION_TYPE_PERSPECTIVE=1;

uniform sampler2D textureSampler;
uniform sampler2D depthTextures[MAX_NUM_LIGHTS];
uniform ivec2 depthTextureSize;
uniform float biasCoefficient;
uniform float maxBias;
uniform float inShadowVisibility;

const int INTEGRATION_METHOD_MUL=0;
const int INTEGRATION_METHOD_MIN=1;
uniform int integrationMethod;

in vec3 vsOutPosition;
in vec2 vsOutUV;
in vec3 vsOutNormal;
in vec4 shadowCoords[MAX_NUM_LIGHTS];
out vec4 fsOutColor;

float PCFSampling(int index){
    if(shadowCoords[index].z>1.0){
        return 1.0;
    }

    float cosTh=abs(dot(lights[index].direction,vsOutNormal));
    float bias=biasCoefficient*tan(acos(cosTh));
    bias=clamp(bias,0.0,maxBias);

    float visibility=1.0;
    vec2 texelSize=1.0/depthTextureSize;

    if(lights[index].projectionType==PROJECTION_TYPE_ORTHOGRAPHIC){
        float visibilitySum=0.0;

        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                float pcfDepth=texture(
                    depthTextures[index],
                    shadowCoords[index].xy+vec2(x,y)*texelSize).r;
                if(pcfDepth<shadowCoords[index].z-bias){
                    visibilitySum+=inShadowVisibility;
                }
                else{
                    visibilitySum+=1.0;
                }
            }
        }

        visibility=visibilitySum/9.0;
    }
    else if(lights[index].projectionType==PROJECTION_TYPE_PERSPECTIVE){
        float visibilitySum=0.0;

        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                float pcfDepth=texture(
                    depthTextures[index],
                    shadowCoords[index].xy/shadowCoords[index].w+vec2(x,y)*texelSize).r;
                if(pcfDepth<(shadowCoords[index].z-bias)/shadowCoords[index].w){
                    visibilitySum+=inShadowVisibility;
                }
                else{
                    visibilitySum+=1.0;
                }
            }
        }

        visibility=visibilitySum/9.0;
    }

    return visibility;
}

void main(){
    float finalFactor=1.0;

    int bound=min(numLights,MAX_NUM_LIGHTS);
    for(int i=0;i<bound;i++){
        float factor=PCFSampling(i);
        if(integrationMethod==INTEGRATION_METHOD_MUL){
            finalFactor*=factor;
        }
        else{
            finalFactor=min(finalFactor,factor);
        }
    }

    vec4 colorFactor=vec4(vec3(finalFactor),1.0);
    fsOutColor=texture(textureSampler,vsOutUV)*colorFactor;
}
