#version 120

uniform sampler2D textureIn;
uniform vec2 texelSize, direction;
uniform float radius, weights[256];

#define offset texelSize * direction

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This class contains code of Soar shit skid client made by a retard named eldodebug
 * @author Nexuscript 2024
 */
void main() {
    vec3 color = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];
    float totalWeight = weights[0];

    for (float f = 1.0F; f <= radius; f++) {
        color += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);
        color += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);

        totalWeight += (weights[int(abs(f))]) * 2.0F;
    }

    gl_FragColor = vec4(color / totalWeight, 1.0F);
}