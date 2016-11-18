package net.alfredandersson.indaplusplus.raytracer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public final class RayTracer {
  
  public final Matrix4 projection = new Matrix4();
  private final Scene scene;
  
  public RayTracer(Scene scene) {
    this.scene = scene;
  }
  
  public float[] rayTrace(int width, int height) {
    RaycastResult res = new RaycastResult();
    RaycastResult temp = new RaycastResult();
    
    Scene sc = scene;
    
    Pool<Color> colPool = new Pools.ColorPool();
    Pool<Vector3> vecPool = new Pools.VectorPool();
    
    float[] img = new float[width * height * 4];
    
    for (int y = 0, i = 0; y < height; y++) {
      for (int x = 0; x < width; x++, i += 4) {
        sc.raycast(res, temp, x, y, -1000, 0, 0, 1);
        
        if (res.isHit()) {
          Color col = res.shape.material.shade(colPool, vecPool, scene, Color.WHITE, res, 0);
          
          img[i+0] = col.r;
          img[i+1] = col.g;
          img[i+2] = col.b;
          
          colPool.free(col);
        }
        img[i+3] = 1.0f;
      }
    }
    
    return img;
  }
}
