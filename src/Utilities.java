
public class Utilities {;
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
    public static boolean checkCollision(Entity entity1, Entity entity2){
        double distance = getEuclideanDistance(entity1.getX(), entity1.getY(),
                entity2.getX(), entity2.getY());
        double radius = getRadiusDisatance(entity1.getRadius(), entity2.getRadius());
        return radius > distance;
    }
//    public static void handleCollision(Entity entity1, Entity entity2){
//        if(entity2 == null || entity1 == null){
//            return;
//        }
//        boolean isCollied = Utilities.checkCollsion(entity1,entity2);
//        if(!isCollied){ return;}
//
//        if(entity1 instanceof Damageable && entity2 instanceof Damageable && isCollied){
//            Damageable damageable1 = (Damageable) entity1;
//            Damageable damageable2 = (Damageable) entity2;;
//            if(checkDamageable(entity1,entity2)){
//                if(entity1 instanceof Movable && entity2 instanceof Movable ){
//                    if(entity1.getY() > entity2.getY()){
//                        entity1.moveUp();
//                        entity2.moveDown();
//                    }else {
//                        entity1.moveDown();
//                        entity2.moveUp();
//                    }
//                }
//            }
//        }
//
//    }
//    public static boolean checkDamageable(Entity attacker, Entity target){
//        if((attacker instanceof EnemyCar && target instanceof Fireball) ||
//                (attacker instanceof Fireball && target instanceof EnemyCar)){
//            return false;
//        }
//
//        if(attacker instanceof Passenger || attacker instanceof Driver){
//            return false;
//        }
//        return true;
//    }

}
