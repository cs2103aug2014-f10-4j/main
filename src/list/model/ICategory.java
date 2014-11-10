//@author A0113672L
package list.model;

import java.awt.Color;
import java.util.List;

public interface ICategory extends Comparable<ICategory> {
    
    public Color getColor();
    
    public String getName();
    
    public List<ITask> getList();
    
    public ICategory setColor(Color color);
    
    public ICategory setName(String name);
    
    public void setList(List<ITask> list);    
}
