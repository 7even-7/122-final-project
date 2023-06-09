import java.util.ArrayList;
import java.awt.*;

public class ColorData {
    private ArrayList<Color> colors;

    public ColorData() {
        colors = new ArrayList<Color>();
        colors.add(Color.MAGENTA);
        colors.add(Color.white);
        colors.add(Color.pink);
        colors.add(Color.yellow);
        colors.add(Color.pink);
        colors.add(Color.green);
        colors.add(Color.blue);
        colors.add(Color.orange);
    }

    public ArrayList<Color> getColors() {
        return this.colors;
    }
}
