package puc.compiladores.lexico;

public enum Simbolo {
    SPROGRAMA("sprograma"),
    SINICIO("sinicio"),
    SFIM("sfim"),
    SPROCEDIMENTO("sprocedimento"),
    SFUNCAO("sfuncao"),
    SSE("sse"),
    SENTAO("sentao"),
    SSENAO("ssenao"),
    SENQUANTO("senquanto"),
    SFACA("sfaca"),
    SATRIBUICAO("satribuicao"),
    SESCREVA("sescreva"),
    SLEIA("sleia"),
    SVAR("svar"),
    SINTEIRO("sinteiro"),
    SBOOLEANO("sbooleano"),
    SIDENTIFICADOR("sidentificador"),
    SNUMERO("snumero"),
    SPONTO("sponto"),
    SPONTOVIRGULA("sponto_virgula"),
    SVIRGULA("svirgula"),
    SABREPARENTESES("sabre_parenteses"),
    SFECHAPARENTESES("sfecha_parenteses"),
    SMAIOR("smaior"),
    SMAIORIG("smaiorig"),
    SIG("sig"),
    SMENOR("smenor"),
    SMENORIG("smenorig"),
    SDIF("sdif"),
    SMAIS("smais"),
    SMENOS("smenos"),
    SMULT("smult"),
    SDIV("sdiv"),
    SE("se"),
    SOU("sou"),
    SNAO("snao"),
    SDOISPONTOS("sdois_pontos"),
    SVERDADEIRO("sverdadeiro"),
    SFALSO("sfalso");

    private final String name;

    Simbolo(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
