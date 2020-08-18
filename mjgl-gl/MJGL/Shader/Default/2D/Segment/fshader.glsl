#version 330

in vec4 vsOutColor;
layout(location=0) fsOutColor;

void main(){
    fsOutColor=vsOutColor;
}
