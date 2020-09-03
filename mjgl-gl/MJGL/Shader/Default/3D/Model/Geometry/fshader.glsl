#version 330

in vec3 vsOutPosition;
in vec2 vsOutUV;
in vec3 vsOutNormal;
layout(location=0) out float fsOutAlbedo;
layout(location=1) out vec3 fsOutPosition;
layout(location=2) out vec2 fsOutUV;
layout(location=3) out vec3 fsOutNormal;

void main(){
    fsOutAlbedo=1.0;
    fsOutPosition=vsOutPosition;
    fsOutUV=vsOutUV;
    fsOutNormal=vsOutNormal;
}
