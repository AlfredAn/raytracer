package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.Scene;

public final class FlatMaterial extends Material {
  
  public final Color diffuse = new Color();
  
  public FlatMaterial(Color diffuse) {
    this.diffuse.set(diffuse);
  }
  
  @Override
  public Color shade(Pool<Color> colPool, Pool<Vector3> vecPool, Scene scene, Color rayColor, RaycastResult raycast, int depth) {
    Color result = colPool.obtain();
    
    calcLighting(result, colPool, vecPool, scene,
            raycast.hit.x, raycast.hit.y, raycast.hit.z,
            raycast.norm.x, raycast.norm.y, raycast.norm.z);
    result.set(rayColor.r * result.r * diffuse.r,
               rayColor.g * result.g * diffuse.g,
               rayColor.b * result.b * diffuse.b,
               1.0f);
    
    return result;
  }
}
