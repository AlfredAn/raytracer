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
  
  public final class Worker implements Runnable {
    
    private final int width, height;
    private final Vector3 camPos, forward, up;
    private final float fov;
    
    private volatile float[] result;
    
    private Worker(int width, int height, Vector3 camPos, Vector3 forward, Vector3 up, float fov) {
      this.width = width;
      this.height = height;
      this.camPos = camPos;
      this.forward = forward;
      this.up = up;
      this.fov = fov;
    }
    
    @Override
    public void run() {
      result = rayTrace(width, height, camPos, forward, up, fov);
    }
    
    public boolean isDone() {
      return result != null;
    }
    
    public float[] getResult() {
      return result;
    }
  }
  
  public Worker rayTraceAsync(int width, int height, Vector3 camPos, Vector3 forward, Vector3 up, float fov) {
    Worker w = new Worker(width, height, camPos, forward, up, fov);
    Thread t = new Thread(w, w.toString());
    t.setDaemon(true);
    
    t.start();
    
    return w;
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
    
    Utilities pools = new Utilities();
    
    float[] img = new float[width * height * 4];
    
    Vector3 rayDir = new Vector3();
    
    float xinc = (float)(2.0 / width);
    float yinc = (float)(2.0 / height);
    
    float[] dx = {
      xinc * 0.2f, xinc * 0.4f, xinc * 0.6f, xinc * 0.8f,
      xinc * 0.2f, xinc * 0.4f, xinc * 0.6f, xinc * 0.8f,
      xinc * 0.2f, xinc * 0.4f, xinc * 0.6f, xinc * 0.8f,
      xinc * 0.2f, xinc * 0.4f, xinc * 0.6f, xinc * 0.8f};
    float[] dy = {
      yinc * 0.2f, yinc * 0.2f, yinc * 0.2f, yinc * 0.2f,
      yinc * 0.4f, yinc * 0.4f, yinc * 0.4f, yinc * 0.4f,
      yinc * 0.6f, yinc * 0.6f, yinc * 0.6f, yinc * 0.6f,
      yinc * 0.8f, yinc * 0.8f, yinc * 0.8f, yinc * 0.8f};
    
    for (int y = 0, i = 0; y < height; y++) {
      float yy = y * yinc - 1.0f;
      System.out.println("y=" + y);
      for (int x = 0; x < width; x++, i += 4) {
        float xx = x * xinc - 1.0f;
        
        for (int j = 0; j < 16; j++) {
          rayDir.set(xx + dx[j], yy + dy[j], 0).mul(mat).nor();
          
          sc.raycast(res, temp, camPos.x, camPos.y, camPos.z, rayDir.x, rayDir.y, rayDir.z);
          
          if (res.isHit()) {
            Color col = res.shape.material.shade(pools, scene, res, 7, 5);
            
            img[i+0] += col.r / 128;
            img[i+1] += col.g / 128;
            img[i+2] += col.b / 128;
            
            pools.free(col);
          }
          img[i+3] = 1.0f;
        }
      }
    }
    
    return img;
  }
}
