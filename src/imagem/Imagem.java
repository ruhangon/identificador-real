package imagem;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

public class Imagem {
	private String caminhoImg;
	private String extensaoImg;
	private boolean existeImg = false;
	private Mat src;

	// escolhe uma imagem para ser usada no programa
	public void escolheImagem(Scanner scan) {
		File cam;
		do {
			System.out.println("Digite o caminho da imagem");
			System.out.print("caminho: ");
			this.setCaminhoImg(scan.nextLine());
			cam = new File(this.getCaminhoImg());
			if (cam.exists()) {
				this.setExisteImg(true);
				descobreExtensao();
			} else {
				System.out.println("a imagem do caminho passado não existe");
			}
		} while (this.isExisteImg() != true);
	}

	// aplica filtro de cinza com open cv
	public void aplicaFiltroDeCinza(String caminho) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		caminho = caminho.concat(".");
		caminho = caminho.concat(this.getExtensaoImg());
		Mat dst = new Mat();
		BufferedImage novaImagemBI = null;
		try {
			File img = new File(caminho);
			BufferedImage imagemBI = ImageIO.read(img);
			byte[] data = ((DataBufferByte) imagemBI.getRaster().getDataBuffer()).getData();
			this.src = new Mat(imagemBI.getHeight(), imagemBI.getWidth(), CvType.CV_8UC3);
			this.src.put(0, 0, data);
			dst = new Mat(imagemBI.getHeight(), imagemBI.getWidth(), CvType.CV_8UC1);
			Imgproc.cvtColor(this.src, dst, Imgproc.COLOR_RGB2GRAY);
			byte[] data1 = new byte[dst.rows() * dst.cols() * (int) (dst.elemSize())];
			dst.get(0, 0, data1);
			novaImagemBI = new BufferedImage(dst.cols(), dst.rows(), BufferedImage.TYPE_BYTE_GRAY);
			novaImagemBI.getRaster().setDataElements(0, 0, dst.cols(), dst.rows(), data1);
			String novo = "notas/processadas/1.";
			novo = novo.concat(this.getExtensaoImg());
			File resultado = new File(novo);
			ImageIO.write(novaImagemBI, this.getExtensaoImg(), resultado);
			System.out.println("Primeira imagem processada salva na pasta notas/processadas (filtro de cinza)");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// aplica binarização em cima de imagem em escala de cinza
	public void aplicaBinarizacao(String caminho) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		caminho = caminho.concat(".");
		caminho = caminho.concat(this.getExtensaoImg());
		try {
			this.src = Imgcodecs.imread(caminho);
			Mat dst = new Mat();
			Imgproc.threshold(this.src, dst, 200, 500, Imgproc.THRESH_BINARY);
			String novoArquivo = "notas/processadas/2.";
			novoArquivo = novoArquivo.concat(this.getExtensaoImg());
			Imgcodecs.imwrite(novoArquivo, dst);
			System.out.println("Segunda imagem processada salva na pasta notas/processadas (filtro de binarização)");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// aplica eliminação de ruídos em cima de imagem binarizada
	public void aplicaEliminacaoDeRuidos(String caminho) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		caminho = caminho.concat(".");
		caminho = caminho.concat(this.getExtensaoImg());
		try {
			this.src = Imgcodecs.imread(caminho);
			Mat dst = new Mat();
			Photo.fastNlMeansDenoising(this.src, dst);
			String novoArquivo = "notas/processadas/3.";
			novoArquivo = novoArquivo.concat(this.getExtensaoImg());
			Imgcodecs.imwrite(novoArquivo, dst);
			System.out.println(
					"Terceira imagem processada salva na pasta notas/processadas (filtro de eliminação de ruídos)");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// filtro de detecção de borda por Sobel
	public void aplicaDeteccaoDeBordaComSobel(String caminho) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		caminho = caminho.concat(".");
		caminho = caminho.concat(this.getExtensaoImg());
		try {
			this.src = Imgcodecs.imread(caminho);
			Mat dst = new Mat();
			Mat grad_x = new Mat();
			Mat abs_grad_x = new Mat();
			Mat grad_y = new Mat();
			Mat abs_grad_y = new Mat();
			int scale = 1;
			int delta = 0;
			int ddepth = CvType.CV_16S;
			Imgproc.Sobel(this.src, grad_x, ddepth, 1, 0);
			Core.convertScaleAbs(grad_x, abs_grad_x);
			Imgproc.Sobel(this.src, grad_y, ddepth, 0, 1);
			Core.convertScaleAbs(grad_y, abs_grad_y);
			Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, dst);
			String nomeNovoArquivo = "notas/processadas/4.";
			nomeNovoArquivo = nomeNovoArquivo.concat(this.getExtensaoImg());
			Imgcodecs.imwrite(nomeNovoArquivo, dst);
			System.out.println(
					"Quarta imagem processada salva na pasta notas/processadas (filtro de detecção de borda por Sobel)");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// descobre a extensão do arquivo, exemplo .jpg
	private void descobreExtensao() {
		int localExt = -1; // pega a posição do ponto na palavra
		for (int i = 0; i < this.getCaminhoImg().length(); i++) {
			if (this.getCaminhoImg().charAt(i) == ('.')) {
				localExt = i + 1;
				break;
			}
		}
		this.extensaoImg = this.getCaminhoImg().substring(localExt, this.getCaminhoImg().length());
	}

	public String getCaminhoImg() {
		return caminhoImg;
	}

	public void setCaminhoImg(String caminhoImg) {
		this.caminhoImg = caminhoImg;
	}

	public String getExtensaoImg() {
		return extensaoImg;
	}

	public void setExtensaoImg(String extensaoImg) {
		this.extensaoImg = extensaoImg;
	}

	public boolean isExisteImg() {
		return existeImg;
	}

	public void setExisteImg(boolean existeImg) {
		this.existeImg = existeImg;
	}

	public Mat getSrc() {
		return src;
	}

	public void setSrc(Mat src) {
		this.src = src;
	}

}
