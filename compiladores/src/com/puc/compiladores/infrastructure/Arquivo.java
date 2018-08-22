package com.puc.compiladores.infraInstructure;

        import com.puc.compiladores.ui.VM;

        import javax.swing.*;
        import java.awt.event.ActionEvent;
        import java.io.File;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.stream.Collectors;
        import java.util.stream.Stream;

public class Arquivo {

    private JFileChooser jFileChooser;

    private List<String> minhaLista;

    public void populaLista(){
        minhaLista = new ArrayList<>();
        VM vm = new VM();
        JFileChooser fileChooser = new JFileChooser();
        int result =  fileChooser.showOpenDialog(vm);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.getAbsolutePath()))) {
                minhaLista = stream.collect(Collectors.toList());
                System.out.println(minhaLista.get(0));
                preencherTabelaInstrucoes(minhaLista, vm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void preencherTabelaInstrucoes(List<String> minhaLista, VM vm) {

        Object[] texto;

        if(null == this.minhaLista){
            texto = new Object[] {1,1,1,1,1};
        }else {
            texto = new Object[] {minhaLista.get(0),1,1,1,1};
            System.out.println("bla" + minhaLista.get(0));
        }

    }

    public String getLinha(int index) {
        return minhaLista.get(index);
    }

    public String getPalavra(int index, int indexPalavra) {

        String linha = getLinha(index);

        String[] palavra = linha.split(" ");

        return palavra[indexPalavra];
    }

    public void fechar(ActionEvent e) {
        System.exit(0);
    }
}
