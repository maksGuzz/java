
package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountDao;

	public List getAccounts() {
		return accountDao.findAll();
	}

	public AccountModel save(AccountModel model) {
		accountDao.save(model);
		return model;
	}
	
	public AccountModel saveWithUserId(AccountModel model, Long id) {
		model.setUserId(id);
		accountDao.save(model);
		return model;
	}

	public void delete(AccountModel model) {
		accountDao.delete(model);		
	}

	public List findByUserId(Long id)
	{
		return accountDao.findByUserId(id);
	}
}
