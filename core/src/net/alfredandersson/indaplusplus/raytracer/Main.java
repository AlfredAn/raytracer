package net.alfredandersson.indaplusplus.raytracer;

import net.alfredandersson.indaplusplus.raytracer.shapes.Sphere;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import net.alfredandersson.indaplusplus.raytracer.lights.DirectionalLight;
import net.alfredandersson.indaplusplus.raytracer.materials.FlatMaterial;

public class Main extends ApplicationAdapter {
  
  private SpriteBatch sb;
  private Texture tex;
  
  @Override
  public void create() {
    Scene sc = new Scene();
    sc.add(new Sphere(new FlatMaterial(Color.BLUE, new Color(0.3f, 0.3f, 0.3f, 1.0f)), 0, 0, 0, 1));
    sc.add(new DirectionalLight(1, 1, -1, 0.5f, 0.5f, 0.5f));
    
    RayTracer rt = new RayTracer(sc);
    float[] img = rt.rayTrace(1920, 1080, new Vector3(3, 0, 0), new Vector3(-1, 0, 0), new Vector3(0, 0, 1), 90);
    
    tex = new Texture(new CustomTextureData(img, 1920, 1080));
    
    sb = new SpriteBatch();
  }
  
  private void update() {
    if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
      Gdx.app.exit();
    }
  }
  
  @Override
  public void render() {
    update();
    
    sb.begin();
    sb.draw(tex, 0, 0);
    sb.end();
  }
  
  @Override
  public void dispose() {
    tex.dispose();
  }
}
