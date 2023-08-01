package com.company;
import java.util.*;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void getUserId() {
    }
}

class Reservation {
    private int pnr;
    private String trainNumber;
    private String trainName;
    private String classType;
    private Date journeyDate;
    private String from;
    private String to;

    public Reservation(int pnr, String trainNumber, String trainName, String classType, Date journeyDate, String from, String to) {
        this.pnr = pnr;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.from = from;
        this.to = to;
    }

    public int getPNR() {
        return pnr;
    }

    // Add other methods for the other fields
}

class ReservationSystem {
    private List<User> users;
    private List<Reservation> reservations;
    private List<Train> trains; // Add a list to store train information
    private int nextPNR;

    public ReservationSystem() {
        users = new ArrayList<>();
        reservations = new ArrayList<>();
        nextPNR = 78989098; // Start with a random PNR number
        trains = new ArrayList<>(); // Initialize the list for trains
    }

    public boolean addUser(String username, String password) {
        // Check if the username is unique before adding
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        return true;
    }

    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public int makeReservation(String trainNumber, String classType, Date journeyDate, String from, String to) {
        String trainName = getTrainName(trainNumber); // Implement this method to get train name from the database
        int pnr = nextPNR++;
        Reservation reservation = new Reservation(pnr, trainNumber, trainName, classType, journeyDate, from, to);
        reservations.add(reservation);
        return pnr;
    }

    class Train {
        private String trainNumber;
        private String trainName;

        public Train(String trainNumber, String trainName) {
            this.trainNumber = trainNumber;
            this.trainName = trainName;
        }

        public String getTrainNumber() {
            return trainNumber;
        }

        public String getTrainName() {
            return trainName;
        }
    }

    // Add a method to get train name on train number
    private String getTrainName(String trainNumber) {
        for (Train train : trains) {
            if (train.getTrainNumber().equals(trainNumber)) {
                return train.getTrainName();
            }
        }

        return "Train not found";
    }

    // Add a method to add train information to the system
    public void addTrain(String trainNumber, String trainName) {
        Train newTrain = new Train(trainNumber, trainName);
        trains.add(newTrain);
    }


    public Reservation getReservationDetails(int pnr) {
        for (Reservation reservation : reservations) {
            if (reservation.getPNR() == pnr) {
                return reservation;
            }
        }
        return null;
    }

    public boolean cancelReservation(int pnr) {
        Reservation reservationToRemove = null;
        for (Reservation reservation : reservations) {
            if (reservation.getPNR() == pnr) {
                reservationToRemove = reservation;
                break;
            }
        }
        if (reservationToRemove != null) {
            reservations.remove(reservationToRemove);
            return true;
        }
        return false;
    }
}



public class OnlineReservationSystem {
    public static void main(String[] args) {
        // Example usage of the ReservationSystem
        ReservationSystem reservationSystem = new ReservationSystem();
        reservationSystem.addUser("user123", "password123");

        // Simulate user login
        String username = "user123";
        String password = "password123";
        boolean isAuthenticated = reservationSystem.authenticate(username, password);

        if (isAuthenticated) {
            // Logged in successfully, make a reservation
            String trainNumber = "12345";
            String classType = "AC";
            Date journeyDate = new Date(); // You can use SimpleDateFormat to parse a date string
            String from = "Source";
            String to = "Destination";

            int pnr = reservationSystem.makeReservation(trainNumber, classType, journeyDate, from, to);
            System.out.println("Reservation successful. Your PNR is: " + pnr);

            // Example cancellation
            int pnrToCancel = 1000;
            Reservation reservationToCancel = reservationSystem.getReservationDetails(pnrToCancel);
            if (reservationToCancel != null) {
                boolean isCancelled = reservationSystem.cancelReservation(pnrToCancel);
                if (isCancelled) {
                    System.out.println("Reservation with PNR " + pnrToCancel + " has been cancelled.");
                } else {
                    System.out.println("Cancellation failed. Invalid PNR number.");
                }
            } else {
                System.out.println("Reservation with PNR " + pnrToCancel + " not found.");
            }
        } else {
            System.out.println("Invalid login credentials.");
        }
    }
}


