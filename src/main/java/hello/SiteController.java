package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;

import hello.CustomerRepository;

@SpringUI(path=SiteController.APP_ROOT)
@SpringViewDisplay
public class SiteController extends UI  implements ViewDisplay 
{
	private static final long serialVersionUID = 8274731018433512467L;
	static final String APP_ROOT = "/";
    static final String CONTACTS_VIEW = "Customers";
    static final String ACCOUNT_VIEW = "Accounts";
    static final String TRANSACTIONS_VIEW = "Transactions";
    static final String TRANSFER_VIEW = "Send money";

    private Panel springViewDisplay;
    private CustomerView customerView = new CustomerView();
    private CustomerDetailView customerDetailsView = new CustomerDetailView();
    private AccountsView accountsView = new AccountsView();
    private TransactionsView transactionsView = new TransactionsView();
    //private TransferView transferView = new TransferView();
    
    private CustomerModel currentModel = null;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        final CssLayout navigationButtons = new CssLayout();
        navigationButtons.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationButtons.addComponent(createNavigationButton(CONTACTS_VIEW, CustomerView.VIEW_NAME));
        //navigationButtons.addComponent(createNavigationButton(ACCOUNT_VIEW, AccountsView.VIEW_NAME));
        navigationButtons.addComponent(createNavigationButton(TRANSACTIONS_VIEW, TransactionsView.VIEW_NAME));
        navigationButtons.addComponent(createNavigationButton(TRANSFER_VIEW, TransferView.VIEW_NAME));
        root.addComponent(navigationButtons);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
        getNavigator().setErrorView(ErrorView.class);
        getNavigator().addView(CustomerView.VIEW_NAME, customerView);
        getNavigator().addView(TransactionsView.VIEW_NAME, transactionsView);
        getNavigator().addView(AccountsView.VIEW_NAME, accountsView);
        getNavigator().addView(CustomerDetailView.VIEW_NAME, customerDetailsView);
        getNavigator().addView(TransferView.VIEW_NAME, TransferView.class);
        //Presenter presenter = new Presenter(firstView, secondView);
    }

		 

    private Button createNavigationButton(String caption, final String viewName)
    {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view)
    {
        springViewDisplay.setContent((Component)view);
    }
    
    @Override
    public SiteController getUI() {
        return this;
    }
    
    public CustomerView getCustomerView() {
		return customerView;
	}

	public CustomerDetailView getCustomerDetailsView() {
		return customerDetailsView;
	}

	public AccountsView getAccountsView() {
		return accountsView;
	}

	public TransactionsView getTransactionsView() {
		return transactionsView;
	}



	public CustomerModel getCurrentModel() {
		return currentModel;
	}



	public void setCurrentModel(CustomerModel currentModel) {
		this.currentModel = currentModel;
	}
}
