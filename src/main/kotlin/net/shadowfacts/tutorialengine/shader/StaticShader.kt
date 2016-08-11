package net.shadowfacts.tutorialengine.shader

/**
 * @author shadowfacts
 */
const val VERTEX_NAME = "vertexShader"
const val FRAGMENT_NAME = "fragmentShader"

class StaticShader : ShaderProgram(VERTEX_NAME, FRAGMENT_NAME) {

	override fun bindAttributes() {
		bindAttribute(0, "position")
		bindAttribute(1, "in_TextureCoords")
	}

}