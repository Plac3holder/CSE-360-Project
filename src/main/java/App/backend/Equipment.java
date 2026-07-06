package App.backend;

public class Equipment {
    private String id;
    private String name;
    private String category;
    private String condition;
    private double dailyRate;
    private double deposit;
    private String ownerId;
    private String status; // AVAILABLE, RENTED, FLAGGED

    public Equipment(String id, String name, String category, String condition,
                     double dailyRate, double deposit, String ownerId, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.dailyRate = dailyRate;
        this.deposit = deposit;
        this.ownerId = ownerId;
        this.status = status;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getCondition() { return condition; }
    public double getDailyRate() { return dailyRate; }
    public double getDeposit() { return deposit; }
    public String getOwnerId() { return ownerId; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toFileString() {
        return id + "|" + name + "|" + category + "|" + condition + "|" +
               dailyRate + "|" + deposit + "|" + ownerId + "|" + status;
    }

    public static Equipment fromFileString(String line) {
        String[] p = line.split("\\|");
        return new Equipment(
                p[0], p[1], p[2], p[3],
                Double.parseDouble(p[4]),
                Double.parseDouble(p[5]),
                p[6], p[7]
        );
    }
}