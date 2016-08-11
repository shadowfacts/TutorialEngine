package net.shadowfacts.tutorialengine

import net.shadowfacts.tutorialengine.render.DisplayManager
import net.shadowfacts.tutorialengine.render.Loader
import net.shadowfacts.tutorialengine.model.RawModel
import net.shadowfacts.tutorialengine.model.TexturedModel
import net.shadowfacts.tutorialengine.render.Renderer
import net.shadowfacts.tutorialengine.shader.StaticShader
import net.shadowfacts.tutorialengine.texture.ModelTexture
import org.lwjgl.opengl.Display

/**
 * @author shadowfacts
 */
object Main {

	@JvmStatic fun main(args: Array<String>) {
		DisplayManager.create()

		val renderer = Renderer()
		val shader = StaticShader()

		val vertices = floatArrayOf(
				-1f, 0f, 0f,
				-1f, -1f, 0f,
				0f, -1f, 0f,
				0f, 0f, 0f
		)

		val indices = intArrayOf(
				0, 1, 3,
				3, 1, 2
		)

		val textureCoords = floatArrayOf(
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 0f
		)

		val model = Loader.loadToVAO(vertices, textureCoords, indices)
		val texture = Loader.loadModelTexture("shadowfacts")
		val texturedModel = TexturedModel(model, texture)

		while (!Display.isCloseRequested()) {
			renderer.prepare()

			shader.start()
			renderer.render(texturedModel)
			shader.start()

			DisplayManager.update()
		}

		Loader.cleanUp()
		shader.cleanUp()

		DisplayManager.close()
	}

}
