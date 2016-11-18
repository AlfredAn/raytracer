package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import net.alfredandersson.indaplusplus.raytracer.Pools;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.Scene;
import net.alfredandersson.indaplusplus.raytracer.lights.Light;

public abstract class Material {
  
  protected void calcLighting(Color result, Pools pools, Scene scene, float xPos, float yPos, float zPos, float xNorm, float yNorm, float zNorm,
          Vector3 hitDir, Color diffuse, Color specular, float specExponent) {
    Color tempCol = pools.color.obtain();
    Vector3 tempVec = pools.vec3.obtain();
    Vector3 tempVec2 = pools.vec3.obtain();
    
    result.set(0, 0, 0, 1);
    
    for (int i = 0; i < scene.numLights(); i++) {
      Light light = scene.getLight(i);
      light.calc(tempCol, tempVec, xPos, yPos, zPos);
      
      float intensity = Math.max(tempVec.dot(xNorm, yNorm, zNorm), 0);
      tempVec2.set(xNorm, yNorm, zNorm).scl(-2 * hitDir.dot(xNorm, yNorm, zNorm)).add(hitDir);
      float specFactor = (float)Math.pow(Math.max(tempVec2.dot(tempVec), 0), specExponent);
      
      result.r += tempCol.r * (diffuse.r * intensity + specular.r * specFactor);
      result.g += tempCol.g * (diffuse.g * intensity + specular.g * specFactor);
      result.b += tempCol.b * (diffuse.b * intensity + specular.b * specFactor);
    }
    
    pools.free(tempCol);
    pools.free(tempVec);
    pools.free(tempVec2);
  }
  
  public abstract Color shade(Pools pools, Scene scene, RaycastResult raycast, int depth);
}
