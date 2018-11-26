package puc.compiladores.geracao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class GeracaoCodigo {

	private PrintWriter fileWriter;
	private int rotulo;

	public GeracaoCodigo() {
		rotulo = 1;
	}

	public int getRotulo() {
		return rotulo;
	}

	public void generateFile(String lexema) throws FileNotFoundException, UnsupportedEncodingException {
		try {
			fileWriter = new PrintWriter(lexema + ".txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			System.out.println("Erro ao gerar arquivo >" + ex);
			throw ex;
		}
	}

	public void generateClose() {
		fileWriter.close();
	}

	public void generateStart() {
		fileWriter.println("START");
	}

	public void generateHlt() {
		fileWriter.println("HLT");
	}

	public void generateLabel(final String rot) {
		fileWriter.println(rot +" NULL");
	}

	public void generateJump(String jump, String rotulo) {
		fileWriter.println(jump + " " + rotulo);
	}

	public void generateCall(String label)
	{
		fileWriter.println("CALL " + label);
	}

	public void generateLdv(final String buscaPosicaoSimbolo) {
		fileWriter.println("LDV " + buscaPosicaoSimbolo);
	}

	public void generatePrn() {
		fileWriter.println("PRN");
	}

	public void geraAlloc(final int controle) {
		fileWriter.println("ALLOC " + controle + ",1");
	}

	public void geraDalloc(final int posicaoVariaveis, final int qtdeVariaveis) {
		fileWriter.println("DALLOC " + posicaoVariaveis + "," + qtdeVariaveis);
	}

	public void geraReturnf(final int posicaoVariaveis, final int qtdeVariaveis) {
		if (qtdeVariaveis > 0) {
			fileWriter.println("RETURNF " + posicaoVariaveis + "," + qtdeVariaveis);
		} else {
			fileWriter.println("RETURNF");
		}

	}

	public void geraReturn() {
		fileWriter.println("RETURN");
	}

	public void generateLdc(final String value) {
		fileWriter.println("LDC " + value);
	}

	public void generateSimpleInstruction(final String instruction) {
		fileWriter.println(instruction);
	}

}
