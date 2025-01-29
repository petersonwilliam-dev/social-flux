package com.backend.util;

import io.javalin.http.UploadedFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ImagemUtil {

    public static String inserirImagem(UploadedFile uploadedFile, Integer id) throws IOException {

        if (uploadedFile != null) {
            if (!isImagem(uploadedFile)) throw new IllegalArgumentException("Arquivo não é uma imagem");
            if (!formatoPermitido(uploadedFile)) throw new IllegalArgumentException("Formato de imagem não é permitido");
            String extensaoArquivo = buscarExtensao(uploadedFile);
            String novoNomeArquivo = UUID.randomUUID() + "." + extensaoArquivo;
            String diretorio = "src/main/resources/imagens/"+id+"/";
            Files.createDirectories(Path.of(diretorio));
            File destino = new File(diretorio + novoNomeArquivo);
            try (InputStream inputStream = uploadedFile.content()) {
                Files.copy(inputStream, destino.toPath());
            }
            return novoNomeArquivo;
        } else {
            throw new IllegalArgumentException("Arquivo de imagem vazio");
        }
    }

    public static String inserirImagem(BufferedImage imagemRedimensionada, Integer id, String extensaoArquivo) throws IOException {
        String novoNomeArquivo = UUID.randomUUID() + "." + extensaoArquivo;
        String diretorio = "src/main/resources/imagens/" + id + "/";
        Files.createDirectories(Path.of(diretorio));
        File destino = new File(diretorio + novoNomeArquivo);

        ImageIO.write(imagemRedimensionada, extensaoArquivo, destino);

        return novoNomeArquivo;
    }

    public static boolean isImagem(UploadedFile uploadedFile) {
        String type = uploadedFile.contentType();
        if (!type.startsWith("image/")) return false;
        return true;
    }

    public static boolean formatoPermitido(UploadedFile uploadedFile) {
        String nomeArquivo = uploadedFile.filename();
        String extensaoArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toLowerCase();
        if (!extensaoArquivo.equals("png") && !extensaoArquivo.equals("jpeg") && !extensaoArquivo.equals("jpg")) return false;
        return true;
    }

    public static String buscarExtensao(File imagem) {
        if (imagem.exists()) {
            String nome = imagem.getName();
            String extensao = "";
            int posicaoExtensao = nome.lastIndexOf(".");
            if (posicaoExtensao > 0 && posicaoExtensao < nome.length()) extensao = nome.substring(posicaoExtensao + 1).toLowerCase();

            return extensao;
        } else {
            throw new IllegalArgumentException("Imagem não encontrada");
        }
    }

    public static  String buscarExtensao(UploadedFile uploadedFile) {
        String nomeArquivo = uploadedFile.filename();
        return nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toLowerCase();
    }

    public static BufferedImage redimensionarImagem(UploadedFile uploadedFile, int larguraDesejada, int alturaDesejada) throws IOException {
        if (uploadedFile != null) {
            try (InputStream inputStream = uploadedFile.content()) {
                BufferedImage imagemOriginal = ImageIO.read(inputStream);

                int larguraOriginal = imagemOriginal.getWidth();
                int alturaOriginal = imagemOriginal.getHeight();
                int novaLargura, novaAltura;

                if (larguraOriginal > alturaOriginal) {
                    novaAltura = alturaDesejada;
                    novaLargura = (larguraOriginal * alturaDesejada) / alturaOriginal;
                } else {
                    novaLargura = larguraDesejada;
                    novaAltura = (alturaOriginal * larguraDesejada) / larguraOriginal;
                }

                Image imagemRedimensionada = imagemOriginal.getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);
                BufferedImage imagemFinal = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = imagemFinal.createGraphics();
                graphics2D.drawImage(imagemRedimensionada, 0, 0, null);
                graphics2D.dispose();

                int x = (novaLargura - larguraDesejada) / 2;
                int y = (novaAltura - alturaDesejada) / 2;

                return imagemFinal.getSubimage(x, y, larguraDesejada, alturaDesejada);
            }
        } else {
            throw new IllegalArgumentException("Arquivo vazio");
        }
    }
}