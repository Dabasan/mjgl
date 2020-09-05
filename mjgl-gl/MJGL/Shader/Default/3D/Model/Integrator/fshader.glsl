#version 330

struct GBuffer{
    sampler2D texColor;
    sampler2D texPosition;
    sampler2D texNormal;
};
uniform GBuffer gBuffer;

const int MAX_NUM_TEX_FACTORS=16;
uniform sampler2D texFactors[MAX_NUM_TEX_FACTORS];
uniform int numTexFactors;

in vec2 vsOutUV;
layout(location=0) out vec4 fsOutColor;

void main(){
    vec3 color=texture(gBuffer.texColor,vsOutUV).rgb;

    int boundNumTexFactors=min(numTexFactors,MAX_NUM_TEX_FACTORS);
    for(int i=0;i<boundNumTexFactors;i++){
        vec3 factor=texture(texFactors[i],vsOutUV).rgb;
        color*=factor;
    }

    fsOutColor=vec4(color,1.0);
}
