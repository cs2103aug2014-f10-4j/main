package list;

import java.awt.Color;

interface ICategory {
    
    Color getColor();
    
    String getName();
    
    ICategory setColor(Color color);
    
    ICategory setCategoryName(String name);
    
}
