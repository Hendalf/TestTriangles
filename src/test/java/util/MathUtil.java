package util;

public class MathUtil {
    public static double getAreaOfTriangle(float a, float b, float c){
            float s=(a+b+c)/2;
            double  area=Math.sqrt(s*(s-a)*(s-b)*(s-c));
            return  area;
    }
}
