package list;

import java.awt.Color;

public class Category implements ICategory {
	private String mCategoryName = null;
	private Color mColor = null;
	
	
	@Override
	public Color getColor() {
		return mColor;
	}

	@Override
	public String getCategoryName() {
		return mCategoryName;
	}

	@Override
	public ICategory setColor(Color color) {
		mColor = color;
		return this;
	}

	@Override
	public ICategory setCategoryName(String name) {
		mCategoryName = name;
		return this;
	}

	
	
}
