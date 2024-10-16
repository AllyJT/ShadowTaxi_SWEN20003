
public class Utilities {;

    /**
     * calculate the euclidean distance
     * @param x x coord
     * @param y y coord
     * @param x2 x coord of the other entity
     * @param y2 y coord  of the other entity
     * @return the distance
     */
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

    /**
     * calcutate the pay
     * @param ratePerY rate per distance y
     * @param priority priority of the passenger
     * @param rate rate of that priority
     * @param distance distance y between passenger and flag
     * @return
     */
    public static double priorityCalculate(double ratePerY, double priority,double rate,
                                           double distance){
        return (ratePerY*(distance) + priority*rate);
    }
    public double calculateFinalEarning(double earning, double penalty){
        return Math.max(earning - penalty,0);
    }

    /**
     * check if two entity is colliding
     * @param entity1 entity 1
     * @param entity2 entity 2
     * @return return if is collied or not
     */
    public static boolean checkCollision(Entity entity1, Entity entity2){
        double distance = getEuclideanDistance(entity1.getX(), entity1.getY(),
                entity2.getX(), entity2.getY());
        double radius = getRadiusDisatance(entity1.getRadius(), entity2.getRadius());
        return radius > distance;
    }

}
