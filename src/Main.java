import java.util.InputMismatchException;
import java.util.Scanner;

import imagem.Imagem;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Identificador de notas de real");
		System.out.println("Processamento digital das imagens, que receberão o OCR");
		Imagem imagem = new Imagem();
		int op = -1;
		String menu = "Menu \n1. Carrega imagem \n2. Aplica filtros na imagem \n"
				+ "0. Sai do programa";

		do {
			try {
				System.out.println();
				System.out.println(menu);
				System.out.print("escolha uma opção: ");
				op = scan.nextInt();
				scan.nextLine();

				switch (op) {
				case 1:
					System.out.println("\nCarrega imagem para o programa");
					imagem.escolheImagem(scan);
					break;

				case 2:
					if (imagem.isExisteImg() == true) {
						System.out.println("Sequencia de filtros");
						System.out.println("Escala de cinza, binarização, eliminação de ruídos e detecção de bordas");
						imagem.aplicaFiltroDeCinza();
						// aplica binarização em cima de imagem gerada pelo filtro de cinza
						imagem.aplicaBinarizacao();
						// aplica eliminação de ruídos em cima de imagem gerada pelo filtro de binarização
						imagem.aplicaEliminacaoDeRuidos();
						// aplica detecção de bordas em cima de imagem gerada pelo filtro de eliminação de ruídos
						imagem.aplicaDeteccaoDeBordaComSobel();
					} else {
						System.out.println("Ainda não existe imagem no programa");
					}
					break;

				case 0:
					break;

				default:
					System.out.println("\nopção inválida");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nopção inválida");
				scan.nextLine();
				op = -1;
			}
		} while (op != 0);

		scan.close();
		System.out.println("\n\nFim do programa");

	}
}
