package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.Scene;
import net.alfredandersson.indaplusplus.raytracer.lights.Light;

public abstract class Material {
  
  protected final void calcLighting(Color result, Pool<Color> colPool, Pool<Vector3> vecPool,
          Scene scene, float xPos, float yPos, float zPos, float xNorm, float yNorm, float zNorm) {
    Color tempCol = colPool.obtain();
    Vector3 tempVec = vecPool.obtain();
    
    calcLighting(result, tempCol, tempVec, scene, xPos, yPos, zPos, xNorm, yNorm, zNorm);
    
    colPool.free(tempCol);
    vecPool.free(tempVec);
  }
  
  protected void calcLighting(Color result, Color tempCol, Vector3 tempVec, Scene scene, float xPos, float yPos, float zPos, float xNorm, float yNorm, float zNorm) {
    result.set(0, 0, 0, 1);
    
    for (int i = 0; i < scene.numLights(); i++) {
      Light light = scene.getLight(i);
      light.calc(tempCol, tempVec, xPos, yPos, zPos);
      
      float intensity = Math.max(tempVec.dot(xNorm, yNorm, zNorm), 0);
      
      result.r += tempCol.r * intensity;
      result.g += tempCol.g * intensity;
      result.b += tempCol.b * intensity;
    }
  }
  
  public abstract Color shade(Pool<Color> colPool, Pool<Vector3> vecPool, Scene scene, Color rayColor, RaycastResult raycast, int depth);
}
