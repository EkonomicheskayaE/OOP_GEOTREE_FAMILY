package Family;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        Human dad = new Human("Иван Иванович");
        Human mom = new Human("Мария Петровна");
        Human son = new Human("Константин Иванович");
        Human daughter = new Human("Анастасия Ивановна");
        Human son2 = new Human("Георгий Константинович");

        Tree tree = new Tree();
        tree.append(dad, son);
        tree.append(mom, son);
        tree.append(dad, daughter);
        tree.append(mom, daughter);
        tree.append(son, son2);
        

        System.out.println("Дети Ивана Ивановича" + ":" + new Research(tree).spend(dad, Relationship.parent));
        System.out.println("Дети Марии Петровны" + ":" + new Research(tree).spend(mom, Relationship.parent));
        System.out.println("Дети Константина Ивановича" + ":" + new Research(tree).spend(son, Relationship.parent));
        System.out.println("Родители Константина Ивановича" + ":" + new Research(tree).spend(son, Relationship.children));
        System.out.println("Родители Анастасии Ивановны" + ":" + new Research(tree).spend(daughter, Relationship.children));
        System.out.println("Родители Георгия Константиновича" + ":" + new Research(tree).spend(son2, Relationship.children));

        Notepad notes = new Notepad();
        notes.newFile();
        notes.currentDocument().addAllText("Иван Иванович ---сын---> Константин Иванович");
        notes.currentDocument().addAllText("Иван Иванович ---дочь---> Анастасия Ивановна");
        notes.currentDocument().addAllText("Мария Петровна ---сын---> Константин Иванович");
        notes.currentDocument().addAllText("Мария Петровна ---дочь---> Анастасия Ивановна");
        notes.currentDocument().addAllText("Константин Иванович --сын--> Георгий Константинович");
        notes.currentDocument().addAllText("Анастасия Ивановна -------> ");
        notes.currentDocument().addAllText("Георгия Константиновича -------> ");
        notes.SaveAs("family", new Txt());
    }

}

enum Relationship {
    parent, children
}


class Human {
    private String Name;

    public String getName() {
        return Name;
    }

    public Human(String Name) {
        this.Name = Name;
    }

    @Override
    public String toString() {
        return String.format("%s", this.Name);
    }
    
}

class Node {
    public Node(Human h1, Relationship r, Human h2) {
        this.h1 = h1;
        this.r = r;
        this.h2 = h2;
    }

    Human h1;
    Relationship r;
    Human h2;

    @Override
    public String toString() {
        return String.format("%s %s %s", h1, r, h2);
    }
}

class Tree {
    private ArrayList<Node> tree = new ArrayList<>();

    public ArrayList<Node> getTree() {
        return tree;
    }

    public void append(Human parent, Human children) {
        tree.add(new Node(parent, Relationship.parent, children));
        tree.add(new Node(children, Relationship.children, parent));
    }
}

class Research{
    ArrayList<Node> tree;

    public Research(Tree geoTree) {
        tree = geoTree.getTree();
    }

    public ArrayList<Human> spend(Human h, Relationship r) {
        ArrayList<Human> result = new ArrayList<>();
        for (Node t : tree) {
            if(t.h1.getName() == h.getName() && t.r == r) {
                result.add(t.h2);
            }
        }
        return result;
    }
}

class Txt extends TextFormat {

    @Override
    public void SaveAs(TextDocument document, String path) {
        try (FileWriter writer = new FileWriter(path + ".txt", false)) {
            writer.write("GeoTree\n");
            writer.write(document.getData());
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}