package list;

import java.awt.Color;

public class Category implements ICategory {
	private static final String DEFAULT_NAME = "";
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static ICategory defaultCategory = null;
    
    private String name = null;
	private Color color = null;
	
	@Override
	public Color getColor() {
		if (color == null) {
		    return DEFAULT_COLOR;
		} else {
	        return color;
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ICategory setColor(Color color) {
		this.color = color;
		return this;
	}

	@Override
	public ICategory setName(String name) {
		this.name = name;
		return this;
	}
	
	public static ICategory getDefaultCategory() {
	    if (defaultCategory == null) {
	        defaultCategory = new Category().setName(DEFAULT_NAME).setColor(DEFAULT_COLOR);
	    }
	    return defaultCategory;
	}

	
	
}
