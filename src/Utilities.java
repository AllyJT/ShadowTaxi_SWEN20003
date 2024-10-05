
public class Utilities {
    public static double  getEuclideanDistance(double x, double y, double x2, double y2){
        return Math.sqrt(Math.pow(x - x2, 2)
                + Math.pow(y - y2, 2));
    }
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double getRadiusDisatance(double a, double b){
        return a + b;
    }
    public static double priorityCalculate(double ratePerY, double priority,double rate,
                                           double endY, double startY){
        return (ratePerY*(startY - endY) + priority*rate);
    }
    public double calculateFinalEarning(double earning, double penalty){
        return Math.max(earning - penalty,0);
    }

}
