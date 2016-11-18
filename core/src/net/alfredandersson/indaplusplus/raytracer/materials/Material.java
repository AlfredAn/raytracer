package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.Scene;
import net.alfredandersson.indaplusplus.raytracer.lights.Light;

public abstract class Material {
  
  protected void calcLighting(Color result, Pool<Color> colPool, Pool<Vector3> vecPool, Scene scene, float xPos, float yPos, float zPos, float xNorm, float yNorm, float zNorm,
          Vector3 hitDir, Color diffuse, Color specular, float specExponent) {
    Color tempCol = colPool.obtain();
    Vector3 tempVec = vecPool.obtain();
    Vector3 tempVec2 = vecPool.obtain();
    
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
    
    colPool.free(tempCol);
    vecPool.free(tempVec);
    vecPool.free(tempVec2);
  }
  
  public abstract Color shade(Pool<Color> colPool, Pool<Vector3> vecPool, Scene scene, Color rayColor, RaycastResult raycast, int depth);
}
