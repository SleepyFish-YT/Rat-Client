#version 120

uniform vec2 location, rectSize;
uniform vec4 color, outlineColor;
uniform float radius, outlineThickness;

float roundedSDF(vec2 centerPos, vec2 size, float radius) {
    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;
}

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This class contains code of Soar shit skid client made by a retard named eldodebug
 * @author Nexuscript 2024
 */
void main() {
    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);

    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));

    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);

    gl_FragColor = mix(outlineColor, insideColor, blendAmount);
}