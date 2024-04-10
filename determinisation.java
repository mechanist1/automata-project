import java.util.*;



public class determinisation {

    private int t;
    private int varVide;
    private String tab[][];
    private String ftab[][];
    private int ligneGlobale;
    private int ligne;
    private int col;
    private int ligneTest;
    private int colTest;
    private  String fn[];




    public void testEpsilonTransition(Afn afn){
        if (afn.testEpsilon()==true)
            t=0; //pas epsilon transition

        else
            t=1;
    }
    public void TableTransition(){
        int i,j;
        for(i=0;i<ftab.length;i++){
            for(j=0;j<ftab[0].length;j++){
                System.out.print(ftab[i][j]+"\t \t ");
            }
            System.out.println("");
        }
    }

    public void afficherAFd(Afd afd,Afn afn){
        int i;
        int taille =afn.alphabet.size();
        for( i=0;i<taille;i++){
            afd.alphabet.add(afn.alphabet.get(i));
        }
        int kk=1;
        do{
            afd.etats.add(ftab[kk][0]);
            kk++;
        }while(kk<ftab.length) ;

        afd.etatInitial=ftab[2][0];

        for(int k=0;k<afn.etatsFinaux.size();k++){
            String et=afn.etatsFinaux.get(k);
            for(int kl=2;kl<ftab.length;kl++){
                if(ftab[kl][0].contains(et)==true)  afd.etatsFinaux.add(ftab[kl][0]);
            }
        }
        afd.fctTransition=new String[afd.etats.size()][afd.alphabet.size()];
        int po=0;int pi;
        for(int mp=1;mp<ftab.length;mp++){
            pi=0;
            for(int mo=1;mo<ftab[mp].length;mo++){
                afd.fctTransition[po][pi]=ftab[mp][mo] ;
                pi++;
            }
            po++;
        }
    }


    public void toAfd(Afn afn){
        varVide=0;
        ArrayList <String> temp; // temporary list of states

        // Check si il y a une epsilon transition
        for(int e=0;e<afn.etats.size();e++){
            for(int a=0;a<afn.alphabet.size();a++){
                if (afn.fctTransition[e][a]=="vide")
                    varVide=1;
                a=afn.alphabet.size(); //quitter la 2eme boucle
            }
            e=afn.etats.size();  //quitter la boucle
        }
        // pour la 1ere fois (t=0), générer la table de transition
        if(t== 0){

            tab=new String[100][afn.alphabet.size()+1]; // Initialiser la table de transition


            for(int i=0;i<100;i++){
                for(int j=0;j<afn.alphabet.size()+1;j++){
                    tab[i][j]="ftab";}
            }
            tab[0][0]=" ";
            for(int i=1;i<=afn.alphabet.size();i++)
                tab[0][i]=afn.alphabet.get(i-1).toString();
            for(int i=0;i<afn.alphabet.size()+1;i++)
                tab[1][i]="vide";

            // Initialiser la 3eme ligne de la transition table avec l'etat s0
            tab[2][0]="{s0}";
            ligneTest=2;
            colTest=1;
            ligne=2;
            col=1;
            // Fill the first row of the transition table with the transitions from the starting state {s0}.
            for(int j=0;j<afn.alphabet.size();j++){
                tab[ligne][col]=afn.fctTransition[0][j];
                col++;
            }
            ligne++;col=0;
            ligneGlobale=3;

            remplirligne(afn.alphabet.size(), afn);
            while(tab[ligneGlobale][0]!="ftab"){
                temp = new ArrayList<String> ();
                for(int l=0;l<afn.etats.size();l++){
                    String c=afn.etats.get(l);
                    if(tab[ligneGlobale][0].contains(c)==true){
                        temp.add(c);
                    }
                }

                remplircolonne(temp,afn);
                remplirligne(afn.alphabet.size(), afn);
            }


            ftab= new String[ligneGlobale][afn.alphabet.size()+1];
            for (int j=0;j<ligneGlobale;j++){
                for(int k=0;k<afn.alphabet.size()+1;k++){
                    ftab[j][k]=tab[j][k];
                }
            }

        }
        else{
            fn =new String[afn.etats.size()];
            for(int i=0;i<afn.etats.size();i++){
                String e=afn.etats.get(i);
                fn[i]=e;
                if(afn.fctTransition[i][afn.alphabet.size()]!="vide"){
                    String z=afn.fctTransition[i][afn.alphabet.size()];
                    fn[i]+=z;         }
            }

            for(int j=0;j<afn.etats.size();j++){
                for (int l=0;l<afn.etats.size();l++){
                    if ( l!=j){
                        if(fn[l].contains(afn.etats.get(j))){
                            fn[l]+=fn[j];
                        }

                    }
                }
            }
            int et=0;
            do{

                ArrayList <String> t=new ArrayList <String> ();
                for(int l=0;l<afn.etats.size();l++){
                    String c=afn.etats.get(l);
                    if(fn[et].contains(c)==true){
                        t.add(c);
                    }
                }
                String chaine="{";
                for(int l=0;l<t.size();l++){
                    chaine=chaine+t.get(l);
                    if(l!=t.size()-1)
                        chaine=chaine+",";
                    else
                        chaine=chaine+"}";
                }

                fn[et]=chaine;
                et++;
            }while( et<afn.etats.size());

            tab=new String[100][afn.alphabet.size()+1];
            for(int i=0;i<100;i++){
                tab[i][0]="ftab";}
            tab[0][0]=" ";
            for (int z=1;z<=afn.alphabet.size();z++)
                tab[0][z]=afn.alphabet.get(z-1).toString();
            if(varVide==1 || varVide==0 ){for (int i=0;i<=afn.alphabet.size();i++)
                tab[1][i]="vide";}
            tab[2][0]=fn[0];
            ligneTest=2;
            colTest=1;
            ligne=2;
            col=1;
            ligneGlobale=2;

            while(tab[ligneGlobale][0]!="ftab"){


                temp=new ArrayList <String> ();

                for (int l=0;l<afn.etats.size();l++){
                    String c=afn.etats.get(l);

                    if(tab[ligneGlobale][0].contains(c)==true){
                        temp.add(c);
                    }
                }
                remplircolonne(temp,afn);
                if(ligneGlobale==3)ligne++;
                remplirligne(afn.alphabet.size(), afn);
                if(tab[ligneGlobale][0]=="ftab"){break;}
            }
            ftab= new String[ligneGlobale][afn.alphabet.size()+1];
            for (int j=0;j<ligneGlobale;j++){
                for(int k=0;k<afn.alphabet.size()+1;k++){
                    ftab[j][k]=tab[j][k];
                }
            }
        }

    }



