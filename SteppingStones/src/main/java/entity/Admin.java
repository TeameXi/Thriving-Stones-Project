package entity;

public class Admin {
    private int admin_id;
    private String admin_username;
    private int branch_id;
    private String email;
    
    public Admin(){
        
    }

    public Admin(String admin_username, int branch_id){
        this.admin_username = admin_username;
        this.branch_id = branch_id;
    }
    
    public Admin(String admin_username, String email, int branch_id,int admin_id){
        this(admin_username, branch_id);
        this.email = email;
        this.admin_id = admin_id;
    }
    
    public Admin(String admin_username,String email, int branch_id){
        this(admin_username, branch_id);
        this.branch_id = branch_id;
        this.email = email;
    }
    /**
     * @return the admin_id
     */
    public int getAdmin_id() {
        return admin_id;
    }

    /**
     * @param admin_id the admin_id to set
     */
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    /**
     * @return the admin_username
     */
    public String getAdmin_username() {
        return admin_username;
    }

    /**
     * @param admin_username the admin_username to set
     */
    public void setAdmin_username(String admin_username) {
        this.admin_username = admin_username;
    }

 
    /**
     * @return the branch_id
     */
    public int getBranch_id() {
        return branch_id;
    }

    /**
     * @param branch_id the branch_id to set
     */
    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the admin_id to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}
