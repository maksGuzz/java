package hello;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@UIScope
@SpringView(name = TransactionsView.VIEW_NAME)
public class TransactionsView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3089511062236116441L;

	public static final String VIEW_NAME = "transactions";

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	Binder<TransactionModel> binder = new Binder<>(TransactionModel.class);

	Label username = new Label();
	Grid<TransactionModel> transactions = new Grid<>(TransactionModel.class);

	Button create = new Button("Create");
	CssLayout actions = new CssLayout(create);

	@PostConstruct
	void init()
	{
		addComponents(new Label("Transactions"),transactions);
		
		
		transactions.setSelectionMode(SelectionMode.SINGLE);
		transactions.setColumns("accountFrom", "accountTo", "amount", "id");
		transactions.asSingleSelect().addValueChangeListener(e -> {
			// ?
		});
		
	}
	
	private void refresh()
	{
		transactions.setItems(transactionService.getTransactions());
	}
	
	@Override
	public void enter(ViewChangeEvent event)
	{
		// This view is constructed in the init() method()
		refresh();
	}
}