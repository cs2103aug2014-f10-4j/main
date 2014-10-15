package list;

import java.awt.Color;

interface ICategory {
    
    Color getColor();
    
    String getCategoryName();
    
    ICategory setColor(Color color);
    
    ICategory setCategoryName(String name);
    
}
