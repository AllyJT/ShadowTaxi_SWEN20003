//public class Trip {
//    private Passenger passenger;
//    private TripEndFlag tripEndFlag;
//    private Car car;
//    private boolean isPickedUp;
//    private boolean isDropOff;
//
//    public Trip(Car car) {
//        this.car = car;
////        this.passenger = passenger;
////        this.tripEndFlag = passenger.getTripEndFlag();
//        this.isPickedUp = false;
//        this.isDropOff = false;
//    }
//
//    public Passenger getPassenger() {
//        return passenger;
//    }
//
//    public void setPassenger(Passenger passenger) {
//        this.passenger = passenger;
//    }
//
//    public boolean isPickedUp() {
//        return isPickedUp;
//    }
//
//    public boolean isDropOff() {
//        return isDropOff;
//    }
//
//    public void pickUpPassenger(Passenger passenger) {
//        if (car.isStopped() && !hasPassenger() &&
//                Utilities.getEuclideanDistance(car.getX(), car.getY(), passenger.getX(),
//                        passenger.getY()) <= 100) {
//            if (!passenger.isPickedUp()) {
//                if (Utilities.getEuclideanDistance(car.getX(), car.getY(), passenger.getX(), passenger.getY()) <= 1) {
//                    passenger.setPickedUp(true);
//                    passenger.render();
//                    this.passenger = passenger;
//
//                    isDropOff = false;
////                    priority = this.passenger.getPriority();
////                    expectedPay = this.passenger.getExpectedValue();
//                } else {
//                    //move the passenger if they not in car
//                    passenger.setX(passenger.getX() + Utilities.clamp(car.getX() - passenger.getX(), -1, 1));
//                    passenger.setY(passenger.getY() + Utilities.clamp(car.getY() - passenger.getY(), -1, 1));
//
//                }
//            }
//        }
//    }
//
//
//    public void dropOffPassenger(Car car){
//        if(car.isStopped() && hasPassenger()){
//            this.getPassenger().setX(car.getX());
//            this.getPassenger().setY(car.getY());
//
//            if ((Utilities.getEuclideanDistance(car.getX(), car.getY(), passenger.getTripEndFlag().getX(),
//                    passenger.getTripEndFlag().getY()) <= passenger.getTripEndFlag().getRadius() )||
//                    (passenger.getY() <= passenger.getTripEndFlag().getY())
//            ){
//                passenger.setDroppedOff(true);
//                isDropOff = true;
//                this.passenger = null;
//
//            }
//        }
//    }
//
//    /**
//     * Move the passenger to the flag when the trip ended
//     * The passenger move at speed 1 pixel
//     */
////
////    public void moveToFlag(Passenger passenger,int speedX, int speedY) {
////        if (passenger.hasTripEndFlag() && isDropOff()) {
////
////            if (Utilities.getEuclideanDistance(tripEndFlag.getX(), tripEndFlag.getY(), passenger.getX(), passenger.getY()) > 1) {
////                //move the passenger if they see flag
////                passenger.setX(passenger.getX() + Utilities.clamp(tripEndFlag.getX() - passenger.getX(), -speedX, speedX));
////                passenger.setY(passenger.getY() + Utilities.clamp(tripEndFlag.getY() - passenger.getY(), -speedY, speedY));
////                if (Utilities.getEuclideanDistance(tripEndFlag.getX(), tripEndFlag.getY(), passenger.getX(), passenger.getY()) <= 1) {
////                    tripEndFlag.setVisible(false);
////
////                }
////
////            }
////        }
////
////    }
//    //check if the taxi have passenger in it
//    public boolean hasPassenger () {
//        return passenger != null && passenger.isPickedUp();
//    }
//}