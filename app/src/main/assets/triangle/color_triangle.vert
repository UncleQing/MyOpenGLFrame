attribute vec4 vPosition;
attribute vec4 vColor;
uniform mat4 vMatrix;
varying vec4 aColor;

void main() {
    gl_Position = vMatrix * vPosition;
    aColor = vColor;
}
