package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

//import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

	Collection<TransactionModel> findByAmount(Long amount);
	
	//@Query("SELECT t.amount amount, a1.userId ufrom, a2.userId utu FROM transaction_model t left join account_model a1 on a1.userId = t., account_model a2 left joun ")
    //public List<TransDescribed> find(@Param("lastName") String lastName);
}
