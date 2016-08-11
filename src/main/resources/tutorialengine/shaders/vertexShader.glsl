#version 400 core

in vec3 position;
in vec2 in_TextureCoords;

out vec2 textureCoords;

void main(void) {

	gl_Position = vec4(position, 1.0);
	textureCoords = in_TextureCoords;

}