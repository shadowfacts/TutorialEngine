package net.shadowfacts.tutorialengine.render

import net.shadowfacts.tutorialengine.model.RawModel
import net.shadowfacts.tutorialengine.model.TexturedModel
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

/**
 * @author shadowfacts
 */
class Renderer {

	fun prepare() {
		GL11.glClearColor(1f, 0f, 0f, 1f)
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
	}

	fun render(model: TexturedModel) {
		val rawModel = model.rawModel
		GL30.glBindVertexArray(rawModel.vaoID)
		GL20.glEnableVertexAttribArray(0)
		GL20.glEnableVertexAttribArray(1)
		GL13.glActiveTexture(GL13.GL_TEXTURE0)
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.texture.id)
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.vertexCount, GL11.GL_UNSIGNED_INT, 0)
		GL20.glDisableVertexAttribArray(0)
		GL20.glDisableVertexAttribArray(1)
		GL30.glBindVertexArray(0)
	}

}
