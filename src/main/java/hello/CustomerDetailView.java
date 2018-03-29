package hello;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
//import com.vaadin.data.validator.EmailValidator;
//import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


//@SpringComponent
@UIScope
@SpringView(name = CustomerDetailView.VIEW_NAME)
public class CustomerDetailView extends VerticalLayout  implements View
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static final String VIEW_NAME = "customDetailView"; 
	private static final long serialVersionUID = -3307156756600939660L;
	private static final String MAX_LENGTH_OF_NAME_IS_30_CHARS = "Max length of name is 30 chars";
	private static final String LAST_NAME_IS_REQUIRED = "Every contact must have a last name";
	private static final String FIRST_NAME_IS_REQUIRED = "Every contact must have a first name";

	private static final String OUTLINED = "outlined";

	@Autowired
	private CustomerService service;
	
	private CustomerModel contact;

	Binder<CustomerModel> binder = new Binder<>(CustomerModel.class);

	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField age = new TextField("Age");
	TextArea address = new TextArea("Address");
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");
	CssLayout actions = new CssLayout(save, delete, cancel);
	

	@Autowired
	public CustomerDetailView() {

		address.setSizeFull();

		addComponents(nameRow(), address);
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.addClickListener(e -> {
			service.save(contact);
			getUI().getNavigator().navigateTo(CustomerView.VIEW_NAME);
		});
		delete.addClickListener(e -> {
			service.delete(contact);
			getUI().getNavigator().navigateTo(CustomerView.VIEW_NAME);
		});
		cancel.addClickListener(e -> {
			getUI().getNavigator().navigateTo(CustomerView.VIEW_NAME);
		});

		bindingFields();

		addComponent(actions);
		
		
	}

	private void bindingFields() {
		binder.forField(this.firstName).withNullRepresentation("")
				.withValidator(str -> str.length() <= 30, MAX_LENGTH_OF_NAME_IS_30_CHARS)
				.asRequired(FIRST_NAME_IS_REQUIRED).bind(CustomerModel::getFirstName, CustomerModel::setFirstName);

		binder.forField(this.lastName).withNullRepresentation("")
				.withValidator(str -> str.length() <= 30, MAX_LENGTH_OF_NAME_IS_30_CHARS)
				.asRequired(LAST_NAME_IS_REQUIRED).bind(CustomerModel::getLastName, CustomerModel::setLastName);
		
		binder.forField(this.address).bind(CustomerModel::getAddress, CustomerModel::setAddress);
		binder.forField(this.age).withConverter(new StringToLongConverter("Numbers only"))
			.bind(CustomerModel::getAge, CustomerModel::setAge);

		binder.bindInstanceFields(this);
	}

	private HorizontalLayout nameRow() {
		HorizontalLayout sample = new HorizontalLayout();
		sample.addStyleName(OUTLINED);
		sample.setSpacing(false);
		sample.setMargin(false);
		sample.setSizeFull();

		sample.addComponents(firstName, lastName, age);
		return sample;
	}
	
	public final void showDetail(CustomerModel c) {
		contact = c;
		binder.setBean(contact);
	}
	
	public interface ChangeHandler {
		void onChange();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		String param = event.getParameters();
		if (param.contentEquals("edit"))
		{
			SiteController c = (SiteController)getUI();
			CustomerModel m = c.getCurrentModel();
			
			if (m!=null)
			{
				showDetail(m);
				return;
			}
		}
		showDetail(new CustomerModel());
	}
}