package net.alfredandersson.indaplusplus.raytracer;

public final class SRGB {
  
  private SRGB() {}
  
  private static final double
          ONE_DIV_12P92 = 1.0 / 12.92,
          ONE_DIV_1P055 = 1.0 / 1.055,
          ONE_DIV_2P4 = 1.0 / 2.4;
  
  public static double toLinear(double s) {
    if (s <= 0.0404482362771082) {
      return s * ONE_DIV_12P92;
    } else {
      return Math.pow((s + 0.055) * ONE_DIV_1P055, 2.4);
    }
  }
  
  public static double toSRGB(double l) {
    if (l <= 0.00313066844250063) {
      return l * 12.92;
    } else {
      return 1.055 * Math.pow(l, ONE_DIV_2P4) - 0.055;
    }
  }
}
