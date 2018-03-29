package hello;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

@UIScope
@SpringView(name = TransferView.VIEW_NAME)
public class TransferView extends VerticalLayout  implements View 
{
	private static final long serialVersionUID = -3089111062230006441L;
	public static final String VIEW_NAME = "transfer";
	public static final String SEL_ACC_FROM = "Select %s's account";
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;

	class TransferModel
	{
		public CustomerModel uidFrom = null;
		public CustomerModel uidTo = null;
		public AccountModel accFrom = null;
		public AccountModel accTo = null;
		public Double amount;
		
		public boolean valid()
		{
			return uidFrom != null && uidTo != null && accFrom != null && accTo != null && amount.doubleValue()>0;
		}
		
		public TransactionModel getModel()
		{
			if (valid())
			{
				return new TransactionModel(accFrom.getId(), accTo.getId(), amount);
			}
			return null;
		}
	}
	
	class Summary extends VerticalLayout
	{
		private Label header = new Label("Trnsaction details:");
		private Label from = new Label("");
		private Label to = new Label("");
		private Label fromA = new Label("");
		private Label toA = new Label("");
		public TextField amount = new TextField("Amount");
		public Button sendButton = new Button("Send");
		
		public Summary()
		{
			addComponents(header, from, fromA, to, toA, amount, sendButton);
			amount.setVisible(false);
			sendButton.setVisible(false);
		}
		
		public void inputAmount()
		{
			amount.setValue("");
			amount.setVisible(true);
		}
		
		public void clear()
		{
			from.setValue("");
			to.setValue("");
			fromA.setValue("");
			toA.setValue("");
			amount.setVisible(false);
			sendButton.setVisible(false);
		}
		
		// move to bunder..
		public void setFrom(CustomerModel m)
		{
			if (m != null)
			{
				from.setValue(String.format("Sender: %s %s", m.getFirstName(), m.getLastName()));
			}
		}
		
		public void setTo(CustomerModel m)
		{
			if (m != null)
			{
				to.setValue(String.format("Recipient: %s %s", m.getFirstName(), m.getLastName()));
			}
		}
		
		public void setFromA(AccountModel m)
		{
			if (m != null)
			{
				fromA.setValue(String.format("Sender Acc: %d", m.getAccountNumber()));
			}
		}
		
		public void setToA(AccountModel m)
		{
			if (m != null)
			{
				toA.setValue(String.format("Rec Acc: %d", m.getAccountNumber()));
			}
		}
	}
	
	private FilteredGrid fgFrom = new FilteredGrid();
	private FilteredGrid fgTo = new FilteredGrid();
	private Grid<CustomerModel> userFrom;
	private Grid<CustomerModel> userTo;
	private Grid<AccountModel> accountFrom = new Grid<>(AccountModel.class);
	private Grid<AccountModel> accountTo = new Grid<>(AccountModel.class);
	private Summary summary = new Summary();
	

	
	private Label title = new Label("Select Customer from");
	
	private TransferModel transfer;

