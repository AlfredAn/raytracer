package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;

public final class FlatMaterial implements Material {
  
  public final Color diffuse = new Color();
  
  public FlatMaterial(Color diffuse) {
    this.diffuse.set(diffuse);
  }
  
  @Override
  public Color shade(Pool<Color> resultPool, Color rayColor, RaycastResult raycast, int depth) {
    Color result = resultPool.obtain();
    
    //result.set(rayColor.r * diffuse.r, rayColor.g * diffuse.g, rayColor.b * diffuse.b, 1.0f);
    result.set(Color.BLUE);
    
    return result;
  }
}
