package hello;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository contactDao;

	public List getCustomers() {
		return contactDao.findAll();
	}

	public CustomerModel save(CustomerModel model) {
		contactDao.save(model);
		return model;
	}

	public void delete(CustomerModel model) {
		contactDao.delete(model);		
	}
	public CustomerModel getById(Long id)
	{
		Optional<CustomerModel> op = contactDao.findById(id); 
		if (op.isPresent())
		{
			return op.get();
		}
		return new CustomerModel("", ""); 
	}
}
