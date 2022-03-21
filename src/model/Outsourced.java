package model;
/**This class has the outsourced parts, it takes a string for company name.*/
public class Outsourced extends Part{
    private String companyName;
/**This method creates an Outsourced object.
 * An outsourced object is made that takes an additional string as a parameter.
 * @param id part ID.
 * @param name part name.
 * @param price part price.
 * @param stock part stock.
 * @param max part stock max.
 * @param min part stock min.
 * @param companyName Outsourced part company name.*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
/**Getter for company name.
 * Gets company name for outsourced parts.
 * @return the company name.*/
    public String getCompanyName() {
        return companyName;
    }
/**Setter for company name.
 * Sets the company name for outsourced parts.
 * @param companyName the company name.*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
