import java.util.*;

 public class Main {

    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        Afn a1 = new Afn();
        determinisation a2 = new determinisation() ;
        Afd afd = new Afd();

        int in;
        boolean test = true;

        // ************** PREMIER MENU *********************//
        System.out.print("###############################\n");
        System.out.print("######## Programme  ###########\n");
        System.out.print("## Determinisation d'automate ##\n");
        System.out.print("###############################\n \n");
        System.out.print("######## Menu 1  ###########\n");
        a1.menuAlphabet();
        do {
            System.out.print("enter votre choix:   ");
            in = sc.nextInt();
            switch (in) {
                case 1:
                    a1.ajouterSymbole();
                    break;
                case 2:
                    a1.afficherAlphabet();
                    break;
                case 3:
                    test = false;
                    break;
            }
        } while (test == true);

        // **************DEUXIEM MENU *********************//
        System.out.print("\n \n ######## Menu 2  ###########\n");
        a1.menuEtat();
        test = true;
        do {
            System.out.print("enter votre choix:   ");
            in = sc.nextInt();
            switch (in) {
                case 1:
                    a1.ajouterEtats();
                    break;

                case 2:
                    a1.afficherEtats();
                    break;
                case 3:
                    test = false;
                    break;
            }
        } while (test == true);

        a1.saisirEtatFinaux();
        a1.afficherEtatsInitial();
        System.out.print("\n############################################\n");
        System.out.println("####### Remplir la fct de transition : #######\n");
        a1.remplirFonctionTrans();
        System.out.print("\n############################################\n");
        System.out.println("## affichage de la fct de transition de l'AFN ##\n");
        System.out.println(" ");
        a1.afficherFctTrans();

        System.out.print("\n ############################################\n");
        System.out.println("######### Table des transitions #########\n");
        a2.testEpsilonTransition(a1);
        a2.toAfd(a1);
        a2.TableTransition();// table transition

        System.out.print("\n ############################################\n");
        System.out.println("################### L'AFD : ###################\n");

        a2.afficherAFd(afd, a1);
        System.out.println("\n alphabet : \t");
        afd.afficherAlphabet();
        System.out.println("\n etats : \t");
        afd.afficherEtats();
        System.out.println("\n etats finaux : \t");
        afd.afficherEtatsFinaux();
        System.out.println("\n etats initial : \t");
        afd.afficherEtatsInitial();

        System.out.print("\n ###############################################################\n");
        System.out.println("##### affichage de la fct de transition de l'AFD  L'AFD : #####\n");
        System.out.println("");
        afd.afficherFctTrans(); // la fct de transition de l'afd

        System.out.print("\n ###############################################################\n");
        System.out.println("##### tester un mot par l'AFD : #####\n");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        String reponse;
        do {
            System.out.print("Veuillez saisir un mot : ");
            String mot = scanner.nextLine();
            boolean estAccepte = afd.accepte(mot);
            if (estAccepte) {
                System.out.println("Le mot est accepté par l'automate.");
            } else {
                System.out.println("Le mot n'est pas accepté par l'automate.");
            }
            System.out.print("Voulez-vous tester un autre mot ? (O/N) ");
            reponse = scanner.nextLine();
        } while (reponse.equalsIgnoreCase("O"));

    }

}