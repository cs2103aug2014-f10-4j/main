package list.model;

import java.awt.Color;

public interface ICategory {
    
    public Color getColor();
    
    public String getName();
    
    public ICategory setColor(Color color);
    
    public ICategory setName(String name);
    
}
