package gerenciadememoriavirtual;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author vitor
 */
 public class GerenciaDeMemoriaVirtual {

    /**
     * @param args the command line arguments
     */
 public static void main(String[] args) {
               
 abstract class Substituicao {
 protected int numFalhas;
 protected int numQuadros;
 LinkedList quadros;

 public Substituicao(int numQuadros) {
  if (numQuadros < 0)
   throw new IllegalArgumentException();
  this.numQuadros = numQuadros;
  numFalhas = 0;
 }

 public int getPageFaultCount() {
  return numFalhas;
 }

 public abstract void insert(String pageNumber);

 public void imprimirQuadros() {
  System.out.print("Quadros:  |");
  for (int i = 0; i < quadros.size(); i++) {
   System.out.print(quadros.get(i) + " |");
  }
  System.out.println();
 }

}

class AlgoritmoSegundaChance extends Substituicao {
 LinkedList bits;
 private int point = 0;

 public AlgoritmoSegundaChance(int numeroDeQuadros) {
  super(numeroDeQuadros);
  this.quadros = new LinkedList();
  this.bits = new LinkedList();

 }

 @Override
 public void insert(String pageNumber) {
  int tmp = quadros.indexOf(pageNumber);

  // caso a pagina ainda nao esteja na memoria
  if (tmp == -1) {
   if (quadros.size() < numQuadros) {
    quadros.add(pageNumber);
    bits.add(0);
   } else {
     while((int)bits.get(point) == 1){
     bits.set(point, 0);
     point++;
     // ponteiro voltando ao inicio
     if (point == numQuadros) {
      point = 0;
     }
    } 
    // substituicao
    quadros.remove(point);
    bits.remove(point);
    quadros.add(point, pageNumber);
    bits.add(point, 0);

    point++;
    // ponteiro voltando ao inicio
    if (point == numQuadros) {
     point = 0;
    }
   }
   numFalhas++;
  } else { // se a pagina ja esta na memoria
   bits.set(tmp, 1);

  }
 }

 @Override
 public void imprimirQuadros() {
  System.out.print("Quadros:  |");
  for (int i = 0; i < quadros.size(); i++) {
   System.out.print(quadros.get(i) + "| bit: " + bits.get(i) + " |");
  }
  System.out.println();
 }
 }

 class AlgoritmoFifo extends Substituicao {
 private int INSERT = 0;

 public AlgoritmoFifo(int numeroDeQuadros) {

  super(numeroDeQuadros);
  this.quadros = new LinkedList();
 }

 @Override
 public void insert(String pageNumber) {
  // antes de inserir, checar se a pagina ja esta na lista
  if (!quadros.contains(pageNumber)) {

   // se a quantidade de paginas na memoria for menor que o numero de
   // quadros
   // ou seja, ainda ha espaco
   if (quadros.size() < numQuadros) {
    quadros.add(pageNumber);
   } else {
    quadros.remove(INSERT);
    quadros.add(INSERT, pageNumber);
    INSERT++;
    if (INSERT == numQuadros) {
     INSERT = 0;
    }
   }
   numFalhas++;

  }
 }
 }
        
        int vQuadro = 3;
        // TODO code application logic here
        
        Scanner scanner = new Scanner(System.in);
        String referencia = scanner.nextLine();
        String[] stringReferencia = referencia.split(",");

         // Algoritmo de substituição FIFO
        Substituicao fifo = new AlgoritmoFifo(vQuadro);

        for (int i = 0; i < (stringReferencia.length - 1); i++) {
        fifo.insert(stringReferencia[i]);

        }
        System.out.println("Falhas FIFO: " + fifo.getPageFaultCount());


        // Algoritmo de substituição SEGUNDA CHANCE
        Substituicao twice = new AlgoritmoSegundaChance(vQuadro);

        for (int i = 0; i < (stringReferencia.length - 1); i++) {
        twice.insert(stringReferencia[i]);
        twice.imprimirQuadros();

        }
        System.out.println("Falhas SEGUNDA CHANCE: " + twice.getPageFaultCount());

        }
        }



    
    

