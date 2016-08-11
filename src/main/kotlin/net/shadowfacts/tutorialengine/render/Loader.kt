package net.shadowfacts.tutorialengine.render

import net.shadowfacts.tutorialengine.model.RawModel
import net.shadowfacts.tutorialengine.texture.ModelTexture
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.newdawn.slick.opengl.TextureLoader

import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * @author shadowfacts
 */
object Loader {

	private val vaos: MutableList<Int> = mutableListOf()
	private val vbos: MutableList<Int> = mutableListOf()
	private val textures: MutableList<Int> = mutableListOf()

	fun loadToVAO(positions: FloatArray, textureCoords: FloatArray, indices: IntArray): RawModel {
		val vao = createVAO()
		bindIndicesBuffer(indices)
		storeData(0, 3, positions)
		storeData(1, 2, textureCoords)
		unbindVAO()
		return RawModel(vao, indices.size)
	}

	fun loadTexture(name: String): Int {
		val texture = TextureLoader.getTexture("PNG", Loader::class.java.getResourceAsStream("/tutorialengine/textures/$name.png"))
		textures.add(texture.textureID)
		return texture.textureID
	}

	fun loadModelTexture(name: String): ModelTexture {
		return ModelTexture(loadTexture(name))
	}

	fun cleanUp() {
		vaos.forEach(GL30::glDeleteVertexArrays)
		vbos.forEach(GL15::glDeleteBuffers)
		textures.forEach(GL11::glDeleteTextures)
	}

	private fun createVAO(): Int {
		val vao = GL30.glGenVertexArrays()
		vaos.add(vao)
		GL30.glBindVertexArray(vao)
		return vao
	}

	private fun storeData(listNumber: Int, vertexSize: Int, data: FloatArray) {
		val vbo = GL15.glGenBuffers()
		vbos.add(vbo)
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
		val buf = createBuffer(data)
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW)
		GL20.glVertexAttribPointer(listNumber, vertexSize, GL11.GL_FLOAT, false, 0, 0)
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
	}

	private fun unbindVAO() {
		GL30.glBindVertexArray(0)
	}

	private fun bindIndicesBuffer(indices: IntArray) {
		val vbo = GL15.glGenBuffers()
		vbos.add(vbo)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo)
		val buf = createBuffer(indices)
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW)
	}

	private fun createBuffer(data: IntArray): IntBuffer {
		val buf = BufferUtils.createIntBuffer(data.size)
		buf.put(data)
		buf.flip()
		return buf
	}

	private fun createBuffer(data: FloatArray): FloatBuffer {
		val buf = BufferUtils.createFloatBuffer(data.size)
		buf.put(data)
		buf.flip()
		return buf
	}

}
