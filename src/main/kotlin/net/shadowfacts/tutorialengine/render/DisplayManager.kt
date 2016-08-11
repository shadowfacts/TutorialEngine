package net.shadowfacts.tutorialengine.render

import org.lwjgl.LWJGLException
import org.lwjgl.opengl.*

/**
 * @author shadowfacts
 */
object DisplayManager {

	private val WIDTH = 1280
	private val HEIGHT = 720
	private val FPS = 120

	fun create() {
		val attribs = ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true)

		try {
			Display.setDisplayMode(DisplayMode(WIDTH, HEIGHT))
			Display.create(PixelFormat(), attribs)
			Display.setTitle("Tutorial Engine")
		} catch (e: LWJGLException) {
			throw RuntimeException(e)
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT)
	}

	fun update() {
		Display.sync(FPS)
		Display.update()
	}

	fun close() {
		Display.destroy()
	}

}
