package net.shadowfacts.tutorialengine.shader

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author shadowfacts
 */
abstract class ShaderProgram {

	val program: Int
	val vertexShader: Int
	val fragmentShader: Int

	constructor(vertexFile: String, fragmentFile: String) {
		vertexShader = loadShader(vertexFile, GL20.GL_VERTEX_SHADER)
		fragmentShader = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER)
		program = GL20.glCreateProgram()
		GL20.glAttachShader(program, vertexShader)
		GL20.glAttachShader(program, fragmentShader)
		GL20.glLinkProgram(program)
		bindAttributes()
		GL20.glValidateProgram(program)
	}

	protected abstract fun bindAttributes()

	fun bindAttribute(attr: Int, variableName: String) {
		GL20.glBindAttribLocation(program, attr, variableName)
	}

	fun start() {
		GL20.glUseProgram(program)
	}

	fun stop() {
		GL20.glUseProgram(0)
	}

	fun cleanUp() {
		stop()
		GL20.glDetachShader(program, vertexShader)
		GL20.glDetachShader(program, fragmentShader)
		GL20.glDeleteShader(vertexShader)
		GL20.glDeleteShader(fragmentShader)
		GL20.glDeleteProgram(program)
	}

}

fun loadShader(name: String, type: Int): Int {
	val source = StringBuilder()
	val reader = BufferedReader(InputStreamReader(ShaderProgram::class.java.getResourceAsStream("/tutorialengine/shaders/$name.glsl")))
	var line: String? = reader.readLine()
	while (line != null) {
		source.append(line).append("\n")
		line = reader.readLine()
	}
	reader.close()

	val id = GL20.glCreateShader(type)
	GL20.glShaderSource(id, source)
	GL20.glCompileShader(id)
	if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
		println(GL20.glGetShaderInfoLog(id, 500))
		throw RuntimeException("Could not compile shader")
	}
	return id
}