	public TransferView()
	{
		userFrom = fgFrom.grid;
		userTo = fgTo.grid;
		addComponent(title);
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponents(summary, tablesRow());
		addComponent(hl);
		summary.sendButton.addClickListener(e -> {
				if (transfer.valid())
				{
					transactionService.save(transfer.getModel());
					getUI().getNavigator().navigateTo(TransactionsView.VIEW_NAME);
				}
			});
		summary.amount.addValueChangeListener(s -> {
				Double d = new Double(s.getValue());
				if (d.doubleValue() > 0)
				{
					transfer.amount = d;
					summary.sendButton.setVisible(true);
				}
			});
		userFrom.setSelectionMode(SelectionMode.SINGLE);
		userFrom.setColumns("firstName", "lastName");
		userFrom.asSingleSelect().addValueChangeListener(e -> {
			CustomerModel m  = (CustomerModel)e.getValue();
			if (m != null)
			{
				transfer.uidFrom = m;
				showAccountFromSelection();
				summary.setFrom(m);
			}
		});
		
		accountFrom.setSelectionMode(SelectionMode.SINGLE);
		accountFrom.asSingleSelect().addValueChangeListener(e -> {
			AccountModel m  = (AccountModel)e.getValue();
			if (m != null)
			{
				transfer.accFrom = m;
				showUserToSelection();
				summary.setFromA(m);
			}
		});
		
		userTo.setSelectionMode(SelectionMode.SINGLE);
		userTo.setColumns("firstName", "lastName");
		userTo.asSingleSelect().addValueChangeListener(e -> {
			CustomerModel m  = (CustomerModel)e.getValue();
			if (m != null)
			{
				transfer.uidTo = m;
				showAccountToSelection();
				summary.setTo(m);
			}
		});
		
		accountTo.setSelectionMode(SelectionMode.SINGLE);
		accountTo.asSingleSelect().addValueChangeListener(e -> {
			AccountModel m  = (AccountModel)e.getValue();
			if (m != null)
			{
				transfer.accTo = m;
				showAmountSelection();
				summary.setToA(m);
			}
		});
	}
	
	private void setTitle()
	{

	}
	
	private HorizontalLayout tablesRow()
	{
		HorizontalLayout h = new HorizontalLayout();
		h.addComponent(fgFrom);
		h.addComponent(fgTo);
		h.addComponent(accountFrom);
		h.addComponent(accountTo);
		
		return h;
	}
	
	private void showUserFromSelection()
	{
		fgFrom.setVisible(true);
		fgTo.setVisible(false);
		accountFrom.setVisible(false);
		accountTo.setVisible(false);

		userFrom.setItems(customerService.getCustomers());
		title.setValue("Select Customer from");
	}
	
	private void showAccountFromSelection()
	{
		fgFrom.setVisible(false);
		fgTo.setVisible(false);
		accountFrom.setVisible(true);
		accountTo.setVisible(false);

		accountFrom.setItems(accountService.findByUserId(transfer.uidFrom.getId()));
		title.setValue(String.format("Sender %s %s, select account", transfer.uidFrom.getFirstName(), transfer.uidFrom.getLastName()));
	}
	
	private void showUserToSelection()
	{
		fgFrom.setVisible(false);
		fgTo.setVisible(true);
		accountFrom.setVisible(false);
		accountTo.setVisible(false);
		
		userTo.setItems(customerService.getCustomers());
		title.setValue(String.format("%s %s acc %d, select Recipient", transfer.uidFrom.getFirstName(), transfer.uidFrom.getLastName(), transfer.accFrom.getAccountNumber()));
	}
	
	private void showAccountToSelection()
	{
		fgFrom.setVisible(false);
		fgTo.setVisible(false);
		accountFrom.setVisible(false);
		accountTo.setVisible(true);

		accountTo.setItems(accountService.findByUserId(transfer.uidTo.getId()));
		title.setValue(String.format("%s %s acc %d, will send to %s %s select account",
				transfer.uidFrom.getFirstName(),
				transfer.uidFrom.getLastName(),
				transfer.accFrom.getAccountNumber(),
				transfer.uidTo.getFirstName(),
				transfer.uidTo.getLastName()));
	}
	
	private void showAmountSelection()
	{
		fgFrom.setVisible(false);
		fgTo.setVisible(false);
		accountFrom.setVisible(false);
		accountTo.setVisible(false);
		title.setValue(String.format("%s %s acc %d, will send to %s %s acc %d",
				transfer.uidFrom.getFirstName(),
				transfer.uidFrom.getLastName(),
				transfer.accFrom.getAccountNumber(),
				transfer.uidTo.getFirstName(),
				transfer.uidTo.getLastName(),
				transfer.accTo.getAccountNumber()));
		summary.inputAmount();
	}
		
	@Override
	public void enter(ViewChangeEvent event)
	{
		// This view is constructed in the init() method()
		transfer = new TransferModel();
		//summary = new Summary();
		showUserFromSelection();
		summary.clear();
	}
}
