package model;
/**This class has the in house parts, it takes an integer for machine ID.*/
public class InHouse extends Part{
    private int machineId;
/**This method creates an InHouse object.
 * An InHouse object is made that takes an additional integer as a parameter.
 * @param id part ID.
 * @param name part name.
 * @param price part price.
 * @param stock part stock.
 * @param max part stock max.
 * @param min part stock min.
 * @param machineId InHouse part machine ID.*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
/**Getter for machineId.
 * Gets machineId for InHouse parts.
 * @return the machine ID.*/
    public int getMachineId() {
        return machineId;
    }
/**Setter for machineId.
 * Sets machineId for InHouse parts.
 * @param machineId the machine ID*/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
