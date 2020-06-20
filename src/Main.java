import java.util.InputMismatchException;
import java.util.Scanner;

import imagem.Imagem;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Identificador de notas de real");
		Imagem imagem = new Imagem();
		int op = -1;
		String menu = "Menu \n1. Carrega imagem \n2. Aplica filtros na imagem e realiza t�cnica de OCR em seguida \n"
				+ "3. Testa identifica��o de nota em todas as imagens da pasta notas \n0. Sai do programa";

		do {
			try {
				System.out.println();
				System.out.println(menu);
				System.out.print("escolha uma op��o: ");
				op = scan.nextInt();
				scan.nextLine();

				switch (op) {
				case 1:
					System.out.println("\nCarrega imagem para o programa");
					// imagem.escolheImagem(scan);
					break;

				case 2:
					if (imagem.isExisteImg()==true) {
						System.out.println("Sequencia de filtros");
						System.out.println("Escala de cinza, binariza��o, elimina��o de ru�dos e detec��o de bordas");
						// come�a aplicando filtro de cinza na imagem do programa
						imagem.aplicaFiltroDeCinza(imagem.getCaminhoImg());
						// filtro acima criar� arquivo de imagem com nome 1 na pasta notas/processadas
						// aplica binariza��o em cima de imagem de nome 1
						imagem.aplicaBinarizacao("notas/processadas/1");
						// filtro acima criar� arquivo de imagem com nome 2 na pasta notas/processadas
						// aplica elimina��o de ru�dos em cima de imagem de nome 2
						imagem.aplicaEliminacaoDeRuidos("notas/processadas/2");
						// filtro acima criar� arquivo de imagem com nome 3 na pasta notas/processadas
						// aplica detec��o de bordas em cima de imagem de nome 3
						imagem.aplicaDeteccaoDeBordaComSobel("notas/processadas/3");
						// filtro acima criar� arquivo de imagem com nome 4 na pasta notas/processadas
						// agora na �ltima imagem processada aplica o ocr do tesseract
						// a fazer
					} else {
						System.out.println("Ainda n�o existe imagem no programa");
					}
					break;

				case 3:
					break;

				case 0:
					break;

				default:
					System.out.println("\nop��o inv�lida");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("op��o inv�lida");
				scan.nextLine();
				op = -1;
			}
		} while (op != 0);

		scan.close();
		System.out.println("\n\nFim do programa");

	}
}
