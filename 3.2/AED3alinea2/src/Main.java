import java.util.*;

class Pessoa{
    String nome;
    ArrayList<Vacina> vacina;

    public Pessoa(String a){
        this.nome = a;
        this.vacina = new ArrayList<>();
    }

    public void addVacina(Vacina a){
        vacina.add(a);
    }

    public String toString(){
        String s = String.format("%s ", nome);
        for (int i = 0; i < vacina.size(); i++){
            s = s + vacina.toString();
        }
        return s;
    }
}

class Vacina{
    String vacina;
    Data data;

    public Vacina(String a, Data b){
        this.vacina = a;
        this.data = b;
    }

    public String toString(){
        return String.format("%s ", vacina + data.toString());
    }
}

class Data{
    int dia;
    int mes;
    int ano;

    public Data(int a, int b, int c){
        this.dia = a;
        this.mes = b;
        this.ano = c;
    }

    public String toString(){
        return String.format("%d/%d/%d", dia, mes, ano);
    }
}

class Ponto{
    Pessoa pessoa;
    Ponto esquerda;
    Ponto direita;

    public Ponto(Pessoa a){
        this.pessoa = a;
        esquerda = null;
        direita = null;
    }
}

class Arvore{
    Ponto raiz;

    public Arvore(){
        this.raiz = null;
    }

    public void adicionar(String nome, Vacina vacina){
        raiz = adicionar_recursivo(nome, vacina, raiz);
    }

    public Ponto adicionar_recursivo(String nome, Vacina vacina, Ponto atual){
        if (atual == null){
            Pessoa pessoa = new Pessoa(nome);
            pessoa.addVacina(vacina);
            atual = new Ponto(pessoa);
            System.out.println("NOVO UTENTE CRIADO");
            return atual;
        }

        if (nome.compareTo(atual.pessoa.nome) < 0) {
            atual.esquerda = adicionar_recursivo(nome, vacina, atual.esquerda);
        } else if (nome.compareTo(atual.pessoa.nome) > 0) {
            atual.direita = adicionar_recursivo(nome, vacina, atual.direita);
        }
        if (nome.equals(atual.pessoa.nome)){
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
                System.out.println("NOVA VACINA CRIADA");
            }
        }
        return atual;
    }

    public void apresentar(){
        apresentar_recursivo(raiz);
    }

    public void apresentar_recursivo(Ponto atual){
        if (atual != null) {
            apresentar_recursivo(atual.esquerda);
            for (Vacina vacina : atual.pessoa.vacina) {
                System.out.printf("%s %s %s ",atual.pessoa.nome, vacina.vacina, vacina.data);
            }
            System.out.print("\n");
            apresentar_recursivo(atual.direita);
        }
    }

    public void consultar(String nome){
        consultar_recursivo(nome, raiz);
    }

    public void consultar_recursivo(String nome, Ponto atual){
        try {
            if (nome.compareTo(atual.pessoa.nome) < 0) {
                consultar_recursivo(nome, atual.esquerda);
            } else if (nome.compareTo(atual.pessoa.nome) > 0) {
                consultar_recursivo(nome, atual.direita);
            } else {
                for (Vacina vacina : atual.pessoa.vacina) {
                    System.out.printf("%s %s ", vacina.vacina, vacina.data);
                }
                System.out.print("\n");
            }
        } catch (NullPointerException e){
            System.out.println("A pessoa não foi encontrada no sistema.");
        }

    }

    public void remover(){
        raiz = null;
        System.out.println("LISTAGEM DE NOMES APAGADA");
    }
}

public class Main {

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
                int[] datas = new int[3];
                String[] s2 = s1[3].split("/");
                for (int i = 0; i < 3; i++) {
                    datas[i] = Integer.parseInt(s2[i]);
                }
                Data data = new Data(datas[0], datas[1], datas[2]);
                Vacina vacina = new Vacina(s1[2], data);
                arvore.adicionar(s1[1], vacina);
            } else if (s1[0].equals("CONSULTA")) {
                arvore.consultar(s1[1]);
            } else if (s1[0].equals("LISTA")) {
                arvore.apresentar();
                System.out.print("FIM\n");
            } else if (s1[0].equals("APAGA")) {
                arvore.remover();
            } else{
                System.exit(-1);
            }
        }
    }

}
