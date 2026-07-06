package App.backend;

import java.io.*;
import java.util.*;

public class DataManager {
    private static final String EQUIPMENT_FILE = "equipment.txt";
    private static final String RENTALS_FILE = "rentals.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static ArrayList<Equipment> loadEquipment() {
        ArrayList<Equipment> list = new ArrayList<>();
        File file = new File(EQUIPMENT_FILE);

        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(Equipment.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading equipment: " + e.getMessage());
        }

        return list;
    }

    public static void saveEquipment(ArrayList<Equipment> equipmentList) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(EQUIPMENT_FILE))) {
            for (Equipment e : equipmentList) {
                pw.println(e.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving equipment: " + e.getMessage());
        }
    }

    public static void addEquipment(Equipment equipment) {
        ArrayList<Equipment> list = loadEquipment();
        list.add(equipment);
        saveEquipment(list);
    }

    public static void addRentalRecord(RentalRecord record) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RENTALS_FILE, true))) {
            pw.println(record.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving rental record: " + e.getMessage());
        }
    }

    public static ArrayList<RentalRecord> loadRentalRecords() {
        ArrayList<RentalRecord> list = new ArrayList<>();
        File file = new File(RENTALS_FILE);

        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(RentalRecord.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading rental records: " + e.getMessage());
        }

        return list;
    }

    public static void addTransaction(Transaction transaction) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            pw.println(transaction.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public static void checkoutEquipment(String equipmentId, String renterId, String returnDate) {
        ArrayList<Equipment> equipmentList = loadEquipment();

        for (Equipment e : equipmentList) {
            if (e.getId().equals(equipmentId) && e.getStatus().equals("AVAILABLE")) {
                e.setStatus("RENTED");

                RentalRecord record = new RentalRecord(
                        UUID.randomUUID().toString(),
                        e.getId(),
                        renterId,
                        e.getOwnerId(),
                        returnDate,
                        e.getDeposit(),
                        "ACTIVE"
                );

                Transaction transaction = new Transaction(
                        UUID.randomUUID().toString(),
                        e.getId(),
                        renterId,
                        e.getDailyRate() + e.getDeposit()
                );

                addRentalRecord(record);
                addTransaction(transaction);
                saveEquipment(equipmentList);
                return;
            }
        }
    }

    public static int getActiveRentalCount() {
        int count = 0;
        for (RentalRecord r : loadRentalRecords()) {
            if (r.getStatus().equals("ACTIVE")) count++;
        }
        return count;
    }

    public static int getAvailableEquipmentCount() {
        int count = 0;
        for (Equipment e : loadEquipment()) {
            if (e.getStatus().equals("AVAILABLE")) count++;
        }
        return count;
    }

    public static void deleteEquipment(String equipmentId) {
        ArrayList<Equipment> list = loadEquipment();
        list.removeIf(e -> e.getId().equals(equipmentId));
        saveEquipment(list);
    }

    public static void returnEquipment(String rentalId, String equipmentId) {
        // 1. Mark the rental record as returned and rewrite the text file
        ArrayList<RentalRecord> rentals = loadRentalRecords();
        for (RentalRecord r : rentals) {
            if (r.getRentalId().equals(rentalId)) {
                r.markReturned();
                break;
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(RENTALS_FILE))) {
            for (RentalRecord r : rentals) {
                pw.println(r.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving returns: " + e.getMessage());
        }

        // 2. Put the item back on the market
        ArrayList<Equipment> equipmentList = loadEquipment();
        for (Equipment e : equipmentList) {
            if (e.getId().equals(equipmentId)) {
                e.setStatus("AVAILABLE");
                break;
            }
        }
        saveEquipment(equipmentList);
    }
}