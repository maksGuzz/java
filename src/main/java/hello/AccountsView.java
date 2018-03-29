package hello;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import hello.CustomerDetailView.ChangeHandler;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@UIScope
@SpringView(name = AccountsView.VIEW_NAME)
public class AccountsView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3089111062236116441L;

	public static final String VIEW_NAME = "accounts";
	private static final String ACC_NUM_IS_REQUIRED = "Account required";

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	AccountModel accountModel;
	Long uid;
	
	Binder<AccountModel> binder = new Binder<>(AccountModel.class);

	Label username = new Label();
	Grid<AccountModel> acc = new Grid<>(AccountModel.class);
	
	TextField accNum = new TextField("Account Number");
	NativeSelect<String> status = new NativeSelect<>("Status");
	
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");
	
	TextField userId = new TextField("UserId");;
	
	CssLayout actions = new CssLayout(save, delete, cancel);
	
	@PostConstruct
	void init()
	{
		status.setItems(Stream.of(AccountStatus.values()).map(AccountStatus::name).collect(Collectors.toList()));
		status.setValue(AccountStatus.ACTIVE.name());
		addComponent(username);
		addComponent(status);
		addComponent(accNum);
		
		addComponent(acc);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);

		save.addClickListener(e -> { 
				accountService.saveWithUserId(accountModel, uid);
				getUI().getNavigator().navigateTo(CustomerView.VIEW_NAME);
			});
		cancel.addClickListener(e -> { 
			getUI().getNavigator().navigateTo(CustomerView.VIEW_NAME);
		});
		delete.addClickListener(e -> accountService.delete(accountModel));
		delete.setVisible(false);

		bindingFields();

		addComponent(actions);
		
		acc.setSelectionMode(SelectionMode.SINGLE);
		acc.asSingleSelect().addValueChangeListener(e -> {
			setModel((AccountModel)e.getValue());
			delete.setVisible(true);
			
			//sendMoney.setVisible(true);
			//SiteController c = (SiteController)getUI();
			//c.setCurrentModel(m);
		});
	}
	
	private void bindingFields() {
		binder.forField(this.accNum)
				.withNullRepresentation("")
				.withConverter(new StringToLongConverter("Numbers only"))
				//.withValidator(str -> str.length() <= 30, "30 chars MAX")
				.asRequired(ACC_NUM_IS_REQUIRED).bind(AccountModel::getAccountNumber, AccountModel::setAccountNumber);

		binder.forField(this.userId).withConverter(new StringToLongConverter("Numbers only")).bind(AccountModel::getUserId, AccountModel::setUserId);
		binder.forField(this.status).bind(AccountModel::getStatus, AccountModel::setStatus);


		binder.bindInstanceFields(this);
	}
	
	private void setModel(AccountModel m)
	{
		accountModel = m;
		binder.setBean(accountModel);
	}
	
	private void refresh()
	{
		acc.setItems(accountService.findByUserId(uid));
	}
	
	public interface ChangeHandler {
		void onChange();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> {refresh();});
		delete.addClickListener(e -> h.onChange());
	}
	
	@Override
	public void enter(ViewChangeEvent event)
	{
		SiteController c = (SiteController)getUI();
		CustomerModel m = c.getCurrentModel();

		if (m!=null)
		{
			String l = new String("User name: ") + m.getFirstName() + " " + m.getLastName();
			username.setValue(l);
			uid = m.getId();
			refresh();
		}
		setModel(new AccountModel());
		// This view is constructed in the init() method()
	}
}