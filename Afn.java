import java.util.*;

public class Afn {

    public ArrayList<String> etats;
    public ArrayList<Character> alphabet;
    public ArrayList<String> etatsFinaux;
    public String etatInitial;
    public String[][] fctTransition;

    Scanner sc = new Scanner(System.in);

    public Afn() {

        alphabet = new ArrayList<Character>();
        etats = new ArrayList<String>();
        etatsFinaux = new ArrayList<String>();
    }

    public String Getetatinitial() {
        return this.etatInitial;
    }

    public void Setetatinitial(String etatI) {
        this.etatInitial = etatI;

    }

    // **********************gestion des etats ***************************//

    public void menuEtat() {
        System.out.println("choix 1: ajouter un état:    ");
        System.out.println("choix 2: afficher les etats:     ");

        System.out.println("choix 3: sortir de ce menu ");
    }

    public void ajouterEtats() {
        int n;
        String e;
        System.out.println("donnez le nombre des états S:  ");
        // ajouter exception d' int apres
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            e = "s" + i;
            etats.add(e);
        }
    }

    public void afficherEtats() {
        int taille = etats.size();
        for (int i = 0; i < taille; i++) {
            System.out.print(etats.get(i) + ", ");
        }
        System.out.println();
    }

    // **********************gestion de l'alphabet ***************************//
    public void menuAlphabet() {
        System.out.println("veuillez choisir le choix qui vous convient :");
        System.out.println("choix 1: ajouter symbole ");

        System.out.println("choix 2: afficher alphabet");
        System.out.println("choix 3: sortir de ce menu");
    }

    public void ajouterSymbole() {
        char s; // le symbole entree par l'utilisateur
        int i; //
        System.out.print("donner le symbole que vous voulez ajouter (char):   ");
        s = sc.next().charAt(0);

        if (alphabet.size() != 0) {
            i = alphabet.indexOf(s);

            if (i != -1) { // if(alphabet.contains(s)
                System.out.println("ERREUR . ce symbole existe !");
            }

            else {
                alphabet.add(new Character(s));
                System.out.println("votre symbole ajouté est :   " + s);
            }
        } else {
            alphabet.add(new Character(s));
            System.out.println("votre symbole ajouté est :   " + s);
        }
    }

    public void afficherAlphabet() {
        int size = alphabet.size();
        for (int i = 0; i < size; i++) {
            System.out.print(alphabet.get(i) + "\t");
        }
    }

    // **********************gestion des etats finaux ***************************//

    public void saisirEtatFinaux() {
        String f;
        int n;

        System.out.println(" saisir le nombre d'états finaux: ");
        n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Veuillez saisir l'état final" + (1 + i) + " : ");
            f = sc.next();
            etatsFinaux.add(f);
        }
        System.out.println();
    }

    public void afficherEtatsFinaux() {
        int taille = etatsFinaux.size();
        for (int i = 0; i < taille; i++) {
            System.out.print(etatsFinaux.get(i) + "\t");
        }
        System.out.print("\n");
    }

    // ********************** Etat Initial ***************************//

    public void afficherEtatsInitial() {
        System.out.println("l'etat initial de votre AFD est : S0 ");
    }

    // ********************** fonction de transition ***************************//
    public void remplirFonctionTrans() {
        fctTransition = new String[etats.size()][alphabet.size() + 1];
        String var;
        ArrayList<String> tab;
        int r;
        int y;
        for (int i = 0; i < etats.size(); i++) {
            String s = etats.get(i);
            for (int j = 0; j < alphabet.size() + 1; j++) {
                if (j == alphabet.size()) {
                    System.out.println("donnez le resultat de (" + s + ",'epsilon')");
                    System.out.println("choisissez soit 'aucun' ou 'un ou plusieurs etats' (0/1) :  ");
                } else {
                    char a = alphabet.get(j);
                    System.out.println("donnez le resultat de (" + s + "," + a + ")");
                    System.out.println("choisir : 'aucun' ou 'un ou plusieurs etats' (0/1) : ");
                }
                int rep = sc.nextInt();
                if (rep == 0) {
                    fctTransition[i][j] = "vide";
                }

                if (rep == 1) {
                    tab = new ArrayList<String>();
                    System.out.println("saisir le nombre d'etats");
                    r = sc.nextInt();
                    System.out.println("donnez le resultat de cette transition etat par etat :   ");
                    int l;
                    for (l = 1; l <= r; l++) {
                        System.out.print("donnez l'état " + l + " :   ");
                        do {
                            var = sc.nextLine();
                            y = etats.indexOf(var);
                        } while (y == -1);
                        tab.add(var);
                    }

                    String chaine = "{";

                    for (l = 0; l < tab.size(); l++) {
                        if (l < tab.size() - 1)
                            chaine = chaine + tab.get(l) + ",";
                        else
                            chaine = chaine + tab.get(l) + "}";
                    }
                    fctTransition[i][j] = chaine;
                }
            }
        }
    }

    public void afficherFctTrans() {
        System.out.print("\t");
        afficherAlphabet();
        System.out.print("\n");
        for (int i = 0; i < etats.size(); i++) {
            System.out.print(etats.get(i) + "\t");
            for (int j = 0; j < alphabet.size() + 1; j++) {

                System.out.print(fctTransition[i][j] + "\t");

            }
            System.out.print("\n");
        }
    }

    public boolean testEpsilon() {
        boolean test = true;
        int i, j;
        j = alphabet.size();
        for (i = 0; i < etats.size(); i++) {
            if (fctTransition[i][j] != "vide") {
                test = false;
                i = etats.size();
            }
        }
        return test;
    }

}