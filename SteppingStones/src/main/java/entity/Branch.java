package entity;

public class Branch {
    private int branchId;
    private String name;
    private String startDate;
    private String address;
    private int phone;
    
    
    public Branch(){
        
    }
    
    
    public Branch(int branchId,String name){
        this.branchId = branchId;
        this.name = name;
    }
    
    public Branch(String branchName, String startDate, String branchAddress, int phoneNo){
        this.name = branchName;
        this.startDate = startDate;
        this.address = branchAddress;
        this.phone = phoneNo;
    }
    
    public Branch(int branchID, String branchName, String startDate, String branchAddress, int phoneNo){
        this.branchId = branchID;
        this.name = branchName;
        this.startDate = startDate;
        this.address = branchAddress;
        this.phone = phoneNo;
    }

    /**
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public int getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }
    
    /**
     * @return the phone
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param phone the phone to set
     */
    public void setStartDate(String newStartDate) {
        this.startDate = newStartDate;
    }
}
