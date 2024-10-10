//public class junk {
//    public void setTimeFrame(int timeFrame) {
//        this.timeFrame = timeFrame;
//    }
//
//    public void colliedWithCar(Entity target){
//        if(target instanceof Damageable && !inCollision){
//            if(Utilities.checkCollision(this,(Entity) target)
//                    && checkDamageable(this, target)) {
//                inflicDamage((Damageable) this,(Damageable) target);
//                if(this.getHealth() == 0 || ((Damageable) target).getHealth() == 0){
//                    isCollied = false;
//                    inCollision = false;
//                    handleCollision(this, target);
//                }else {
//                    moveAway(this, target);
//                    this.timeFrame = 10;
//                    this.isCollied = true;
//                    //this.inCollision = true;
//                    //target.setInCollision(true);
//                }
//            }
//        }
//
//    }
//    public void countDown(){
//        if(inCollision){
//            timeFrame--;
//            if(timeFrame == 0){
//                inCollision = false;
//                isCollied = false;
//            }
//        }
//    }
//
//    public void inflicDamage(Damageable entity1, Damageable entity2){
//        double hp1 = entity1.getHealth();
//        double dmg1 = entity1.getDamage();
//        double hp2 = entity2.getHealth();
//        double dmg2 = entity2.getDamage();
//
//        entity1.setHealth(hp1 - dmg2);
//        entity2.setHealth(hp2 - dmg1);
//
//    }
//
//
//    public static void handleCollision(Entity entity1, Entity entity2) {
//        Damageable damageable1 = (Damageable) entity1;
//        Damageable damageable2 = (Damageable) entity2;
//        if(damageable1.getHealth() == 0){
//            if(entity1 instanceof Taxi){
//                entity1.render();
//            }else{ entity1.setVisible(false);}
//        }else {
//            if(entity2 instanceof  Taxi){
//                entity2.render();
//            }
//            else {
//                entity2.setVisible(false);
//            }
//        }
//    }
////        boolean isCollied = Utilities.checkCollision(entity1,entity2);
////        if(isCollied) {
////
////            if (entity1 instanceof Damageable && entity2 instanceof Damageable) {
////                Damageable damageable1 = (Damageable) entity1;
////                Damageable damageable2 = (Damageable) entity2;
////                if (checkDamageable(entity1, entity2)) {
////                    moveAway(entity1,entity2);
////                }
////            }
////        }
////
////    }
//
//
//    public static void moveAway(Entity entity1, Entity entity2){
//        int ogS1 = entity1.getSpeed();
//        int ogS2 = entity2.getSpeed();
//        if (entity1 instanceof Movable && entity2 instanceof Movable) {
//            // turn on time frame count down, and while still have time frame, move both entity by compare the
//            // entity coord
//
//            entity1.setSpeed(1);
//            entity2.setSpeed(1);
//            if (entity1.getY() > entity2.getY()) {
//                entity1.moveUp();
//                entity2.moveDown();
//            } else {
//                entity1.moveDown();
//                entity2.moveUp();
//            }
//            entity1.setSpeed(ogS1);
//            entity2.setSpeed(ogS2);
//        }
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
//}
//wat gpt saying
//private int collisionTimeout = 0; // Counter for collision frames
//
//public void hitAndMove() {
//    for (OtherCar otherCar : otherCarList) {
//        if (Utilities.checkCollision(otherCar, taxi)) {
//            // Reset collisionTimeout on collision
//            if (collisionTimeout == 0) {
//                collisionTimeout = 10; // Start the timeout counter
//            }
//
//            // Check if within the first 10 frames of collision
//            if (collisionTimeout > 0) {
//                // Move cars apart based on their positions
//                if (taxi.getY() < otherCar.getY()) {
//                    // Taxi is on top
//                    taxi.setY(taxi.getY() - 1); // Move taxi up
//                    otherCar.setY(otherCar.getY() + 1); // Move other car down
//                } else {
//                    // OtherCar is on top
//                    otherCar.setY(otherCar.getY() - 1); // Move other car up
//                    taxi.setY(taxi.getY() + 1); // Move taxi down
//                }
//
//                collisionTimeout--; // Decrement the frame counter
//            } else {
//                // Handle normal collision logic after timeout
//                otherCar.attack(taxi);
//                taxi.attack(otherCar);
//
//                // Check health status
//                if (otherCar.getHealth() <= 0) {
//                    otherCar.setVisible(false);
//                }
//            }
//        }
//    }
//    taxi.renderHealth(); // Ensure health is rendered
//}
