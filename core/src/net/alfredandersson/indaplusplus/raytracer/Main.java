package net.alfredandersson.indaplusplus.raytracer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
  
  private SpriteBatch sb;
  private Texture tex;
  
  @Override
  public void create() {
    Scene sc = new Scene();
    sc.add(new Sphere(200, 300, 0, 50));
    
    RayTracer rt = new RayTracer(sc);
    float[] img = rt.rayTrace(1920, 1080);
    
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
