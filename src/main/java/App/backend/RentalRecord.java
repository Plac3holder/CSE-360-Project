package App.backend;

public class RentalRecord {
    private String rentalId;
    private String equipmentId;
    private String renterId;
    private String ownerId;
    private String expectedReturnDate;
    private double securityDepositHeld;
    private String status; // ACTIVE, DUE, RETURNED

    public RentalRecord(String rentalId, String equipmentId, String renterId,
                        String ownerId, String expectedReturnDate,
                        double securityDepositHeld, String status) {
        this.rentalId = rentalId;
        this.equipmentId = equipmentId;
        this.renterId = renterId;
        this.ownerId = ownerId;
        this.expectedReturnDate = expectedReturnDate;
        this.securityDepositHeld = securityDepositHeld;
        this.status = status;
    }

    public String getRentalId() { return rentalId; }
    public String getEquipmentId() { return equipmentId; }
    public String getRenterId() { return renterId; }
    public String getOwnerId() { return ownerId; }
    public String getExpectedReturnDate() { return expectedReturnDate; }
    public double getSecurityDepositHeld() { return securityDepositHeld; }
    public String getStatus() { return status; }

    public void markReturned() {
        this.status = "RETURNED";
    }

    public String toFileString() {
        return rentalId + "|" + equipmentId + "|" + renterId + "|" + ownerId + "|" +
                expectedReturnDate + "|" + securityDepositHeld + "|" + status;
    }

    public static RentalRecord fromFileString(String line) {
        String[] p = line.split("\\|");
        return new RentalRecord(
                p[0], p[1], p[2], p[3],
                p[4],
                Double.parseDouble(p[5]),
                p[6]
        );
    }
}