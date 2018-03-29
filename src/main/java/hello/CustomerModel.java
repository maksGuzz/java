package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
    
    private String address;
    
    private Long age;

    protected CustomerModel() {
    }

    public CustomerModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getAddress()
    {
    	return address;
    }
    
    public void setAddress(String address)
    {
    	this.address = address;
    }
    
    public Long getAge()
    {
    	return age;
    }
    
    public void setAge(Long age)
    {
    	this.age = age;
    }
    
    public String getAgeString()
    {
    	if (age != null)
    	{
    		return age.toString();
    	}
    	return new String();
    }
    
    public void setAgeString(String age)
    {
    	this.age = new Long(age);
    }

    public Long getId() {
        return id;
    }
    
    public String getIdString()
    {
    	if (id != null)
    	{
    		return id.toString();
    	}
        return new String();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s', age='%d', address='%s']", id,
                firstName, lastName, age, address);
    }

}
