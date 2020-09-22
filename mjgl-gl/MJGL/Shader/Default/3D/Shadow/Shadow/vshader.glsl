#version 330

struct Camera{
    float near;
    float far;
    vec3 position;
    vec3 target;
    mat4 vp;
};
uniform Camera camera;

struct Light{
    int projectionType;
    mat4 depthBiasVP;
    vec3 direction;
};
const int MAX_NUM_LIGHTS=16;
uniform Light lights[MAX_NUM_LIGHTS];
uniform int numLights;

uniform float normalOffset;

layout(location=0) in vec3 vsInPosition;
layout(location=1) in vec2 vsInUV;
layout(location=2) in vec3 vsInNormal;
out vec3 vsOutPosition;
out vec2 vsOutUV;
out vec3 vsOutNormal;
out vec4 shadowCoords[MAX_NUM_LIGHTS];

void main(){
   gl_Position=camera.vp*vec4(vsInPosition,1.0);
   vsOutPosition=vsInPosition;
   vsOutUV=vsInUV;
   vsOutNormal=vsInNormal;

   int bound=min(numLights,MAX_NUM_LIGHTS);
   for(int i=0;i<bound;i++){
       shadowCoords[i]=lights[i].depthBiasVP*vec4(vsInPosition+vsInNormal*normalOffset,1.0);
   }
}
