package list;

import java.awt.Color;

public class Category implements ICategory {
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
	}

	@Override
	public String getName() {
		return categoryName;
	}

	@Override
	public ICategory setColor(Color color) {
		this.color = color;
		return this;
	}

	@Override
	public ICategory setCategoryName(String name) {
		categoryName = name;
		return this;
	}

	
	
}
