package list;

import java.awt.Color;

public class Category implements ICategory {
<<<<<<< HEAD
	private String categoryName = null;
	private Color color = null;
	
	/**
	 * Constructor for Category object. 
	 * Sets the name of category with <code>categoryName</code>.
	 * Sets the default color for the category to black.
	 * 
	 * @param categoryName
	 */
	public Category(String categoryName) {
		this.categoryName = categoryName;
		color = Color.BLACK;
	}
	
	@Override
	public Color getColor() {
		return color;
=======
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
>>>>>>> master
	}

	@Override
	public String getName() {
<<<<<<< HEAD
		return categoryName;
=======
		return this.name;
>>>>>>> master
	}

	@Override
	public ICategory setColor(Color color) {
		this.color = color;
		return this;
	}

	@Override
<<<<<<< HEAD
	public ICategory setCategoryName(String name) {
		categoryName = name;
=======
	public ICategory setName(String name) {
		this.name = name;
>>>>>>> master
		return this;
	}
	
	public static ICategory getDefaultCategory() {
	    if (defaultCategory == null) {
	        defaultCategory = new Category().setName(DEFAULT_NAME).setColor(DEFAULT_COLOR);
	    }
	    return defaultCategory;
	}

	
	
}
