package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import net.alfredandersson.indaplusplus.raytracer.Pools;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.Scene;

public final class FlatMaterial extends Material {
  
  public final Color diffuse = new Color();
  public final Color specular = new Color();
  public final Color reflection = new Color();
  
  public boolean enableReflection;
  
  public FlatMaterial(Color diffuse, Color specular, Color reflection) {
    this.diffuse.set(diffuse);
    this.specular.set(specular);
    this.reflection.set(reflection);
    
    enableReflection = reflection.r != 0 || reflection.g != 0 || reflection.b != 0;
  }
  
  @Override
  public Color shade(Pools pools, Scene scene, RaycastResult raycast, int depth) {
    Color result = pools.color.obtain();
    
    calcLighting(result, pools, scene,
            raycast.hit.x, raycast.hit.y, raycast.hit.z,
            raycast.norm.x, raycast.norm.y, raycast.norm.z,
            raycast.hitDir, diffuse, specular, 64.0f);
    
    if (depth > 0 && enableReflection) {
      RaycastResult newCast = pools.raycast.obtain();
      RaycastResult tempCast = pools.raycast.obtain();
      Vector3 refDir = pools.vec3.obtain();
      
      refDir.set(raycast.norm).scl(-2 * raycast.hitDir.dot(raycast.norm)).add(raycast.hitDir);
      scene.raycast(newCast, tempCast, raycast.hit.x, raycast.hit.y, raycast.hit.z, refDir.x, refDir.y, refDir.z);
      
      if (newCast.isHit()) {
        Color col = newCast.shape.material.shade(pools, scene, newCast, depth - 1);
        
        result.r += col.r * reflection.r;
        result.g += col.g * reflection.g;
        result.b += col.b * reflection.b;
        
        pools.free(col);
      }
      
      pools.free(newCast);
      pools.free(tempCast);
      pools.free(refDir);
    }
    
    return result;
  }
}
