package hello;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

//import java.util.List;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {

	List<AccountModel> findByUserId(Long id);
}
