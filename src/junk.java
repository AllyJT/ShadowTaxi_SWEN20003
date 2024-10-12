public class junk {
    //    public void enemyHit() {
//        // Check for collisions and initiate countdown
//        if (!isCollided2 && !isCollided) {
//            for (EnemyCar enemyCar : enemyCarList) {
//                if (Utilities.checkCollision(enemyCar, taxi)) {
//                    isCollided2 = true;
//                    countDown = 10; // Set the countdown
//                    currentCollidedEnemy = enemyCar;
//                    break; // Exit after the first collision
//                }
//            }
//        }
//
//        // Handle movement and countdown logic if collided
//        if (isCollided2 && !isCollided) {
//            // Display the countdown
//            font.drawString(String.valueOf(countDown), 500, 500);
//
//            // Move the cars based on their vertical positions
//            if (currentCollidedEnemy != null) {
//                if (taxi.getY() < currentCollidedEnemy.getY() && countDown > 0) {
//                    taxi.moveUp(); // Taxi moves up
//                    //currentCollidedCar.moveDown();
//                } else if (taxi.getY() >= currentCollidedEnemy.getY() && countDown > 0) {
//                    taxi.moveDown(); // Taxi moves down
//                    //currentCollidedCar.moveUp(); // Other car moves up
//                }
//            }
//
//            // Apply damage during collision
//            currentCollidedEnemy.attack(taxi);
//
//            // Decrement the countdown
//            countDown--;
//
//            // Reset collision state after countdown
//            if (countDown <= 0) {
//                isCollided2 = false; // Reset collision state
//                currentCollidedEnemy = null; // Clear the reference
//            }
//
//        }
//    }
}