    public void remplircolonne(ArrayList <String> tem,Afn af){
        ArrayList <String> tp;
        col=1;
        int j,i;
        if(tem.size()==1  && t==0){
            for( j=0;j<af.alphabet.size();j++){
                String  a= tem.get(0).substring(1,2);
                int z= Integer.parseInt(a);
                tab[ligneGlobale][col]=af.fctTransition[z][j];
                col++;
            }ligneGlobale++;
            col=0;
        }
        else{

            for( j=0;j<af.alphabet.size();j++){
                for( i=0;i<tem.size();i++){
                    String  a= tem.get(i).substring(1,2);
                    int z= Integer.parseInt(a);
                    if(i==0){
                        tab[ligneGlobale][col]=af.fctTransition[z][j];
                    }
                    else{
                        tab[ligneGlobale][col]+=af.fctTransition[z][j];
                    }
                }
                boolean testVide=true;
                for(int l=0;l<af.etats.size();l++){
                    String c=af.etats.get(l);
                    if(tab[ligneGlobale][col].contains(c)==true){
                        testVide=false;l=af.etats.size();
                    }
                }
                if(testVide==true) tab[ligneGlobale][col] ="vide";
                if(tab[ligneGlobale][col] != "vide"){
                    if(t==1){
                        for(int l=0;l<af.etats.size();l++){
                            String c=af.etats.get(l);
                            if(tab[ligneGlobale][col].contains(c)==true){
                                tab[ligneGlobale][col]+=fn[l];

                            }
                        }
                    }

                    tp=new ArrayList <String> ();
                    for(int l=0;l<af.etats.size();l++){
                        String c=af.etats.get(l);
                        if(tab[ligneGlobale][col].contains(c)==true){
                            tp.add(c);
                        }
                    }
                    String chaine="{";
                    for(int l=0;l<tp.size();l++){
                        chaine=chaine+tp.get(l);
                        if(l!=tp.size()-1)
                            chaine=chaine+",";
                        else
                            chaine=chaine+"}";
                    }
                    tab[ligneGlobale][col]=chaine;
                    col++;
                }
            }

            ligneGlobale++;
            col=0;
        }
    }

    public void remplirligne(int taille,Afn af){
        int tailleGlobaleParcours=1;
        boolean test;
        do{
            test=true;

            for(int m=2;m<ligne;m++){
                String a=tab[ligneTest][colTest];
                String b=tab[m][0];
                if(a.equals(b)==true && a.length()==b.length()){
                    test=false;
                    m=ligne;
                }

            }

            if(test==true){
                tab[ligne][0]=tab[ligneTest][colTest];ligne++;}

            if(colTest==taille){
                ligneTest++;
                colTest=1;
            }else {
                colTest++;
            }
            tailleGlobaleParcours++;
        }while(tailleGlobaleParcours<=taille);
    }


}
	