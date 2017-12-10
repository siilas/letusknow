package br.com.silas.letusknow.ws;

public class LetUsKnowWs {

    private LetUsKnowWs() {
    }

    public static LetUsKnowWs criar() {
        LetUsKnowWs ws = new LetUsKnowWs();
        return ws;
    }

    public LetUsKnowWs setRootUrl() {
        return this;
    }

}
