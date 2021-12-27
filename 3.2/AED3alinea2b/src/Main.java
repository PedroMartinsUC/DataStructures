import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    static class Pessoa{
        int num;
        ArrayList<Vacina> vacina;

        public Pessoa(int a){
            this.num = a;
            this.vacina = new ArrayList<>();
        }

        public void addVacina(Vacina a){
            vacina.add(a);
        }

        public String toString(){
            String s = String.format("%d ", num);
            for (int i = 0; i < vacina.size(); i++){
                s = s + vacina.toString();
            }
            return s;
        }
    }

    static class Vacina implements Serializable {
        String vacina;
        String data;

        public Vacina(String a, String b){
            this.vacina = a;
            this.data = b;
        }

        public String getVacina(){
            return this.vacina;
        }

        public String toString(){
            return String.format("%s %s", vacina, data);
        }

    }

    static class OrdenarVacinas implements Comparator<Vacina> {
        @Override
        public int compare(final Vacina a, final Vacina b){
            return a.getVacina().compareTo(b.getVacina());
        }
    }

    static class Ponto{
        Pessoa pessoa;
        Ponto esquerda;
        Ponto direita;
        int altura;

        public Ponto(Pessoa a){
            this.pessoa = a;
            esquerda = null;
            direita = null;
            this.altura = 0;
        }
    }

    static class Arvore {

        Ponto raiz;

        public Arvore() {
            this.raiz = null;
        }

        public void adicionar(int num, Vacina vacina) {
            raiz = adicionar_recursivo(num, vacina, raiz);
        }

        public Ponto adicionar_recursivo(int num, Vacina vacina, Ponto atual) {
            if (atual == null) {
                Pessoa pessoa = new Pessoa(num);
                pessoa.addVacina(vacina);
                atual = new Ponto(pessoa);
                System.out.println("NOVO UTENTE CRIADO");
                return atual;
            }

            if (num < atual.pessoa.num) {
                atual.esquerda = adicionar_recursivo(num, vacina, atual.esquerda);
            } else if (num > atual.pessoa.num) {
                atual.direita = adicionar_recursivo(num, vacina, atual.direita);
            } else {
                int count = 0;
                for (Vacina vacina1 : atual.pessoa.vacina) {
                    if (vacina1.vacina.equals(vacina.vacina)){
                        vacina1.data = vacina.data;
                        System.out.println("VACINA ATUALIZADA");
                        count++;
                    }
                }
                if (count == 0){
                    atual.pessoa.vacina.add(vacina);
                    Collections.sort(atual.pessoa.vacina, new OrdenarVacinas());
                    System.out.println("NOVA VACINA INSERIDA");
                }
            }
            return ordenar(atual);
        }

        public Ponto ordenar(Ponto atual){

            atual.altura = Math.max(verificarAltura(atual.esquerda), verificarAltura(atual.direita)) + 1;
            int equilibrio = verificarEquilibrio(atual);

            if (equilibrio < -1){
                if (verificarEquilibrio(atual.direita) < 0){
                    atual = rodaresquerda(atual);
                } else {
                    atual.direita = rodardireita(atual.direita);
                    atual = rodaresquerda(atual);
                }
            } else if (equilibrio > 1){
                if (verificarEquilibrio(atual.esquerda) > 0){
                    atual = rodardireita(atual);
                } else {
                    atual.esquerda = rodaresquerda(atual.esquerda);
                    atual = rodardireita(atual);
                }
            }
            return atual;
        }

        public int verificarEquilibrio(Ponto n) {
            if (n == null){
                return 0;
            } else {
                return verificarAltura(n.esquerda) - verificarAltura(n.direita);
            }
        }

        public Ponto rodardireita(Ponto atual){
            Ponto root = atual.esquerda;
            Ponto direita = root.direita;

            root.direita = atual;
            atual.esquerda = direita;

            atual.altura = Math.max(verificarAltura(atual.esquerda), verificarAltura(atual.direita)) + 1;
            root.altura = Math.max(verificarAltura(atual.esquerda), verificarAltura(atual.direita)) + 1;

            return root;
        }

        public Ponto rodaresquerda(Ponto atual){
            Ponto root = atual.direita;
            Ponto direita = root.esquerda;

            root.esquerda = atual;
            atual.direita = direita;

            atual.altura = Math.max(verificarAltura(atual.esquerda), verificarAltura(atual.direita)) + 1;
            root.altura = Math.max(verificarAltura(atual.esquerda), verificarAltura(atual.direita)) + 1;

            return root;
        }

        public int verificarAltura(Ponto atual){
            if (atual == null){
                return -1;
            } else {
                return atual.altura;
            }
        }

        public void apresentar(){
            apresentar_recursivo(raiz);
        }

        public void apresentar_recursivo(Ponto atual){
            if (atual != null) {
                apresentar_recursivo(atual.esquerda);
                System.out.printf("%d", atual.pessoa.num);
                for (Vacina vacina : atual.pessoa.vacina) {
                    System.out.printf(" %s %s", vacina.vacina, vacina.data);
                }
                System.out.print("\n");
                apresentar_recursivo(atual.direita);
            }
        }

        public void consultar(int num){
            consultar_recursivo(num, raiz);
        }

        public void consultar_recursivo(int num, Ponto atual){
            try {
                if (num < atual.pessoa.num) {
                    consultar_recursivo(num, atual.esquerda);
                } else if (num > atual.pessoa.num) {
                    consultar_recursivo(num, atual.direita);
                } else {
                    System.out.printf("%d", atual.pessoa.num);
                    for (Vacina vacina : atual.pessoa.vacina) {
                        System.out.printf(" %s %s", vacina.vacina, vacina.data);
                    }
                    System.out.print("\nFIM\n");
                }
            } catch (NullPointerException e){
                System.out.println("NAO ENCONTRADO");
            }
        }

        public void remover(){
            raiz = null;
            System.out.println("LISTAGEM DE NOMES APAGADA");
        }
    }
    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        ler_inputs(arvore);

    }

    public static void ler_inputs(Arvore arvore){
        Scanner sc = new Scanner(System.in);
        while(true) {
            String s = sc.nextLine();
            String[] s1 = s.split(" ");
            if (s1[0].equals("ACRESCENTA")) {
                Vacina vacina = new Vacina(s1[2], s1[3]);
                arvore.adicionar(Integer.parseInt(s1[1]), vacina);
            } else if (s1[0].equals("CONSULTA")) {
                arvore.consultar(Integer.parseInt(s1[1]));
            } else if (s1[0].equals("LISTAGEM")) {
                arvore.apresentar();
                System.out.print("FIM\n");
            } else if (s1[0].equals("APAGA")) {
                arvore.remover();
            } else if (s1[0].equals("FIM")){
                System.exit(0);
            }
        }
    }
}
