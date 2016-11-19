package net.alfredandersson.indaplusplus.raytracer;

import net.alfredandersson.indaplusplus.raytracer.shapes.Sphere;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.alfredandersson.indaplusplus.raytracer.lights.DirectionalLight;
import net.alfredandersson.indaplusplus.raytracer.materials.FlatMaterial;
import net.alfredandersson.indaplusplus.raytracer.shapes.Plane;

public class Main extends ApplicationAdapter {
  
  private SpriteBatch sb;
  private Texture tex;
  
  private boolean hasSaved = false;
  private boolean isFirstFrame = true;
  
  private int width, height;
  private float[] img;
  
  @Override
  public void create() {
    DisplayMode dm = Gdx.graphics.getDisplayMode();
    width = dm.width;
    height = dm.height;
    
    Scene sc = new Scene();
    
    sc.add(new Sphere(new FlatMaterial(new Color(0.05f, 0.05f, 0.9f, 1.0f), new Color(0.3f, 0.3f, 0.3f, 1.0f), new Color(0.3f, 0.3f, 0.3f, 1.0f)), -1.25f, 0, 0, 1f));
    sc.add(new Sphere(new FlatMaterial(new Color(0.1f, 0.1f, 0.1f, 1.0f), Color.BLACK, Color.WHITE), 1.25f, 0, 0, 1f));
    
    sc.add(new Plane(new FlatMaterial(new Color(0.9f, 0.9f, 0.9f, 1.0f), new Color(0.5f, 0.5f, 0.5f, 1.0f), new Color(0.2f, 0.2f, 0.2f, 1.0f)),
            new Vector3(0, 0, 1), new Vector3(0, 0, 1)));
    
    sc.add(new DirectionalLight(1, 1, -1, 0.5f, 0.5f, 0.5f));
    
    RayTracer rt = new RayTracer(sc);
    img = rt.rayTrace(1920, 1080, new Vector3(0, 5, 0), new Vector3(0, -1, 0), new Vector3(0, 0, 1), 60);
    
    tex = new Texture(new CustomTextureData(img, width, height));
    
    sb = new SpriteBatch();
  }
  
  private void update() {
    if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
      Gdx.app.exit();
    }
    
    if (!hasSaved && !isFirstFrame) {
      hasSaved = true;
      
      // convert and save image
      BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      int[] ibuf = ((DataBufferInt)bimg.getRaster().getDataBuffer()).getData();
      for (int i = 0; i < ibuf.length; i++) {
        int r = toByte(img[i * 4 + 0]) & 0xFF;
        int g = toByte(img[i * 4 + 1]) & 0xFF;
        int b = toByte(img[i * 4 + 2]) & 0xFF;
        int a = toByte(img[i * 4 + 3]) & 0xFF;
        
        ibuf[i] = (a << 24) | (r << 16) | (g << 8) | (b);
      }
      
      try {
        File file = new File("renders/" + System.currentTimeMillis() + ".png");
        file.mkdirs();
        
        ImageIO.write(bimg, "png", file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  private byte toByte(float f) {
    return (byte)Math.min(Math.max(Math.round(SRGB.toSRGB(f) * 255), 0), 255);
  }
  
  @Override
  public void render() {
    update();
    
    sb.begin();
    sb.draw(tex, 0, 0);
    sb.end();
    
    isFirstFrame = false;
  }
  
  @Override
  public void dispose() {
    tex.dispose();
  }
}
