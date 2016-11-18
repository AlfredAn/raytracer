package net.alfredandersson.indaplusplus.raytracer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public final class RayTracer {
  
  private final Scene scene;
  
  public RayTracer(Scene scene) {
    this.scene = scene;
  }
  
  public float[] rayTrace(int width, int height, Vector3 camPos, Vector3 forward, Vector3 up, float fov) {
    PerspectiveCamera cam = new PerspectiveCamera(fov, width, height);
    cam.position.set(0, 0, 0);
    cam.direction.set(forward);
    cam.up.set(up);
    cam.update();
    Matrix4 mat = cam.combined.inv();
    
    RaycastResult res = new RaycastResult();
    RaycastResult temp = new RaycastResult();
    
    Scene sc = scene;
    
    Pools pools = new Pools();
    
    float[] img = new float[width * height * 4];
    
    Vector3 rayDir = new Vector3();
    
    float xinc = (float)(2.0 / width);
    float yinc = (float)(2.0 / height);
    for (int y = 0, i = 0; y < height; y++) {
      float yy = y * yinc - 1.0f;
      for (int x = 0; x < width; x++, i += 4) {
        float xx = x * xinc - 1.0f;
        rayDir.set(xx, yy, 0).mul(mat).nor();
        
        sc.raycast(res, temp, camPos.x, camPos.y, camPos.z, rayDir.x, rayDir.y, rayDir.z);
        
        if (res.isHit()) {
          Color col = res.shape.material.shade(pools, scene, res, 10);
          
          img[i+0] = col.r;
          img[i+1] = col.g;
          img[i+2] = col.b;
          
          pools.free(col);
        }
        img[i+3] = 1.0f;
      }
    }
    
    return img;
  }
}
