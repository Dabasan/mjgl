#version 330

uniform sampler2D tex;

in vec3 vsOutPosition;
in vec2 vsOutUV;
in vec3 vsOutNormal;
layout(location=0) out vec4 fsOutColor;
layout(location=1) out vec4 fsOutPosition;
layout(location=2) out vec4 fsOutNormal;

void main(){
    fsOutColor=vec4(texture(tex,vsOutUV).rgb,1.0);
    fsOutPosition=vec4(vsOutPosition,1.0);
    fsOutNormal=vec4(vsOutNormal,1.0);
}
