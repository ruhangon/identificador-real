import java.util.InputMismatchException;
import java.util.Scanner;

import imagem.Imagem;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Identificador de notas de real");
		Imagem imagem = new Imagem();
		int op = -1;
		String menu = "1. Carrega imagem \n2. Aplica filtros na imagem e realiza técnica de OCR em seguida \n"
				+ "3. Testa identificação de nota em todas as imagens da pasta notas \n0. Sai do programa";

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
					// imagem.escolheImagem(scan);
					break;

				case 2:
					break;

				case 3:
					break;

				case 0:
					break;

				default:
					System.out.println("\nopção inválida");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				op = -1;
			}
		} while (op != 0);

		scan.close();
		System.out.println("\n\nFim do programa");

	}
}
