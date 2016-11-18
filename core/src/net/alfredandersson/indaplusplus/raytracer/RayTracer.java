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
  
  public float[] rayTrace(int width, int height, Vector3 camPos, Vector3 forward, Vector3 up, float fov) {
    forward.nor();
    up.nor();
    
    Vector3 right = new Vector3(forward).crs(up);
    
    Vector3 current1 = new Vector3();
    Vector3 current2 = new Vector3();
    
    float invaspect = (float)((double)height / width);
    float hfov = (float)Math.toRadians(fov);
    float vfov = (float)Math.toRadians(fov * invaspect);
    
    float hinc = hfov / width;
    float hoff = hinc * 0.5f - hfov * 0.5f;
    
    float vinc = vfov / height;
    float voff = vinc * 0.5f - vfov * 0.5f;
    
    RaycastResult res = new RaycastResult();
    RaycastResult temp = new RaycastResult();
    
    Scene sc = scene;
    
    Pool<Color> colPool = new Pools.ColorPool();
    Pool<Vector3> vecPool = new Pools.VectorPool();
    
    float[] img = new float[width * height * 4];
    
    for (int y = 0, i = 0; y < height; y++) {
      current1.set(forward).rotateRad(right, voff + vinc * y);
      for (int x = 0; x < width; x++, i += 4) {
        current2.set(current1).rotateRad(up, hoff + hinc * x);
        
        sc.raycast(res, temp, camPos.x, camPos.y, camPos.z, current2.x, current2.y, current2.z);
        
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
