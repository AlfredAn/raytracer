package net.alfredandersson.indaplusplus.raytracer;

public final class RayTracer {
  
  private final Scene scene;
  
  public RayTracer(Scene scene) {
    this.scene = scene;
  }
  
  public float[] rayTrace(int width, int height) {
    RaycastResult res = new RaycastResult();
    RaycastResult temp = new RaycastResult();
    
    Scene sc = scene;
    
    float[] img = new float[width * height * 4];
    
    for (int y = 0, i = 0; y < height; y++) {
      for (int x = 0; x < width; x++, i += 4) {
        sc.raycast(res, temp, x, y, -1000, 0, 0, 1);
        
        if (res.isHit()) {
          img[i+0] = 1.0f;
          img[i+1] = 0.0f;
          img[i+2] = 0.0f;
          img[i+3] = 1.0f;
        } else {
          img[i+0] = 0.0f;
          img[i+1] = 1.0f;
          img[i+2] = 0.0f;
          img[i+3] = 1.0f;
        }
      }
    }
    
    return img;
  }
}
