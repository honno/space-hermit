package coursework.barberm1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import GraphicsLab.FloatBuffer;
import GraphicsLab.Normal;
import GraphicsLab.Vertex;

/**
 * Contains useful functions used across the package.
 */
public class Util {
	public static String pckgDir = "coursework/barberm1";
	public static double rad = 2 * Math.PI;

	/**
	 * Draw a rectangular polygon with given vertexes.
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 */
	public static void drawRect(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
		GL11.glBegin(GL11.GL_POLYGON);
		new Normal(v1.toVector(), v2.toVector(), v3.toVector(), v4.toVector()).submit();
		v1.submit();
		v2.submit();
		v3.submit();
		v4.submit();
		GL11.glEnd();
	}

	/**
	 * Draw a rectangular polygon with given vertexes and map texture
	 * coordinates.
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 */
	public static void drawTexRect(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
		GL11.glBegin(GL11.GL_POLYGON);
		new Normal(v1.toVector(), v2.toVector(), v3.toVector(), v4.toVector()).submit();
		GL11.glTexCoord2f(1.0f, 0.0f);
		v1.submit();
		GL11.glTexCoord2f(1.0f, 1.0f);
		v2.submit();
		GL11.glTexCoord2f(0.0f, 1.0f);
		v3.submit();
		GL11.glTexCoord2f(0.0f, 0.0f);
		v4.submit();
		GL11.glEnd();
	}

	/**
	 * Draw a triangle polygon with given vertexes.
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public static void drawTri(Vertex v1, Vertex v2, Vertex v3) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		new Normal(v1.toVector(), v2.toVector(), v3.toVector()).submit();
		v1.submit();
		v2.submit();
		v3.submit();
		GL11.glEnd();
	}

	/**
	 * Set material properties to GL11 buffer.
	 * 
	 * @param shininess
	 * @param specular
	 * @param colour
	 */
	public static void material(float shininess, float[] specular, float[] colour) {
		GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, shininess);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(specular));
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, FloatBuffer.wrap(colour));
	}

	/**
	 * Loads provided images into OpenGL textures.
	 * 
	 * @param dir
	 *            the path to the images
	 * @param names
	 *            a array of all the names of the images
	 * @return an arraylist of the textures that loaded successfully
	 */
	public static List<Texture> loadTextures(String dir, String[] names) {
		// initiate arraylist that stores successfully loaded textures
		List<Texture> textures = new ArrayList<Texture>();
		// regex pattern that captures the image's type (i.e. png, jpg, etc.)
		Pattern ext = Pattern.compile("\\.(.+)");
		// iterates over every image provided to attempt loading as a texture
		for (String name : names) {
			try {
				// applies regex pattern to image name
				Matcher match = ext.matcher(name);
				match.find();
				// loads texture with the path to image generated by the
				// provided dir value and current name, and provides what is the
				// image's type using captured regex pattern from name
				textures.add(loadTexture(dir + "/" + name, match.group(1).toUpperCase()));
			} catch (IOException e) {
				// captures any errors (i.e. no pattern found, path does not
				// exist, etc.)
				e.printStackTrace();
			}
		}
		return textures;
	}

	/**
	 * Load a texture.
	 * 
	 * @param path
	 *            path of texture
	 * @param imageType
	 *            type of image
	 * @return loaded Texture object
	 * @throws IOException
	 *             incase path doesn't exist or texture cannot be read
	 */
	public static Texture loadTexture(String path, String imageType) throws IOException {
		Texture tex = TextureLoader.getTexture(imageType, ResourceLoader.getResourceAsStream(path), true);
		return tex;
	}
}