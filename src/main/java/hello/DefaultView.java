package hello;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3903205444585313680L;
	public static final String VIEW_NAME = ""; //default

    @PostConstruct
    void init() {
        addComponent(new Label("Welcome page"));
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}