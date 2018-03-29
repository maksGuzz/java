package hello;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountModel {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	private Long userId;
	
	private Long accountNumber;
	
	private String status;
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public Long getId()
    {
    	return id;
    }
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/*public String getUserIdString()
	{
		if (userId!=null)
		{
			return userId.toString();
		}
		return new String();
	}

	public void setUserIdString(String userId) {
		this.userId = new Long(userId);
	}*/

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	/*public String getAccountNumberString()
	{
		if (accountNumber != null)
    	{
    		return accountNumber.toString();
    	}
		return new String();
	}

	public void setAccountNumberString(String accountNumber) {
		this.accountNumber = new Long(accountNumber);
	}*/
	
	@Override
    public String toString() {
        return String.format("Account[id=%d, userId='%d', accountNumber='%d', status='%s']",
                id, userId, accountNumber, status);
    }
}

