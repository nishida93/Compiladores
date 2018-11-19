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
		fileWriter.println(rot +": NULL");
	}

	public void generateJump(String jump, String rotulo) {
		fileWriter.println(jump + " " + rotulo);
	}

	public void generateCall(String label)
	{
		fileWriter.println("CALL " + label);
	}

	public void generateAlloc(String comando, String numeros)
	{
		if(comando.equals("ALLOC "))
		{
			fileWriter.println(comando+numeros);
		}

		if(comando.equals("DALLOC "))
		{
			fileWriter.println(comando+numeros);
		}
	}

	public String geraRotulo() {
		String ret = "L" + rotulo;
		rotulo++;
		return ret;
	}

	public String pegaRotulo() {
		return "L" + rotulo;
	}

	public void generateLdv(final String buscaPosicaoSimbolo) {
		fileWriter.println("LDV " + buscaPosicaoSimbolo);
	}

	public void generatePrn() {
		fileWriter.println("PRN");
	}
}
