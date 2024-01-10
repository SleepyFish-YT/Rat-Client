#version 120

uniform vec2 rectSize;
uniform sampler2D textureIn;
uniform float radius, alpha;

float roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {
    return length(max(abs(centerPos) -size, 0.)) - radius;
}

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This class contains code of Soar shit skid client made by a retard named eldodebug
 * @author Nexuscript 2024
 */
void main() {
    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);
    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;

    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);
}