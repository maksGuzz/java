package hello;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionsDao;

	public List getTransactions() {
		return transactionsDao.findAll();
	}

	public TransactionModel save(TransactionModel model) {
		transactionsDao.save(model);
		return model;
	}

	public void delete(TransactionModel model) {
		transactionsDao.delete(model);		
	}
	public TransactionModel getById(Long id)
	{
		Optional<TransactionModel> op = transactionsDao.findById(id); 
		if (op.isPresent())
		{
			return op.get();
		}
		return new TransactionModel(); 
	}
}
