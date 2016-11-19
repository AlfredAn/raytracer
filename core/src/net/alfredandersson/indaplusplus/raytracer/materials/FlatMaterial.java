package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import net.alfredandersson.indaplusplus.raytracer.Utilities;
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
  public Color shade(Utilities pools, Scene scene, RaycastResult raycast, int reflectionDepth, int diffuseDepth) {
    Color result = pools.color.obtain();
    
    calcLighting(result, pools, scene,
            raycast.hit.x, raycast.hit.y, raycast.hit.z,
            raycast.norm.x, raycast.norm.y, raycast.norm.z,
            raycast.hitDir, diffuse, specular, 64.0f);
    
    RaycastResult newCast = pools.raycast.obtain();
    RaycastResult tempCast = pools.raycast.obtain();
    Vector3 tempVec = pools.vec3.obtain();
    
    if (reflectionDepth > 0 && enableReflection) {
      tempVec.set(raycast.norm).scl(-2 * raycast.hitDir.dot(raycast.norm)).add(raycast.hitDir); // reflection dir
      scene.raycast(newCast, tempCast, raycast.hit.x, raycast.hit.y, raycast.hit.z, tempVec.x, tempVec.y, tempVec.z);
      
      if (newCast.isHit()) {
        Color col = newCast.shape.material.shade(pools, scene, newCast, reflectionDepth - 1, diffuseDepth - 1);
        
        result.r += col.r * reflection.r;
        result.g += col.g * reflection.g;
        result.b += col.b * reflection.b;
        
        pools.free(col);
      }
    }
    
    if (diffuseDepth > 0) {
      tempVec.set((float)pools.rand.nextGaussian(), (float)pools.rand.nextGaussian(), (float)pools.rand.nextGaussian());
      tempVec.nor();
      if (tempVec.dot(raycast.norm) < 0) {
        tempVec.scl(-1);
      }
      
      scene.raycast(newCast, tempCast, raycast.hit.x, raycast.hit.y, raycast.hit.z, tempVec.x, tempVec.y, tempVec.z);
      
      if (newCast.isHit()) {
        Color col = newCast.shape.material.shade(pools, scene, newCast, reflectionDepth - 1, diffuseDepth - 1);
        
        result.r += col.r * diffuse.r;
        result.g += col.g * diffuse.g;
        result.b += col.b * diffuse.b;
        
        pools.free(col);
      }
    }
    
    pools.free(newCast);
    pools.free(tempCast);
    pools.free(tempVec);
    
    return result;
  }
}
