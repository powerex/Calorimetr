import javafx.beans.binding.ObjectExpression;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class MaterialManager {

    private void saveToFile(String filename, List<Material> list) {
        try {
            ObjectOutputStream objstream = new ObjectOutputStream(
                    new FileOutputStream(filename));

                    /*for (Material m: list) {
                        objstream.writeObject(m);
                    }*/
            objstream.writeObject(list);


                    objstream.flush();
                    objstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(List<Material> listOfMaterials) {
        saveToFile("Materials.dat", listOfMaterials);
    }

    private List<Material> loadFromFile(String filename) {
        List<Material> read = null;
        try {
            ObjectInputStream objstream = new ObjectInputStream(
                    new FileInputStream(filename));
            read = (List<Material>)objstream.readObject();
//            System.out.println(read);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //catch (EOFException e) {}
        return read;
    }

    public List<Material> loadFromFile() {
        return loadFromFile("Materials.dat");
    }

    public static void main(String[] args) {
        LinkedList<Material> list = new LinkedList<Material>();

        list.add(new Material(1,"Water",100.0, 0.0, 2100.0, 4200.0, 1500.0, 200.0, 300.0));
        list.add(new Material(2,"Aluminium",100.0, 0.0, 2100.0, 4200.0, 1500.0, 200.0, 300.0));

        MaterialManager mm = new MaterialManager();
        mm.saveToFile(list);

        List<Material> loaded = mm.loadFromFile();
        for (Material m: loaded)
            System.out.println(m);
    }

}
