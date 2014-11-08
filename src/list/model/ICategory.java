package list.model;

import java.awt.Color;
import java.util.List;

public interface ICategory {
    
    public Color getColor();
    
    public String getName();
    
    public List<ITask> getList();
    
    public ICategory setColor(Color color);
    
    public ICategory setName(String name);
    
    public void setList(List<ITask> list);

	public Color getDefaultColor();

	public String getDefaultName();
    
}
