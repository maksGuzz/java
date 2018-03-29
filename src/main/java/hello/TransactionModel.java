package hello;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionModel {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	private Long date;
	
	private Long accountFrom;
	
	private Long accountTo;
	
	private Double amount;
	
	public TransactionModel(Long from, Long to, Double amount)
	{
		this.accountFrom = from;
		this.accountTo = to;
		this.amount = amount;
	}
	
	public TransactionModel()
	{}
	
	public Long getId()
    {
    	return id;
    }

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Long accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Long getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(Long accountTo) {
		this.accountTo = accountTo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
