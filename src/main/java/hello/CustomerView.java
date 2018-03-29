package hello;


import java.awt.List;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.vaadin.ui.VerticalLayout;


@UIScope
@SpringView(name = CustomerView.VIEW_NAME)
public class CustomerView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3089511061636116441L;

	public static final String VIEW_NAME = "customers";

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;
	
	Button newBtn = new Button("New Customer");
	Button refreshBtn = new Button("Refresh");
	Button editButton = new Button("Edit");
	Button newAccountButton = new Button("Add Account");
	CssLayout userActions = new CssLayout(newBtn, refreshBtn, editButton, newAccountButton);
	CssLayout accountActions = new CssLayout(newAccountButton);
	FilteredGrid fgrid = new FilteredGrid();
	Grid grid;// = new Grid<>(CustomerModel.class);
	Grid grid2 = new Grid<>(AccountModel.class);
	final HorizontalLayout tables = new HorizontalLayout();
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	void init() {

		grid = fgrid.grid;
		newBtn.addClickListener(e -> {
			getUI().getNavigator().navigateTo(CustomerDetailView.VIEW_NAME);
		});
		
		refreshBtn.addClickListener(e -> refresh());
		
		editButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(CustomerDetailView.VIEW_NAME + "/edit");
		});
		
		newAccountButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(AccountsView.VIEW_NAME);
		});

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setColumns("firstName", "lastName", "address", "age");
		grid.setItems(customerService.getCustomers());
		grid.asSingleSelect().addValueChangeListener(e -> {
			CustomerModel m = (CustomerModel) e.getValue();

			grid2.setItems(accountService.findByUserId(m.getId()));
			editButton.setVisible(true);
			newAccountButton.setVisible(true);
			SiteController c = (SiteController)getUI();
			c.setCurrentModel(m);
		});
		
		grid2.setSelectionMode(SelectionMode.SINGLE);
		grid2.setColumns("accountNumber", "status");
		grid2.setItems(accountService.getAccounts());

		addComponent(tableRow());
	}
	
	private HorizontalLayout tableRow()
	{
		VerticalLayout v1 = new VerticalLayout();
		//v1.addComponent(new Label("Customers List"));
		v1.addComponent(fgrid);
		fgrid.grid = grid;
		v1.addComponent(userActions);
		
		VerticalLayout v2 = new VerticalLayout();
		v2.addComponent(new Label("Accounts List"));
		v2.addComponent(grid2);
		v2.addComponent(accountActions);
		
		HorizontalLayout h = new HorizontalLayout();
		h.addComponent(v1);
		h.addComponent(v2);
		
		return h;
	}

	public final void refresh() {
		grid.setItems(customerService.getCustomers());
		//grid2.setItems(new List());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// This view is constructed in the init() method()
		//grid.deselectAll();
		editButton.setVisible(false);
		newAccountButton.setVisible(false);
		refresh();
	}
}