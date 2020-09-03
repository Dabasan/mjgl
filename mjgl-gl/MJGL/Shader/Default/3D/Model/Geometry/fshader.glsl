#version 330

uniform sampler2D tex;

in vec3 vsOutPosition;
in vec2 vsOutUV;
in vec3 vsOutNormal;
layout(location=0) out vec3 fsOutAlbedo;
layout(location=1) out vec3 fsOutPosition;
layout(location=3) out vec3 fsOutNormal;

void main(){
    fsOutColor=texture(tex,vsOutUV).rgb;
    fsOutPosition=vsOutPosition;
    fsOutNormal=vsOutNormal;
}
