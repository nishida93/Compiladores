package puc.compiladores.sintatico;

public class Scope {

	private ScopeType type;

	private String name;

	private Scope(final ScopeType type, final String name) {
		this.type = type;
		this.name = name;
	}

	public static Scope createProgramScope() {
		return new Scope(ScopeType.PROGRAM, "program");
	}

	public static Scope createFunctionScope(final String functionName) {
		return new Scope(ScopeType.FUNCTION, functionName);
	}

	public static Scope createProcedureScope(final String procedureName) {
		return new Scope(ScopeType.PROCEDURE, procedureName);
	}

	public String getName() {
		return name;
	}

	public boolean isProgram() {
		return ScopeType.PROGRAM.equals(this.type);
	}

	public boolean isFunction() {
		return ScopeType.FUNCTION.equals(this.type);
	}

	public boolean isProcedure() {
		return ScopeType.PROCEDURE.equals(this.type);
	}

	private  enum ScopeType {
		PROGRAM,
		FUNCTION,
		PROCEDURE
	}

	@Override
	public String toString() {
		return "Scope{" + "type=" + type + ", name='" + name + '\'' + '}';
	}
}
