package hello;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilteredGrid extends VerticalLayout
{
	public Grid grid = new Grid<>(CustomerModel.class);
    private final TextField firstNameFilter;
    private final TextField lastNameFilter;
    private final HorizontalLayout filterRow;

    public FilteredGrid() {
    	setMargin(false);
    	setSpacing(false);
    	firstNameFilter = new TextField();
    	firstNameFilter.setPlaceholder("Name...");
    	firstNameFilter.addValueChangeListener(this::on1FilterTextChange);
    	
    	lastNameFilter = new TextField();
    	lastNameFilter.setPlaceholder("Family...");
    	lastNameFilter.addValueChangeListener(this::on2FilterTextChange);
    	
    	filterRow = new HorizontalLayout();
    	filterRow.addComponent(firstNameFilter);
    	filterRow.addComponent(lastNameFilter);
    	
        addComponent(filterRow);
        addComponent(grid);
    }

    private void on1FilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<CustomerModel> dataProvider = (ListDataProvider<CustomerModel>) grid.getDataProvider();
        dataProvider.setFilter(CustomerModel::getFirstName, s -> caseInsensitiveContains(s, event.getValue()));
        //dataProvider.setFilter(CustomerModel::getLastName, s -> caseInsensitiveContains(s, lastNameFilter.getValue()));
    }
    
    private void on2FilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<CustomerModel> dataProvider = (ListDataProvider<CustomerModel>) grid.getDataProvider();
        dataProvider.setFilter(CustomerModel::getLastName, s -> caseInsensitiveContains(s, event.getValue()));
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }
}
