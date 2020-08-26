#version 330

uniform sampler2D textureSampler;

in vec3 vsOutPosition;
in vec2 vsOutUV;
in vec3 vsOutNormal;
layout(location=0) out vec4 fsOutAlbedo;
layout(location=1) out vec3 fsOutPosition;
layout(location=2) out vec3 fsOutNormal;

void main(){
    fsOutAlbedo=texture(textureSampler,vsOutUV);
    fsOutPosition=vsOutPosition;
    fsOutNormal=vsOutNormal;
}
