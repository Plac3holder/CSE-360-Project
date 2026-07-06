package App.backend;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String equipmentId;
    private String renterId;
    private double totalCost;
    private String timestamp;

    public Transaction(String transactionId, String equipmentId, String renterId, double totalCost) {
        this.transactionId = transactionId;
        this.equipmentId = equipmentId;
        this.renterId = renterId;
        this.totalCost = totalCost;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getTransactionId() { return transactionId; }
    public String getEquipmentId() { return equipmentId; }
    public String getRenterId() { return renterId; }
    public double getTotalCost() { return totalCost; }
    public String getTimestamp() { return timestamp; }

    public String toFileString() {
        return transactionId + "|" + equipmentId + "|" + renterId + "|" + totalCost + "|" + timestamp;
    }

    public static Transaction fromFileString(String line) {
        String[] p = line.split("\\|");
        Transaction t = new Transaction(p[0], p[1], p[2], Double.parseDouble(p[3]));
        t.timestamp = p[4]; // restore the original timestamp
        return t;
    }
}